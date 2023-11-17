package eu.pb4.sidebars.api.lines;

import eu.pb4.sidebars.api.Sidebar;
import net.minecraft.scoreboard.number.BlankNumberFormat;
import net.minecraft.scoreboard.number.NumberFormat;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

/**
 * Simple implementation of SidebarLine
 */
public class SimpleSidebarLine extends AbstractSidebarLine {
    private Text text;
    @Nullable
    private NumberFormat numberFormat;

    public SimpleSidebarLine(int value, Text text, @Nullable NumberFormat format) {
        this.text = text;
        this.value = value;
        this.numberFormat = format;
    }
    public SimpleSidebarLine(int value, Text text) {
        this(value, text, (NumberFormat) null);
    }

    public SimpleSidebarLine(int value, Text text, Sidebar sidebar) {
        this(value, text);
        this.sidebar = sidebar;
    }

    public SimpleSidebarLine(int value, Text text, NumberFormat numberFormat, Sidebar sidebar) {
        this(value, text, numberFormat);
        this.sidebar = sidebar;
    }

    public Text getText() {
        return this.text;
    }

    @Nullable
    @Override
    public NumberFormat getNumberFormat(ServerPlayNetworkHandler handler) {
        return getNumberFormat();
    }

    public void setNumberFormat(@Nullable NumberFormat numberFormat) {
        this.numberFormat = numberFormat;
    }
    @Nullable
    public NumberFormat getNumberFormat() {
        return this.numberFormat;
    }

    public Text getText(ServerPlayNetworkHandler handler) {
        return this.getText();
    }

    public void setText(Text text) {
        this.text = text;
    }
}
