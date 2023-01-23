package eu.pb4.sidebars.mixin;

import eu.pb4.sidebars.SidebarAPIMod;
import eu.pb4.sidebars.api.SidebarInterface;
import eu.pb4.sidebars.api.lines.ImmutableSidebarLine;
import eu.pb4.sidebars.api.lines.SidebarLine;
import eu.pb4.sidebars.impl.SidebarHolder;
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
    private final Set<SidebarInterface> sidebarApi$sidebars = new HashSet<>();
    @Unique
    private final ImmutableSidebarLine[] sidebarApi$lines = new ImmutableSidebarLine[15];
    @Unique
    private SidebarInterface sidebarApi$currentSidebar = null;
    @Unique
    private Text sidebarApi$title = null;
    @Unique
    private boolean sidebarApi$alreadyHidden = true;

    @Unique int sidebarApi$currentTick = 0;

    @Shadow
    public abstract void sendPacket(Packet<?> packet);

    @Inject(method = "onDisconnected", at = @At("TAIL"))
    private void sidebarApi$removeFromSidebars(Text reason, CallbackInfo ci) {
        for (SidebarInterface sidebar : new HashSet<>(this.sidebarApi$sidebars)) {
            sidebar.disconnected((ServerPlayNetworkHandler) (Object) this);
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void sidebarApi$updateSidebar(CallbackInfo ci) {
        this.sidebarApi$updateState(true);
    }

    @Override
    public void sidebarApi$removeEmpty() {
        if (!this.sidebarApi$alreadyHidden) {
            this.sidebarApi$alreadyHidden = true;
            {
                ScoreboardDisplayS2CPacket packet = new ScoreboardDisplayS2CPacket(1, null);
                this.sendPacket(packet);
            }

            {
                ScoreboardObjectiveUpdateS2CPacket packet = new ScoreboardObjectiveUpdateS2CPacket( SidebarAPIMod.SCOREBOARD_OBJECTIVE, 1);
                this.sendPacket(packet);
            }

            this.sidebarApi$title = null;
            for (int index = 0; index < this.sidebarApi$lines.length; index++) {
                if (this.sidebarApi$lines[index] != null) {
                    this.sendPacket(TeamS2CPacket.updateRemovedTeam(SidebarAPIMod.TEAMS.get(index)));
                    this.sidebarApi$lines[index] = null;
                }
            }
        }
    }

    @Override
    public void sidebarApi$setupInitial() {
        if (this.sidebarApi$alreadyHidden) {
            this.sidebarApi$alreadyHidden = false;
            this.sidebarApi$title = this.sidebarApi$currentSidebar.getTitleFor((ServerPlayNetworkHandler) (Object) this);
            this.sidebarApi$currentTick = 0;

            {
                ScoreboardObjectiveUpdateS2CPacket packet = new ScoreboardObjectiveUpdateS2CPacket(SidebarAPIMod.SCOREBOARD_OBJECTIVE, 0);
                SOUS2CPacketAccessor accessor = (SOUS2CPacketAccessor) packet;
                accessor.setTitle(this.sidebarApi$title);
                this.sendPacket(packet);
            }
            {
                ScoreboardDisplayS2CPacket packet = new ScoreboardDisplayS2CPacket(1, null);
                ScoreboardDisplayS2CPacketAccessor accessor = (ScoreboardDisplayS2CPacketAccessor) packet;
                accessor.setName(SidebarAPIMod.OBJECTIVE_NAME);
                this.sendPacket(packet);
            }

            int x = 0;
            for (SidebarLine line : this.sidebarApi$currentSidebar.getLinesFor((ServerPlayNetworkHandler) (Object) this)) {
                this.sidebarApi$lines[x] = line.immutableCopy((ServerPlayNetworkHandler) (Object) this);
                TeamS2CPacket packet = TeamS2CPacket.updateTeam(SidebarAPIMod.TEAMS.get(x), true);
                ((SerializableTeamAccessor) packet.getTeam().get()).setPrefix(line.getText((ServerPlayNetworkHandler) (Object) this));
                this.sendPacket(packet);

                this.sendPacket(new ScoreboardPlayerUpdateS2CPacket(
                        ServerScoreboard.UpdateMode.CHANGE, SidebarAPIMod.OBJECTIVE_NAME, SidebarAPIMod.FAKE_PLAYER_NAMES.get(x), line.getValue()));
                x++;
            }
        }
    }

    @Override
    public void sidebarApi$updateText() {
        Text sidebarTitle = this.sidebarApi$currentSidebar.getTitleFor((ServerPlayNetworkHandler) (Object) this);
        if (!sidebarTitle.equals(this.sidebarApi$title)) {
            this.sidebarApi$title = sidebarTitle;
            ScoreboardObjectiveUpdateS2CPacket packet = new ScoreboardObjectiveUpdateS2CPacket(SidebarAPIMod.SCOREBOARD_OBJECTIVE, 2);
            SOUS2CPacketAccessor accessor = (SOUS2CPacketAccessor) packet;
            accessor.setTitle(this.sidebarApi$title);
            this.sendPacket(packet);
        }

        int index = 0;

        for (SidebarLine line : this.sidebarApi$currentSidebar.getLinesFor((ServerPlayNetworkHandler) (Object) this)) {
            if (this.sidebarApi$lines[index] == null || !this.sidebarApi$lines[index].equals(line, (ServerPlayNetworkHandler) (Object) this)) {
                TeamS2CPacket packet = TeamS2CPacket.updateTeam(SidebarAPIMod.TEAMS.get(index),this.sidebarApi$lines[index] == null);
                ((SerializableTeamAccessor) packet.getTeam().get()).setPrefix(line.getText((ServerPlayNetworkHandler) (Object) this));
                this.sendPacket(packet);

                this.sendPacket(new ScoreboardPlayerUpdateS2CPacket(
                        ServerScoreboard.UpdateMode.CHANGE, SidebarAPIMod.OBJECTIVE_NAME, SidebarAPIMod.FAKE_PLAYER_NAMES.get(index), line.getValue()));

                this.sidebarApi$lines[index] = line.immutableCopy((ServerPlayNetworkHandler) (Object) this);
            }
            index++;
        }

        for (; index < this.sidebarApi$lines.length; index++) {
            if (this.sidebarApi$lines[index] != null) {
                this.sendPacket(new ScoreboardPlayerUpdateS2CPacket(
                        ServerScoreboard.UpdateMode.REMOVE, SidebarAPIMod.OBJECTIVE_NAME, SidebarAPIMod.FAKE_PLAYER_NAMES.get(index), 0));
                this.sendPacket(TeamS2CPacket.updateRemovedTeam(SidebarAPIMod.TEAMS.get(index)));
            }

            this.sidebarApi$lines[index] = null;
        }
    }

    @Override
    public void sidebarApi$updateState(boolean tick) {
        if (this.sidebarApi$currentSidebar == null) {
            this.sidebarApi$removeEmpty();
            return;
        }

        if (this.sidebarApi$alreadyHidden) {
            this.sidebarApi$setupInitial();
        } else if (tick && !this.sidebarApi$currentSidebar.manualTextUpdates()) {
            if (this.sidebarApi$currentTick % this.sidebarApi$currentSidebar.getUpdateRate() != 0) {
                this.sidebarApi$currentTick++;
                return;
            }

            this.sidebarApi$updateText();

            this.sidebarApi$currentTick++;
        }
    }

    @Override
    public void sidebarApi$add(SidebarInterface sidebar) {
        this.sidebarApi$sidebars.add(sidebar);
        this.sidebarApi$update(sidebar);
    }

    @Override
    public void sidebarApi$remove(SidebarInterface sidebar) {
        this.sidebarApi$sidebars.remove(sidebar);
        if (sidebar == this.sidebarApi$currentSidebar) {
            SidebarInterface newSidebar = null;
            for (var sidebar1 : this.sidebarApi$sidebars) {
                if (newSidebar == null || newSidebar.getPriority().isLowerThan(sidebar1.getPriority())) {
                    newSidebar = sidebar1;
                }
            }
            this.sidebarApi$currentSidebar = newSidebar;
        }
    }

    @Override
    public Set<SidebarInterface> sidebarApi$getAll() {
        return Collections.unmodifiableSet(this.sidebarApi$sidebars);
    }

    @Override
    public SidebarInterface sidebarApi$getCurrent() {
        return this.sidebarApi$currentSidebar;
    }

    @Override
    public void sidebarApi$update(SidebarInterface candidate) {
        if (this.sidebarApi$currentSidebar != null) {
            if (this.sidebarApi$currentSidebar.getPriority().isLowerThan(candidate.getPriority())) {
                this.sidebarApi$currentSidebar = candidate;
            }
        } else {
            this.sidebarApi$currentSidebar = candidate;
        }
    }

    @Override
    public void sidebarApi$clear() {
        this.sidebarApi$sidebars.clear();
        this.sidebarApi$currentSidebar = null;
    }
}
