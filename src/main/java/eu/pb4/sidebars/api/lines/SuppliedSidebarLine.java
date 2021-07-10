package eu.pb4.sidebars.api.lines;


import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class SuppliedSidebarLine extends AbstractSidebarLine {
    private final Function<ServerPlayerEntity, Text> func;

    public SuppliedSidebarLine(int value, Function<@Nullable ServerPlayerEntity, Text> func) {
        this.value = value;
        this.func = func;
    }

    public Text getText() {
        return this.func.apply(null);
    }

    public Text getText(ServerPlayNetworkHandler handler) {
        return this.func.apply(handler.player);
    }
}
