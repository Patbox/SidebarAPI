package eu.pb4.sidebars.api.lines;

import eu.pb4.sidebars.api.Sidebar;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.text.Text;

public abstract class AbstractSidebarLine implements SidebarLine {
    protected int value;
    protected Sidebar sidebar;

    public int getValue() {
        return this.value;
    }

    public boolean setValue(int value) {
        this.value = value;
        if (this.sidebar != null) {
            this.sidebar.markDirty();
        }
        return true;
    }

    public abstract Text getText();

    public Text getText(ServerPlayNetworkHandler handler) {
        return this.getText();
    }

    public void setSidebar(Sidebar sidebar) {
        this.sidebar = sidebar;
    }
}
