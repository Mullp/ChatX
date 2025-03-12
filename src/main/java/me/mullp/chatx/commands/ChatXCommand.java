package me.mullp.chatx.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import me.mullp.chatx.ChatX;
import me.mullp.chatx.format.ChatTabCompletions;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ChatXCommand {
    private final LiteralCommandNode<CommandSourceStack> command = Commands.literal("chatx")
            .requires(sender -> sender.getSender().hasPermission("chatx.commands"))
            .then(Commands.literal("reload")
                    .requires(sender -> sender.getSender().hasPermission("chatx.commands.reload"))
                    .executes(this::reload))
            .build();

    public LiteralCommandNode<CommandSourceStack> getCommand() {
        return command;
    }

    private int reload(@NotNull CommandContext<CommandSourceStack> context) {
        ChatX.getInstance().reloadConfig();

        CommandSender sender = context.getSource().getSender();
        sender.sendMessage(Component.text("Reloading ChatX!"));

        ChatTabCompletions tabCompletions = ChatX.getTabCompletions();
        tabCompletions.reloadChatCompletions();
        tabCompletions.setChatCompletions(sender.getServer().getOnlinePlayers());

        return Command.SINGLE_SUCCESS;
    }
}
