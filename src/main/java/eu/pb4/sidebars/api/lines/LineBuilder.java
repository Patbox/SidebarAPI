package eu.pb4.sidebars.api.lines;

import com.google.common.collect.ImmutableList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.numbers.FixedFormat;
import net.minecraft.network.chat.numbers.NumberFormat;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;


/**
 * Simple builder for making creation of Sidebars faster
 */
public class LineBuilder {
    protected List<SidebarLine> lines = new ArrayList<>();
    private final NumberFormat defaultNumberFormat;

    public LineBuilder(NumberFormat defaultNumberFormat) {
        this.defaultNumberFormat = defaultNumberFormat;
    }

    public LineBuilder() {
        this.defaultNumberFormat = null;
    }

    /**
     * Adds SimpleLineBuilder
     */
    public LineBuilder add(Component text) {
        return this.add(new SimpleSidebarLine(Integer.MIN_VALUE, text, this.defaultNumberFormat));
    }

    public LineBuilder add(Component left, Component right) {
        return this.add(new SimpleSidebarLine(Integer.MIN_VALUE, left, new FixedFormat(right)));
    }

    public LineBuilder add(Component left, NumberFormat format) {
        return this.add(new SimpleSidebarLine(Integer.MIN_VALUE, left, format));
    }

    /**
     * Adds SuppliedLineBuilder
     */
    public LineBuilder add(Function<ServerPlayer, Component> function) {
        var t = this.defaultNumberFormat;
        return this.add(new SuppliedSidebarLine(Integer.MIN_VALUE, function, (p) -> t));
    }

    public LineBuilder add(Function<ServerPlayer, Component> left, Function<ServerPlayer, @Nullable NumberFormat> right) {
        return this.add(new SuppliedSidebarLine(Integer.MIN_VALUE, left, right));
    }

    /**
     * Adds any mutable SidebarLine
     */
    public LineBuilder add(SidebarLine line) {
        if (!line.setValue(Integer.MIN_VALUE)) {
            throw new IllegalArgumentException("Line's value needs to be mutable!");
        }
        this.lines.add(line);
        return this;
    }

    /**
     * Sets line to SimpleLineBuilder
     */
    public LineBuilder set(int pos, Component text) {
        return this.set(pos, new SimpleSidebarLine(Integer.MIN_VALUE, text, this.defaultNumberFormat));
    }

    public LineBuilder set(int pos, Component left, NumberFormat format) {
        return this.set(pos, new SimpleSidebarLine(Integer.MIN_VALUE, left, format));
    }

    /**
     * Sets line to SuppliedLineBuilder
     */
    public LineBuilder set(int pos, Function<@Nullable ServerPlayer, Component> function) {
        var t = this.defaultNumberFormat;
        return this.set(pos, new SuppliedSidebarLine(Integer.MIN_VALUE, function, p -> t));

    }

    public LineBuilder set(int pos, Function<ServerPlayer, Component> left, Function<ServerPlayer, @Nullable NumberFormat> right) {
        return this.set(pos, new SuppliedSidebarLine(Integer.MIN_VALUE, left, right));
    }

    /**
     * Sets any mutable SidebarLine
     */
    public LineBuilder set(int pos, SidebarLine line) {
        if (!line.setValue(Integer.MIN_VALUE)) {
            throw new IllegalArgumentException("Line's value needs to be mutable!");
        }
        this.fillWithEmpty(pos);
        this.lines.set(pos, line);
        return this;
    }

    /**
     * Inserts SimpleLineBuilder
     */
    public LineBuilder insert(int pos, Component text) {
        return this.insert(pos, new SimpleSidebarLine(Integer.MIN_VALUE, text));

    }

    public LineBuilder insert(int pos, Component left, NumberFormat format) {
        return this.insert(pos, new SimpleSidebarLine(Integer.MIN_VALUE, left, format));
    }

    /**
     * Inserts SuppliedLineBuilder
     */
    public LineBuilder insert(int pos, Function<@Nullable ServerPlayer, Component> function) {
        return this.insert(pos, new SuppliedSidebarLine(Integer.MIN_VALUE, function));
    }

    public LineBuilder insert(int pos, Function<ServerPlayer, Component> left, Function<ServerPlayer, @Nullable NumberFormat> right) {
        return this.insert(pos, new SuppliedSidebarLine(Integer.MIN_VALUE, left, right));
    }

    /**
     * Inserts any mutable SidebarLine
     */
    public LineBuilder insert(int pos, SidebarLine line) {
        if (!line.setValue(Integer.MIN_VALUE)) {
            throw new IllegalArgumentException("Line's value needs to be mutable!");
        }
        this.fillWithEmpty(pos);
        this.lines.add(pos, line);
        return this;
    }

    protected void fillWithEmpty(int pos) {
        while (this.lines.size() < pos) {
            this.lines.add(SidebarLine.createEmpty(Integer.MIN_VALUE));
        }
    }


    /**
     * Creates immutable list of SidebarLines
     */
    public List<SidebarLine> getLines() {
        int size = this.lines.size();
        for (SidebarLine line : this.lines) {
            line.setValue(--size);
        }

        return ImmutableList.copyOf(this.lines);
    }
}
