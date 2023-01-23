package eu.pb4.sidebars.api;

import eu.pb4.sidebars.api.lines.SidebarLine;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.text.Text;

import java.util.*;


@SuppressWarnings({ "unused" })
public interface SidebarInterface {

    Sidebar.Priority getPriority();

    default int getUpdateRate() {
        return 1;
    }

    Text getTitleFor(ServerPlayNetworkHandler handler);

    boolean isDirty();

    List<SidebarLine> getLinesFor(ServerPlayNetworkHandler handler);

    boolean isActive();

    void disconnected(ServerPlayNetworkHandler handler);

    default boolean manualTextUpdates() {
        return false;
    }
}
