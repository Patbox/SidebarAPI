package eu.pb4.sidebars.api;

import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.text.Text;

public interface SidebarLine {
    int getValue();
    Text getText(ServerPlayNetworkHandler handler);
    SidebarLine copy();
}
