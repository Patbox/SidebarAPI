package eu.pb4.sidebars.api.lines;

import eu.pb4.sidebars.api.Sidebar;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.numbers.NumberFormat;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.jetbrains.annotations.Nullable;

/**
 * Simple implementation of SidebarLine
 */
public class SimpleSidebarLine extends AbstractSidebarLine {
    private Component text;
    @Nullable
    private NumberFormat numberFormat;

    public SimpleSidebarLine(int value, Component text, @Nullable NumberFormat format) {
        this.text = text;
        this.value = value;
        this.numberFormat = format;
    }
    public SimpleSidebarLine(int value, Component text) {
        this(value, text, (NumberFormat) null);
    }

    public SimpleSidebarLine(int value, Component text, Sidebar sidebar) {
        this(value, text);
        this.sidebar = sidebar;
    }

    public SimpleSidebarLine(int value, Component text, NumberFormat numberFormat, Sidebar sidebar) {
        this(value, text, numberFormat);
        this.sidebar = sidebar;
    }

    public Component getText() {
        return this.text;
    }

    @Nullable
    @Override
    public NumberFormat getNumberFormat(ServerGamePacketListenerImpl handler) {
        return getNumberFormat();
    }

    public void setNumberFormat(@Nullable NumberFormat numberFormat) {
        this.numberFormat = numberFormat;
    }
    @Nullable
    public NumberFormat getNumberFormat() {
        return this.numberFormat;
    }

    public Component getText(ServerGamePacketListenerImpl handler) {
        return this.getText();
    }

    public void setText(Component text) {
        this.text = text;
    }
}
