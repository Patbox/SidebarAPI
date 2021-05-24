package eu.pb4.sidebars.api;

import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Immutable version of SidebarLine used for comparison of change.
 */
public final class ImmutableSidebarLine implements SidebarLine {
    private final Text text;
    private final int value;

    public ImmutableSidebarLine(int value, Text text) {
        this.text = text;
        this.value = value;
    }

    @Override
    public int getValue() {
        return this.value;
    }

    @Override
    public Text getText(ServerPlayNetworkHandler handler) {
        return this.text;
    }

    @Override
    public void setSidebar(@Nullable Sidebar sidebar) {}

    public boolean equals(Object o, ServerPlayNetworkHandler handler) {
        if (this == o) return true;
        if (o == null || !(o instanceof SidebarLine)) return false;
        SidebarLine that = (SidebarLine) o;
        return this.value == that.getValue() && that.getText(handler).equals(this.text);
    }
}
