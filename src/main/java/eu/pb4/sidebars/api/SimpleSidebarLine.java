package eu.pb4.sidebars.api;

import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.text.Text;

/**
 * Simple implementation of SidebarLine
 */
public class SimpleSidebarLine implements SidebarLine {
    private Text text;
    private int value;
    private Sidebar sidebar;

    public SimpleSidebarLine(int value, Text text) {
        this.text = text;
        this.value = value;
    }

    protected SimpleSidebarLine(int value, Text text, Sidebar sidebar) {
        this(value, text);
        this.sidebar = sidebar;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
        if (this.sidebar != null) {
            this.sidebar.markDirty();
        }
    }

    public Text getText() {
        return this.text;
    }

    public Text getText(ServerPlayNetworkHandler handler) {
        return this.getText();
    }

    public void setText(Text text) {
        this.text = text;
    }

    public void setSidebar(Sidebar sidebar) {
        this.sidebar = sidebar;
    }
}
