package eu.pb4.sidebars;

import net.fabricmc.api.ModInitializer;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardCriterion;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.Team;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class SidebarAPIMod implements ModInitializer {
    public static final List<Team> TEAMS = new ArrayList<>();
    public static final List<String> FAKE_PLAYER_NAMES = new ArrayList<>();
    public static final Scoreboard SCOREBOARD = new Scoreboard();
    public static final String OBJECTIVE_NAME = "■SidebarApiObj";
    public static final String TEAM_NAME = "■SidebarApi-";
    public static final ScoreboardObjective SCOREBOARD_OBJECTIVE = new ScoreboardObjective(SCOREBOARD, OBJECTIVE_NAME, ScoreboardCriterion.DUMMY, Text.literal("Something went wrong..."), ScoreboardCriterion.RenderType.INTEGER);

    @Override
    public void onInitialize() {
        for (int x = 0; x < 15; x++) {
            String fakePlayerName = String.format("§%s§r", Integer.toHexString(x));
            Team team = new Team(SCOREBOARD, TEAM_NAME + x);
            SCOREBOARD.addPlayerToTeam(fakePlayerName, team);

            TEAMS.add(team);
            FAKE_PLAYER_NAMES.add(fakePlayerName);
        }
    }
}
