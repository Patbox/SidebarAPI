package eu.pb4.sidebars.mixin;


import net.minecraft.network.packet.s2c.play.ScoreboardDisplayS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ScoreboardDisplayS2CPacket.class)
public interface ScoreboardDisplayS2CPacketAccessor {
    @Mutable
    @Accessor("name")
    void setName(String name);
}
