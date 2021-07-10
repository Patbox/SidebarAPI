package eu.pb4.sidebars.api.lines;

import eu.pb4.sidebars.api.Sidebar;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

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
     * Changes value of sidebar line. Used by {@Code LineBuilder}
     */
    boolean setValue(int value);

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
    static SidebarLine create(int value, Function<@Nullable ServerPlayerEntity, Text> textBuilder) {
        return new SuppliedSidebarLine(value, textBuilder);
    }

    /**
     * Quick way to create SidebarLine instance with more advanced text building
     *
     * @param value Scoreboard value
     * @param text Text
     * @return SidebarLine
     */
    static SidebarLine create(int value, Text text) {
        return new SimpleSidebarLine(value, text);
    }

    /**
     * Quick way to create SidebarLine instance with more advanced text building
     *
     * @param value Scoreboard value
     * @return SidebarLine
     */
    static SidebarLine createEmpty(int value) {
        return new AbstractSidebarLine() {
            @Override
            public Text getText() {
                return LiteralText.EMPTY;
            }
        };
    }
}
