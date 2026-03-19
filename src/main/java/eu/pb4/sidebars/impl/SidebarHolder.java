package eu.pb4.sidebars.impl;

import eu.pb4.sidebars.api.SidebarInterface;
import net.minecraft.server.network.ServerGamePacketListenerImpl;

import java.util.Set;

public interface SidebarHolder {
    void sidebarApi$add(SidebarInterface sidebar);
    void sidebarApi$remove(SidebarInterface sidebar);
    void sidebarApi$clear();
    Set<SidebarInterface> sidebarApi$getAll();
    SidebarInterface sidebarApi$getCurrent();
    void sidebarApi$update(SidebarInterface candidate);
    void sidebarApi$removeEmpty();
    void sidebarApi$setupInitial();
    void sidebarApi$updateText();
    void sidebarApi$updateState(boolean tick);

    static SidebarHolder of(ServerGamePacketListenerImpl handler) {
        return (SidebarHolder) handler;
    }
}
