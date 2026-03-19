package eu.pb4.sidebars.api.lines;

import eu.pb4.sidebars.api.Sidebar;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.numbers.BlankFormat;
import net.minecraft.network.chat.numbers.NumberFormat;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
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
     * Gets number format for selected player
     */
    @Nullable
    NumberFormat getNumberFormat(ServerGamePacketListenerImpl handler);

    /**
     * Gets text for selected player. Uses
     *
     * @param handler Player's ServerGamePacketListenerImpl
     * @return Component displayed for player
     */
    Component getText(ServerGamePacketListenerImpl handler);

    /**
     * Creates a static, immutable copy of this SidebarLine, that's used for comparing
     */
    default ImmutableSidebarLine immutableCopy(ServerGamePacketListenerImpl handler) {
        return new ImmutableSidebarLine(this.getValue(), this.getText(handler).copy(), this.getNumberFormat(handler));
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
    static SidebarLine create(int value, Function<@Nullable ServerPlayer, Component> textBuilder) {
        return new SuppliedSidebarLine(value, textBuilder);
    }

    /**
     * Quick way to create SidebarLine instance with more advanced text building
     *
     * @param value Scoreboard value
     * @param text Text
     * @return SidebarLine
     */
    static SidebarLine create(int value, Component text) {
        return new SimpleSidebarLine(value, text);
    }

    static SidebarLine create(int value, Component text, NumberFormat format) {
        return new SimpleSidebarLine(value, text, format);
    }

    /**
     * Quick way to create SidebarLine instance with more advanced text building
     *
     * @param value Scoreboard value
     * @return SidebarLine
     */
    static SidebarLine createEmpty(int value) {
        AbstractSidebarLine abstractSidebarLine = new AbstractSidebarLine() {
            @Override
            public Component getText(ServerGamePacketListenerImpl handler) {
                return Component.empty();
            }

            @Override
            public NumberFormat getNumberFormat(ServerGamePacketListenerImpl handler) {
                return BlankFormat.INSTANCE;
            }
        };
        abstractSidebarLine.setValue(value);
        return abstractSidebarLine;
    }
}
