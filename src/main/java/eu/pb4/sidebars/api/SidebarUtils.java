package eu.pb4.sidebars.api;

import eu.pb4.sidebars.api.lines.LineBuilder;
import eu.pb4.sidebars.impl.SidebarHolder;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.text.Text;

import java.util.function.Consumer;

public final class SidebarUtils {
    private SidebarUtils() {};

    public static Sidebar create(Text text, Sidebar.Priority priority, Consumer<LineBuilder> lineBuilderConsumer) {
        var sidebar = new Sidebar(text, priority);
        sidebar.set(lineBuilderConsumer);
        return sidebar;
    }

    public static ScrollableSidebar createScrolling(Text text, Sidebar.Priority priority, int speed, Consumer<LineBuilder> lineBuilderConsumer) {
        var sidebar = new ScrollableSidebar(text, priority, speed);
        sidebar.set(lineBuilderConsumer);
        return sidebar;
    }

    public static void updateTexts(ServerPlayNetworkHandler handler, SidebarInterface sidebar) {
        if (isVisible(handler, sidebar)) {
            SidebarHolder.of(handler).sidebarApi$updateText();
        }
    }

    public static boolean isVisible(ServerPlayNetworkHandler handler, SidebarInterface sidebar) {
        return SidebarHolder.of(handler).sidebarApi$getCurrent() == sidebar;
    }

    public static void updatePriorities(ServerPlayNetworkHandler handler, SidebarInterface sidebar) {
        SidebarHolder.of(handler).sidebarApi$update(sidebar);
    }

    public static void requestStateUpdate(ServerPlayNetworkHandler handler) {
        SidebarHolder.of(handler).sidebarApi$updateState(false);
    }

    public static void addSidebar(ServerPlayNetworkHandler handler, SidebarInterface sidebar) {
        if (!SidebarHolder.of(handler).sidebarApi$getAll().contains(sidebar)) {
            SidebarHolder.of(handler).sidebarApi$add(sidebar);
        }
    }

    public static void removeSidebar(ServerPlayNetworkHandler handler, SidebarInterface sidebar) {
        if (SidebarHolder.of(handler).sidebarApi$getAll().contains(sidebar)) {
            SidebarHolder.of(handler).sidebarApi$remove(sidebar);
        }
    }
}
