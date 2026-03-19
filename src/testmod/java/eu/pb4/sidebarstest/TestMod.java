package eu.pb4.sidebarstest;

import com.mojang.brigadier.context.CommandContext;
import eu.pb4.sidebars.api.ScrollableSidebar;
import eu.pb4.sidebars.api.Sidebar;
import eu.pb4.sidebars.api.lines.SidebarLine;
import eu.pb4.sidebars.impl.SidebarHolder;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FontDescription;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.numbers.BlankFormat;
import net.minecraft.network.chat.numbers.FixedFormat;
import net.minecraft.network.chat.numbers.StyledFormat;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.commands.Commands.literal;


public class TestMod implements ModInitializer {
    private static int test(CommandContext<CommandSourceStack> objectCommandContext) {
        try {
            ServerPlayer player = objectCommandContext.getSource().getPlayer();

            Sidebar sidebar = new ScrollableSidebar(Sidebar.Priority.MEDIUM, 10);
            boolean bool = Math.random() > 0.5;
            System.out.println(bool);
            sidebar.setTitle(Component.literal("Test Sidebar").setStyle(Style.EMPTY.withColor(bool ? ChatFormatting.GOLD : ChatFormatting.AQUA)));

            int value = (int) (30 + Math.random() * 4);
            System.out.println(value);
            for (int x = 0; x < value; x++) {
                sidebar.setLine(x, Component.literal("Hello World! " + (int) (Math.random() * 1000)).setStyle(
                        createStyle(
                                TextColor.fromRgb((int) (Math.random() * 0xFFFFFF)),
                                Math.random() > 0.5,
                                Math.random() > 0.5,
                                Math.random() > 0.5,
                                Math.random() > 0.5,
                                Math.random() > 0.5,
                                Math.random() > 0.6 ? Identifier.parse("default") : Math.random() > 0.3 ? Identifier.parse("uniform") : Identifier.parse("alt")
                        )
                ), new StyledFormat(createStyle(
                        TextColor.fromRgb((int) (Math.random() * 0xFFFFFF)),
                        Math.random() > 0.5,
                        Math.random() > 0.5,
                        Math.random() > 0.5,
                        Math.random() > 0.5,
                        Math.random() > 0.5,
                        Math.random() > 0.6 ? Identifier.parse("default") : Math.random() > 0.3 ? Identifier.parse("uniform") : Identifier.parse("alt")
                )));
            }

            sidebar.show();
            sidebar.addPlayer(player);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private static int test2(CommandContext<CommandSourceStack> objectCommandContext) {
        try {
            ServerPlayer player = objectCommandContext.getSource().getPlayer();

            boolean bool = Math.random() > 0.5;
            System.out.println(bool);
            Sidebar sidebar = new Sidebar(Sidebar.Priority.HIGH);
            sidebar.setTitle(Component.literal("Should Override").setStyle(Style.EMPTY.withColor(bool ? ChatFormatting.GOLD : ChatFormatting.AQUA)));

            StringBuilder builder = new StringBuilder();
            for (int x = 0; x < 40; x++) {
                builder.append("Hello World! ");
            }

            sidebar.setDefaultNumberFormat(BlankFormat.INSTANCE);

            sidebar.setLine(0, Component.literal(builder.toString()).setStyle(
                    createStyle(
                            TextColor.fromRgb((int) (Math.random() * 0xFFFFFF)),
                            Math.random() > 0.5,
                            Math.random() > 0.5,
                            Math.random() > 0.5,
                            Math.random() > 0.5,
                            Math.random() > 0.5,
                            
                            Math.random() > 0.6 ? Identifier.parse("default") : Math.random() > 0.3 ? Identifier.parse("uniform") : Identifier.parse("alt")
                    )
            ));

            sidebar.addPlayer(player);
            sidebar.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private static int test3(CommandContext<CommandSourceStack> objectCommandContext) {
        try {
            ServerPlayer player = objectCommandContext.getSource().getPlayer();

            Sidebar sidebar = new Sidebar(Sidebar.Priority.LOW);
            boolean bool = Math.random() > 0.5;
            System.out.println(bool);
            sidebar.setTitle(Component.literal("LOW").setStyle(Style.EMPTY.withColor(bool ? ChatFormatting.GOLD : ChatFormatting.AQUA)));

            sidebar.setLine(0, Component.literal("Hello World! " + (int) (Math.random() * 1000)).setStyle(
                    createStyle(
                            TextColor.fromRgb((int) (Math.random() * 0xFFFFFF)),
                            Math.random() > 0.5,
                            Math.random() > 0.5,
                            Math.random() > 0.5,
                            Math.random() > 0.5,
                            Math.random() > 0.5,
                            Math.random() > 0.6 ? Identifier.parse("default") : Math.random() > 0.3 ? Identifier.parse("uniform") : Identifier.parse("alt")
                    )
            ));
            int speed = (int) (Math.random() * 20);
            sidebar.setUpdateRate(speed);

            sidebar.addLines(SidebarLine.create(2, Component.literal("" + speed), new FixedFormat(Component.literal("<- Speed"))));

            sidebar.addLines(SidebarLine.create(2, (p) -> {
                System.out.println(p.tickCount);
                return Component.literal("" + p.tickCount);
            }));

            sidebar.addPlayer(player);
            sidebar.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    static Style createStyle(@Nullable TextColor color, @Nullable Boolean bold, @Nullable Boolean italic, @Nullable Boolean underlined, @Nullable Boolean strikethrough, @Nullable Boolean obfuscated, Identifier font) throws Exception {
        return Style.EMPTY.withColor(color).withBold(bold).withItalic(italic).withUnderlined(underlined).withStrikethrough(strikethrough).withObfuscated(obfuscated).withFont(new FontDescription.Resource(font));
    }

    private static int test4(CommandContext<CommandSourceStack> objectCommandContext) {
        try {
            ServerPlayer player = objectCommandContext.getSource().getPlayer();

            ((SidebarHolder) player.connection).sidebarApi$clear();

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
