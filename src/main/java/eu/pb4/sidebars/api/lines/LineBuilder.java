package eu.pb4.sidebars.api.lines;

import com.google.common.collect.ImmutableList;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;


/**
 * Simple builder for making creation of Sidebars faster
 */
public class LineBuilder {
    protected List<SidebarLine> lines = new ArrayList<>();

    /**
     * Adds SimpleLineBuilder
     */
    public LineBuilder add(Text text) {
        return this.add(new SimpleSidebarLine(-1, text));

    }

    /**
     * Adds SuppliedLineBuilder
     */
    public LineBuilder add(Function<@Nullable ServerPlayerEntity, Text> function) {
        return this.add(new SuppliedSidebarLine(-1, function));
    }

    /**
     * Adds any mutable SidebarLine
     */
    public LineBuilder add(SidebarLine line) {
        if (!line.setValue(-1)) {
            throw new IllegalArgumentException("Line's value needs to be mutable!");
        }
        this.lines.add(line);
        return this;
    }

    /**
     * Sets line to SimpleLineBuilder
     */
    public LineBuilder set(int pos, Text text) {
        return this.set(pos, new SimpleSidebarLine(-1, text));
    }

    /**
     * Sets line to SuppliedLineBuilder
     */
    public LineBuilder set(int pos, Function<@Nullable ServerPlayerEntity, Text> function) {
        return this.set(pos, new SuppliedSidebarLine(-1, function));

    }

    /**
     * Sets any mutable SidebarLine
     */
    public LineBuilder set(int pos, SidebarLine line) {
        if (!line.setValue(-1)) {
            throw new IllegalArgumentException("Line's value needs to be mutable!");
        }
        this.fillWithEmpty(pos);
        this.lines.set(pos, line);
        return this;
    }

    /**
     * Inserts SimpleLineBuilder
     */
    public LineBuilder insert(int pos, Text text) {
        return this.insert(pos, new SimpleSidebarLine(-1, text));

    }

    /**
     * Inserts SuppliedLineBuilder
     */
    public LineBuilder insert(int pos, Function<@Nullable ServerPlayerEntity, Text> function) {
        return this.insert(pos, new SuppliedSidebarLine(-1, function));
    }

    /**
     * Inserts any mutable SidebarLine
     */
    public LineBuilder insert(int pos, SidebarLine line) {
        if (!line.setValue(-1)) {
            throw new IllegalArgumentException("Line's value needs to be mutable!");
        }
        this.fillWithEmpty(pos);
        this.lines.add(pos, line);
        return this;
    }

    protected void fillWithEmpty(int pos) {
        while (this.lines.size() < pos) {
            this.lines.add(SidebarLine.createEmpty(-1));
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
