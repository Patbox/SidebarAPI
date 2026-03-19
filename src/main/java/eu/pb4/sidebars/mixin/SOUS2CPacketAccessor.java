package eu.pb4.sidebars.mixin;

import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetObjectivePacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ClientboundSetObjectivePacket.class)
public interface SOUS2CPacketAccessor {
    @Mutable
    @Accessor("displayName")
    void setTitle(Component name);
}
