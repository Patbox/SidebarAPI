package eu.pb4.sidebars.mixin;

import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetPlayerTeamPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ClientboundSetPlayerTeamPacket.Parameters.class)
public interface SerializableTeamAccessor {
    @Mutable
    @Accessor("playerPrefix")
    void setPrefix(Component text);
}
