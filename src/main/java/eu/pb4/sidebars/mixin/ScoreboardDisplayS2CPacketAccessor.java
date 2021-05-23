package eu.pb4.sidebars.mixin;


import net.minecraft.network.packet.s2c.play.ScoreboardDisplayS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ScoreboardDisplayS2CPacket.class)
public interface ScoreboardDisplayS2CPacketAccessor {
    @Accessor("slot")
    void setSlot(int slot);

    @Accessor("name")
    void setName(String name);
}
