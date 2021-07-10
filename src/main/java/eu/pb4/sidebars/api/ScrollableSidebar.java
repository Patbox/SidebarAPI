package eu.pb4.sidebars.api;

import eu.pb4.sidebars.api.lines.SidebarLine;
import it.unimi.dsi.fastutil.objects.Object2LongArrayMap;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.text.Text;

import java.util.List;

public class ScrollableSidebar extends Sidebar {
    protected Object2LongMap<ServerPlayNetworkHandler> position = new Object2LongArrayMap<>();
    protected int scrollTickNumber;

    public ScrollableSidebar(Priority priority, int scrollTickNumber) {
        super(priority);
        this.scrollTickNumber = scrollTickNumber;
    }

    public ScrollableSidebar(Text title, Priority priority, int scrollTickNumber) {
        super(title, priority);
        this.scrollTickNumber = scrollTickNumber;
    }

    public int getTicksPerLine() {
        return this.scrollTickNumber;
    }

    public void setTicksPerLine(int scrollTickNumber) {
        this.scrollTickNumber = scrollTickNumber;
    }

    @Override
    public List<SidebarLine> getLinesFor(ServerPlayNetworkHandler handler) {
        this.sortIfDirty();
        long pos = this.position.getLong(handler);
        pos++;
        int index = (int) pos / scrollTickNumber;

        if (index + 14 > this.elements.size()) {
            pos = 0;
            index = 0;
        }

        this.position.put(handler, pos);

        return this.elements.subList(index, Math.min(index + 14, this.elements.size()));
    }

    @Override
    public void removePlayer(ServerPlayNetworkHandler handler) {
        super.removePlayer(handler);
        this.position.removeLong(handler);
    }
}
