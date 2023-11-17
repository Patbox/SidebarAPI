package eu.pb4.sidebars.impl;

import net.fabricmc.api.ModInitializer;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardCriterion;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.Team;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class SidebarAPIMod implements ModInitializer {
    public static final List<String> FAKE_PLAYER_NAMES = new ArrayList<>();
    public static final Scoreboard SCOREBOARD = new Scoreboard();
    public static final String OBJECTIVE_NAME = "■SidebarApiObj";
    public static final ScoreboardObjective SCOREBOARD_OBJECTIVE = new ScoreboardObjective(
            SCOREBOARD, OBJECTIVE_NAME, ScoreboardCriterion.DUMMY,
            Text.literal("Something went wrong..."), ScoreboardCriterion.RenderType.INTEGER, false, null);

    @Override
    public void onInitialize() {
        for (int x = 0; x < 15; x++) {
            String fakePlayerName = String.format("$pb§%s§r", Integer.toHexString(x));
            FAKE_PLAYER_NAMES.add(fakePlayerName);
        }
    }
}
