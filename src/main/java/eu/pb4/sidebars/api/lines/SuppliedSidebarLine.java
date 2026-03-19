package eu.pb4.sidebars.api.lines;


import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.numbers.NumberFormat;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class SuppliedSidebarLine extends AbstractSidebarLine {
    private final Function<ServerPlayer, Component> func;
    private final Function<ServerPlayer, NumberFormat> formatFunc;

    public SuppliedSidebarLine(int value, Function<ServerPlayer, Component> func) {
        this(value, func, (t) -> null);
    }

    public SuppliedSidebarLine(int value, Function<ServerPlayer, Component> func, Function<ServerPlayer, @Nullable NumberFormat> numberFormat) {
        this.value = value;
        this.func = func;
        this.formatFunc = numberFormat;
    }

    @Nullable
    @Override
    public NumberFormat getNumberFormat(ServerGamePacketListenerImpl handler) {
        return this.formatFunc.apply(handler.player);
    }

    public Component getText(ServerGamePacketListenerImpl handler) {
        return this.func.apply(handler.player);
    }
}
