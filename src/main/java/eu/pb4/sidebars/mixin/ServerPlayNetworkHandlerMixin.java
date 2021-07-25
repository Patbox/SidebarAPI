package eu.pb4.sidebars.mixin;

import eu.pb4.sidebars.SidebarAPIMod;
import eu.pb4.sidebars.api.lines.ImmutableSidebarLine;
import eu.pb4.sidebars.api.Sidebar;
import eu.pb4.sidebars.api.lines.SidebarLine;
import eu.pb4.sidebars.interfaces.SidebarHolder;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.ScoreboardDisplayS2CPacket;
import net.minecraft.network.packet.s2c.play.ScoreboardObjectiveUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ScoreboardPlayerUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.TeamS2CPacket;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerPlayNetworkHandlerMixin implements SidebarHolder {

    @Unique
    private final Set<Sidebar> sidebars = new HashSet<>();
    @Unique
    private final ImmutableSidebarLine[] lines = new ImmutableSidebarLine[15];
    @Unique
    private Sidebar currentSidebar = null;
    @Unique
    private Text title = null;
    @Unique
    private boolean alreadyHidden = true;

    @Unique int currentTick = 0;

    @Shadow
    public abstract void sendPacket(Packet<?> packet);

    @Inject(method = "onDisconnected", at = @At("TAIL"))
    private void removeFromSidebars(Text reason, CallbackInfo ci) {
        for (Sidebar sidebar : new HashSet<>(this.sidebars)) {
            sidebar.removePlayer((ServerPlayNetworkHandler) (Object) this);
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void updateSidebar(CallbackInfo ci) {
        if (this.currentSidebar == null && !this.alreadyHidden) {
            this.alreadyHidden = true;
            {
                ScoreboardDisplayS2CPacket packet = new ScoreboardDisplayS2CPacket(1, null);
                this.sendPacket(packet);
            }

            {
                ScoreboardObjectiveUpdateS2CPacket packet = new ScoreboardObjectiveUpdateS2CPacket( SidebarAPIMod.SCOREBOARD_OBJECTIVE, 1);
                this.sendPacket(packet);
            }

            this.title = null;
            for (int index = 0; index < this.lines.length; index++) {
                if (this.lines[index] != null) {
                    this.sendPacket(TeamS2CPacket.updateRemovedTeam(SidebarAPIMod.TEAMS.get(index)));
                    this.lines[index] = null;
                }
            }
        }

        if (this.currentSidebar == null) {
            return;
        }

        if (this.alreadyHidden) {
            this.alreadyHidden = false;
            this.title = this.currentSidebar.getTitle();
            this.currentTick = 0;

            {
                ScoreboardObjectiveUpdateS2CPacket packet = new ScoreboardObjectiveUpdateS2CPacket(SidebarAPIMod.SCOREBOARD_OBJECTIVE, 0);
                SOUS2CPacketAccessor accessor = (SOUS2CPacketAccessor) packet;
                accessor.setTitle(this.title);
                this.sendPacket(packet);
            }
            {
                ScoreboardDisplayS2CPacket packet = new ScoreboardDisplayS2CPacket(1, null);
                ScoreboardDisplayS2CPacketAccessor accessor = (ScoreboardDisplayS2CPacketAccessor) packet;
                accessor.setName(SidebarAPIMod.OBJECTIVE_NAME);
                this.sendPacket(packet);
            }

            int x = 0;
            for (SidebarLine line : this.currentSidebar.getLinesFor((ServerPlayNetworkHandler) (Object) this)) {
                this.lines[x] = line.immutableCopy((ServerPlayNetworkHandler) (Object) this);
                TeamS2CPacket packet = TeamS2CPacket.updateTeam(SidebarAPIMod.TEAMS.get(x), true);
                ((SerializableTeamAccessor) packet.getTeam().get()).setPrefix(line.getText((ServerPlayNetworkHandler) (Object) this));
                this.sendPacket(packet);

                this.sendPacket(new ScoreboardPlayerUpdateS2CPacket(
                        ServerScoreboard.UpdateMode.CHANGE, SidebarAPIMod.OBJECTIVE_NAME, SidebarAPIMod.FAKE_PLAYER_NAMES.get(x), line.getValue()));
                x++;
            }
        } else {
            if (this.currentTick % this.currentSidebar.getUpdateRate() != 0) {
                this.currentTick++;
                return;
            }

            Text sidebarTitle = this.currentSidebar.getTitle();
            if (!sidebarTitle.equals(this.title)) {
                this.title = sidebarTitle;
                ScoreboardObjectiveUpdateS2CPacket packet = new ScoreboardObjectiveUpdateS2CPacket(SidebarAPIMod.SCOREBOARD_OBJECTIVE, 2);
                SOUS2CPacketAccessor accessor = (SOUS2CPacketAccessor) packet;
                accessor.setTitle(this.title);
                this.sendPacket(packet);
            }

            int index = 0;

            for (SidebarLine line : this.currentSidebar.getLinesFor((ServerPlayNetworkHandler) (Object) this)) {
                if (this.lines[index] == null || !this.lines[index].equals(line, (ServerPlayNetworkHandler) (Object) this)) {
                    TeamS2CPacket packet = TeamS2CPacket.updateTeam(SidebarAPIMod.TEAMS.get(index),this.lines[index] == null);
                    ((SerializableTeamAccessor) packet.getTeam().get()).setPrefix(line.getText((ServerPlayNetworkHandler) (Object) this));
                    this.sendPacket(packet);

                    this.sendPacket(new ScoreboardPlayerUpdateS2CPacket(
                            ServerScoreboard.UpdateMode.CHANGE, SidebarAPIMod.OBJECTIVE_NAME, SidebarAPIMod.FAKE_PLAYER_NAMES.get(index), line.getValue()));

                    this.lines[index] = line.immutableCopy((ServerPlayNetworkHandler) (Object) this);
                }
                index++;
            }

            for (; index < this.lines.length; index++) {
                if (this.lines[index] != null) {
                    this.sendPacket(new ScoreboardPlayerUpdateS2CPacket(
                            ServerScoreboard.UpdateMode.REMOVE, SidebarAPIMod.OBJECTIVE_NAME, SidebarAPIMod.FAKE_PLAYER_NAMES.get(index), 0));
                    this.sendPacket(TeamS2CPacket.updateRemovedTeam(SidebarAPIMod.TEAMS.get(index)));
                }

                this.lines[index] = null;
            }
            this.currentTick++;
        }
    }

    @Override
    public void addSidebar(Sidebar sidebar) {
        this.sidebars.add(sidebar);
        this.updateCurrentSidebar(sidebar);
    }

    @Override
    public void removeSidebar(Sidebar sidebar) {
        this.sidebars.remove(sidebar);
        if (sidebar == this.currentSidebar) {
            Sidebar newSidebar = null;
            for (Sidebar sidebar1 : this.sidebars) {
                if (newSidebar == null || newSidebar.getPriority().isLowerThan(sidebar1.getPriority())) {
                    newSidebar = sidebar1;
                }
            }
            this.setCurrentSidebar(newSidebar);
        }
    }

    @Override
    public Set<Sidebar> getSidebarSet() {
        return Collections.unmodifiableSet(this.sidebars);
    }

    @Override
    public Sidebar getCurrentSidebar() {
        return this.currentSidebar;
    }

    @Unique
    private void setCurrentSidebar(Sidebar sidebar) {
        this.currentSidebar = sidebar;
    }

    @Override
    public void updateCurrentSidebar(Sidebar candidate) {
        if (this.currentSidebar != null) {
            if (this.currentSidebar.getPriority().isLowerThan(candidate.getPriority())) {
                this.setCurrentSidebar(candidate);
            }
        } else {
            this.setCurrentSidebar(candidate);
        }
    }

    @Override
    public void clearSidebars() {
        this.sidebars.clear();
        this.setCurrentSidebar(null);
    }
}
