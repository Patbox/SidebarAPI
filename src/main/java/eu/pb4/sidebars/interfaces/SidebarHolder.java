package eu.pb4.sidebars.interfaces;

import eu.pb4.sidebars.api.Sidebar;

import java.util.Set;

public interface SidebarHolder {
    void addSidebar(Sidebar sidebar);
    void removeSidebar(Sidebar sidebar);
    void clearSidebars();
    Set<Sidebar> getSidebarSet();
    Sidebar getCurrentSidebar();
    void updateCurrentSidebar(Sidebar candidate);
}
