package eu.pb4.sidebars.api.lines;

import eu.pb4.sidebars.api.Sidebar;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.text.Text;

/**
 * Simple implementation of SidebarLine
 */
public class SimpleSidebarLine extends AbstractSidebarLine {
    private Text text;

    public SimpleSidebarLine(int value, Text text) {
        this.text = text;
        this.value = value;
    }

    public SimpleSidebarLine(int value, Text text, Sidebar sidebar) {
        this(value, text);
        this.sidebar = sidebar;
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
}
