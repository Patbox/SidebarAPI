package eu.pb4.sidebars.api;

import eu.pb4.sidebars.api.lines.LineBuilder;
import eu.pb4.sidebars.impl.SidebarHolder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.network.ServerGamePacketListenerImpl;

import java.util.function.Consumer;

public final class SidebarUtils {
    private SidebarUtils() {};

    public static Sidebar create(Component text, Sidebar.Priority priority, Consumer<LineBuilder> lineBuilderConsumer) {
        var sidebar = new Sidebar(text, priority);
        sidebar.set(lineBuilderConsumer);
        return sidebar;
    }

    public static ScrollableSidebar createScrolling(Component text, Sidebar.Priority priority, int speed, Consumer<LineBuilder> lineBuilderConsumer) {
        var sidebar = new ScrollableSidebar(text, priority, speed);
        sidebar.set(lineBuilderConsumer);
        return sidebar;
    }

    public static void updateTexts(ServerGamePacketListenerImpl handler, SidebarInterface sidebar) {
        if (isVisible(handler, sidebar)) {
            SidebarHolder.of(handler).sidebarApi$updateText();
        }
    }

    public static boolean isVisible(ServerGamePacketListenerImpl handler, SidebarInterface sidebar) {
        return SidebarHolder.of(handler).sidebarApi$getCurrent() == sidebar;
    }

    public static void updatePriorities(ServerGamePacketListenerImpl handler, SidebarInterface sidebar) {
        SidebarHolder.of(handler).sidebarApi$update(sidebar);
    }

    public static void requestStateUpdate(ServerGamePacketListenerImpl handler) {
        SidebarHolder.of(handler).sidebarApi$updateState(false);
    }

    public static void addSidebar(ServerGamePacketListenerImpl handler, SidebarInterface sidebar) {
        if (!SidebarHolder.of(handler).sidebarApi$getAll().contains(sidebar)) {
            SidebarHolder.of(handler).sidebarApi$add(sidebar);
        }
    }

    public static void removeSidebar(ServerGamePacketListenerImpl handler, SidebarInterface sidebar) {
        if (SidebarHolder.of(handler).sidebarApi$getAll().contains(sidebar)) {
            SidebarHolder.of(handler).sidebarApi$remove(sidebar);
        }
    }
}
