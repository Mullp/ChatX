package me.mullp.chatx.listeners;

import io.papermc.paper.event.player.AsyncChatDecorateEvent;
import me.mullp.chatx.ChatX;
import me.mullp.chatx.format.ChatFormatter;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

public class ChatDecorateListener implements Listener {
    @EventHandler
    public void onChatDecorate(@NotNull AsyncChatDecorateEvent event) {
        event.result(event.result()
                .replaceText(builder -> {
                    Player source = event.player();
                    if (source == null) {
                        return;
                    }

                    FileConfiguration config = ChatX.getInstance().getConfig();
                    String format = config.getString("mentions-format");

                    if (format == null) {
                        return;
                    }

                    String plainMessage = ChatFormatter.getPlainTextSerializer().serialize(event.originalMessage());
                    for (Player player : source.getServer().getOnlinePlayers().stream()
                            .sorted(Comparator.comparingInt((Player p) -> p.getName().length()).reversed())
                            .toList()) {
                        if (!plainMessage.contains(player.getName())) {
                            continue;
                        }

                        player.playSound(Sound.sound(Key.key("block.note_block.pling"), Sound.Source.NEUTRAL, 1f, 1f));

                        builder.matchLiteral(player.getName())
                                .replacement(ChatFormatter.deserializePlaceholders(format, player));
                    }
                }));
    }
}
