package eu.pb4.sidebars.api;

import eu.pb4.sidebars.api.lines.SidebarLine;
import net.minecraft.network.chat.Component;
import net.minecraft.server.network.ServerGamePacketListenerImpl;

import java.util.*;


@SuppressWarnings({ "unused" })
public interface SidebarInterface {

    Sidebar.Priority getPriority();

    default int getUpdateRate() {
        return 1;
    }

    Component getTitleFor(ServerGamePacketListenerImpl handler);

    boolean isDirty();

    List<SidebarLine> getLinesFor(ServerGamePacketListenerImpl handler);

    boolean isActive();

    void disconnected(ServerGamePacketListenerImpl handler);

    default boolean manualTextUpdates() {
        return false;
    }
}
