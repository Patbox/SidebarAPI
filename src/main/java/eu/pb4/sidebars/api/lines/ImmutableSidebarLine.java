package eu.pb4.sidebars.api.lines;

import eu.pb4.sidebars.api.Sidebar;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.numbers.NumberFormat;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Immutable version of SidebarLine used for comparison of change.
 */
public record ImmutableSidebarLine(int value, Component text, @Nullable NumberFormat format) implements SidebarLine {

    @Override
    public int getValue() {
        return this.value;
    }

    @Override
    public boolean setValue(int value) {
        return false;
    }

    @Override
    public Component getText(ServerGamePacketListenerImpl handler) {
        return this.text;
    }

    @Nullable
    @Override
    public NumberFormat getNumberFormat(ServerGamePacketListenerImpl handler) {
        return format;
    }

    @Override
    public void setSidebar(@Nullable Sidebar sidebar) {}

    public boolean equals(Object o, ServerGamePacketListenerImpl handler) {
        if (this == o) return true;
        if (o == null || !(o instanceof SidebarLine)) return false;
        SidebarLine that = (SidebarLine) o;
        return this.value == that.getValue() && that.getText(handler).equals(this.text) && Objects.equals(this.format, that.getNumberFormat(handler));
    }
}
