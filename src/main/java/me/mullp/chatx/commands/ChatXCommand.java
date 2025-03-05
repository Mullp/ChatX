package me.mullp.chatx.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import me.mullp.chatx.ChatX;
import me.mullp.chatx.tags.TabCompletions;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;

public class ChatXCommand {
    private final LiteralCommandNode<CommandSourceStack> command = Commands.literal("chatx")
            .then(Commands.literal("reload")
                    .requires(sender -> sender.getSender().hasPermission("chatx.reload"))
                    .executes(this::reload))
            .then(Commands.literal("about").executes(context -> {
                context.getSource().getSender().sendMessage("Version 1.0.0");

                return Command.SINGLE_SUCCESS;
            }))
            .build();

    public LiteralCommandNode<CommandSourceStack> getCommand() {
        return command;
    }

    private int reload(CommandContext<CommandSourceStack> context) {
        ChatX.getInstance().reloadConfig();

        CommandSender sender = context.getSource().getSender();
        sender.sendMessage(Component.translatable("commands.reload.success"));

        TabCompletions tabCompletions = ChatX.getTabCompletions();
        tabCompletions.reloadCompletions();
        tabCompletions.setCompletions(sender.getServer().getOnlinePlayers());

        return Command.SINGLE_SUCCESS;
    }
}
