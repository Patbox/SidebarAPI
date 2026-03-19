package eu.pb4.sidebars.mixin;


import net.minecraft.network.protocol.game.ClientboundSetDisplayObjectivePacket;
import net.minecraft.network.protocol.game.ClientboundSetScorePacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ClientboundSetDisplayObjectivePacket.class)
public interface ScoreboardDisplayS2CPacketAccessor {
    @Mutable
    @Accessor("objectiveName")
    void setName(String name);
}
