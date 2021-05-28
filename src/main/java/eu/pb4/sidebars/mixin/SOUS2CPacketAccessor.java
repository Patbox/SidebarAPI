package eu.pb4.sidebars.mixin;

import net.minecraft.network.packet.s2c.play.ScoreboardObjectiveUpdateS2CPacket;
import net.minecraft.scoreboard.ScoreboardCriterion;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ScoreboardObjectiveUpdateS2CPacket.class)
public interface SOUS2CPacketAccessor {
    @Mutable
    @Accessor("displayName")
    void setTitle(Text name);
}
