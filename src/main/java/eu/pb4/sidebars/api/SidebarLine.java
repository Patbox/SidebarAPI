package eu.pb4.sidebars.api;

import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

/**
 * Minimalistic interface for creating own SidebarLines
 *
 */
public interface SidebarLine {
    /**
     * Returns score of this line (used for ordering)
     * Highest number is always on top
     */
    int getValue();

    /**
     * Gets text for selected player. Uses
     *
     * @param handler Player's ServerPlayNetworkHandler
     * @return Text displayed for player
     */
    Text getText(ServerPlayNetworkHandler handler);

    /**
     * Creates a static, immutable copy of this SidebarLine, that's used for comparing
     */
    default ImmutableSidebarLine immutableCopy(ServerPlayNetworkHandler handler) {
        return new ImmutableSidebarLine(this.getValue(), this.getText(handler).shallowCopy());
    }

    /**
     * Sets active sidebar. Every SidebarLine should only have one sidebar!
     */
    void setSidebar(@Nullable Sidebar sidebar);

    /**
     * Quick way to create SidebarLine instance with more advanced text building
     *
     * @param value Scoreboard value
     * @param textBuilder Function creating text
     * @return SidebarLine 
     */
    static SidebarLine create(int value, GetText textBuilder) {
        return new SidebarLine() {
            @Override
            public int getValue() {
                return value;
            }

            @Override
            public Text getText(ServerPlayNetworkHandler handler) {
                return textBuilder.getText(handler);
            }

            @Override
            public void setSidebar(@Nullable Sidebar sidebar) {

            }
        };
    }

    @FunctionalInterface
    interface GetText {
        Text getText(ServerPlayNetworkHandler handler);
    }
}
