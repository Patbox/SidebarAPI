package eu.pb4.sidebars.api.lines;

import eu.pb4.sidebars.api.Sidebar;
import net.minecraft.scoreboard.number.NumberFormat;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Immutable version of SidebarLine used for comparison of change.
 */
public record ImmutableSidebarLine(int value, Text text, @Nullable NumberFormat format) implements SidebarLine {

    @Override
    public int getValue() {
        return this.value;
    }

    @Override
    public boolean setValue(int value) {
        return false;
    }

    @Override
    public Text getText(ServerPlayNetworkHandler handler) {
        return this.text;
    }

    @Nullable
    @Override
    public NumberFormat getNumberFormat(ServerPlayNetworkHandler handler) {
        return format;
    }

    @Override
    public void setSidebar(@Nullable Sidebar sidebar) {}

    public boolean equals(Object o, ServerPlayNetworkHandler handler) {
        if (this == o) return true;
        if (o == null || !(o instanceof SidebarLine)) return false;
        SidebarLine that = (SidebarLine) o;
        return this.value == that.getValue() && that.getText(handler).equals(this.text) && Objects.equals(this.format, that.getNumberFormat(handler));
    }
}
