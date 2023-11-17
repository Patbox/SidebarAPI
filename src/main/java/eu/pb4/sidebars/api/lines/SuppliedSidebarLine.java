package eu.pb4.sidebars.api.lines;


import net.minecraft.scoreboard.number.BlankNumberFormat;
import net.minecraft.scoreboard.number.NumberFormat;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class SuppliedSidebarLine extends AbstractSidebarLine {
    private final Function<ServerPlayerEntity, Text> func;
    private final Function<ServerPlayerEntity, NumberFormat> formatFunc;

    public SuppliedSidebarLine(int value, Function<ServerPlayerEntity, Text> func) {
        this(value, func, (t) -> null);
    }

    public SuppliedSidebarLine(int value, Function<ServerPlayerEntity, Text> func, Function<ServerPlayerEntity, @Nullable NumberFormat> numberFormat) {
        this.value = value;
        this.func = func;
        this.formatFunc = numberFormat;
    }

    @Nullable
    @Override
    public NumberFormat getNumberFormat(ServerPlayNetworkHandler handler) {
        return this.formatFunc.apply(handler.player);
    }

    public Text getText(ServerPlayNetworkHandler handler) {
        return this.func.apply(handler.player);
    }
}
