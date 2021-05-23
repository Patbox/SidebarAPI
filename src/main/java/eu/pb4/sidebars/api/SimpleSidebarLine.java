package eu.pb4.sidebars.api;

import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.text.Text;

public class SimpleSidebarLine implements SidebarLine {
    private Text text;
    private int value;

    public SimpleSidebarLine(int value, Text text) {
        this.text = text;
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
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

    public SimpleSidebarLine copy() {
        return new SimpleSidebarLine(this.value, text.shallowCopy());
    }
}
