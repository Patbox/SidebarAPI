package eu.pb4.sidebarstest;

import com.mojang.brigadier.context.CommandContext;
import eu.pb4.sidebars.api.ScrollableSidebar;
import eu.pb4.sidebars.api.Sidebar;
import eu.pb4.sidebars.api.lines.SidebarLine;
import eu.pb4.sidebars.interfaces.SidebarHolder;
import eu.pb4.sidebarstest.mixin.StyleAccessor;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import static net.minecraft.server.command.CommandManager.literal;

public class TestMod implements ModInitializer {
    private static int test(CommandContext<ServerCommandSource> objectCommandContext) {
        try {
            ServerPlayerEntity player = objectCommandContext.getSource().getPlayer();

            Sidebar sidebar = new ScrollableSidebar(Sidebar.Priority.MEDIUM, 10);
            boolean bool = Math.random() > 0.5;
            System.out.println(bool);
            sidebar.setTitle(Text.literal("Test Sidebar").setStyle(Style.EMPTY.withColor(bool ? Formatting.GOLD : Formatting.AQUA)));

            int value = (int) (30 + Math.random() * 4);
            System.out.println(value);
            for (int x = 0; x < value; x++) {
                sidebar.setLine(x, Text.literal("Hello World! " + (int) (Math.random() * 1000)).setStyle(
                        StyleAccessor.invokeInit(
                                TextColor.fromRgb((int) (Math.random() * 0xFFFFFF)),
                                Math.random() > 0.5,
                                Math.random() > 0.5,
                                Math.random() > 0.5,
                                Math.random() > 0.5,
                                Math.random() > 0.5,
                                null,
                                null,
                                null,
                                Math.random() > 0.6 ? new Identifier("default") : Math.random() > 0.3 ? new Identifier("uniform") : new Identifier("alt")
                                )
                ));
            }

            sidebar.show();
            sidebar.addPlayer(player);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private static int test2(CommandContext<ServerCommandSource> objectCommandContext) {
        try {
            ServerPlayerEntity player = objectCommandContext.getSource().getPlayer();

            boolean bool = Math.random() > 0.5;
            System.out.println(bool);
            Sidebar sidebar = new Sidebar(Sidebar.Priority.HIGH);
            sidebar.setTitle(Text.literal("Should Override").setStyle(Style.EMPTY.withColor(bool ? Formatting.GOLD : Formatting.AQUA)));

            StringBuilder builder = new StringBuilder();
            for (int x = 0; x < 40; x++) {
                builder.append("Hello World! ");
            }

            sidebar.setLine(0, Text.literal(builder.toString()).setStyle(
                    StyleAccessor.invokeInit(
                            TextColor.fromRgb((int) (Math.random() * 0xFFFFFF)),
                            Math.random() > 0.5,
                            Math.random() > 0.5,
                            Math.random() > 0.5,
                            Math.random() > 0.5,
                            Math.random() > 0.5,
                            null,
                            null,
                            null,
                            Math.random() > 0.6 ? new Identifier("default") : Math.random() > 0.3 ? new Identifier("uniform") : new Identifier("alt")
                    )
            ));

            sidebar.addPlayer(player);
            sidebar.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private static int test3(CommandContext<ServerCommandSource> objectCommandContext) {
        try {
            ServerPlayerEntity player = objectCommandContext.getSource().getPlayer();

            Sidebar sidebar = new Sidebar(Sidebar.Priority.LOW);
            boolean bool = Math.random() > 0.5;
            System.out.println(bool);
            sidebar.setTitle(Text.literal("LOW").setStyle(Style.EMPTY.withColor(bool ? Formatting.GOLD : Formatting.AQUA)));

            sidebar.setLine(0, Text.literal("Hello World! " + (int) (Math.random() * 1000)).setStyle(
                    StyleAccessor.invokeInit(
                            TextColor.fromRgb((int) (Math.random() * 0xFFFFFF)),
                            Math.random() > 0.5,
                            Math.random() > 0.5,
                            Math.random() > 0.5,
                            Math.random() > 0.5,
                            Math.random() > 0.5,
                            null,
                            null,
                            null,
                            Math.random() > 0.6 ? new Identifier("default") : Math.random() > 0.3 ? new Identifier("uniform") : new Identifier("alt")
                    )
            ));
            int speed = (int) (Math.random() * 20);
            sidebar.setUpdateRate(speed);

            sidebar.addLines(SidebarLine.create(2, Text.literal("" + speed)));

            sidebar.addLines(SidebarLine.create(2, (p) -> {
                System.out.println(p.age);
                return Text.literal("" + p.age);
            }));

            sidebar.addPlayer(player);
            sidebar.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private static int test4(CommandContext<ServerCommandSource> objectCommandContext) {
        try {
            ServerPlayerEntity player = objectCommandContext.getSource().getPlayer();

            ((SidebarHolder) player.networkHandler).clearSidebars();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(
                    literal("test").executes(TestMod::test)
            );
            dispatcher.register(
                    literal("test2").executes(TestMod::test2)
            );
            dispatcher.register(
                    literal("test3").executes(TestMod::test3)
            );
            dispatcher.register(
                    literal("test4").executes(TestMod::test4)
            );
        });
    }

}
