package me.mullp.chatx.listeners;

import io.papermc.paper.event.player.AsyncChatDecorateEvent;
import me.mullp.chatx.ChatX;
import me.mullp.chatx.format.ChatFormatter;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;

public class ChatMentionListener implements Listener {
    @EventHandler
    public void onChatMention(@NotNull AsyncChatDecorateEvent event) {
        Player source = event.player();
        if (source == null) {
            return;
        }

        FileConfiguration config = ChatX.getInstance().getConfig();
        String format = config.getString("mentions-format");
        if (format == null) {
            return;
        }

        Component result = event.result();

        String plainMessage = ChatFormatter.getPlainTextSerializer().serialize(event.originalMessage());
        List<? extends Player> players = source.getServer().getOnlinePlayers().stream()
                .sorted(Comparator.comparingInt((Player p) -> p.getName().length()).reversed())
                .toList();

        for (Player player : players) {
            if (!plainMessage.contains(player.getName())) {
                continue;
            }

            player.playSound(Sound.sound(Key.key("block.note_block.pling"),
                    Sound.Source.NEUTRAL,
                    1f,
                    1f));

            result = result.replaceText(builder -> builder
                    .matchLiteral(player.getName())
                    .replacement(ChatFormatter.deserializePlaceholders(format, player)));
        }

        event.result(result);
    }
}
