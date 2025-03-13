package me.mullp.chatx.listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.mullp.chatx.ChatX;
import me.mullp.chatx.format.ChatFormatter;
import net.kyori.adventure.identity.Identity;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class ChatProximityListener implements Listener {
    private final static ChatX PLUGIN = ChatX.getInstance();

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerChat(AsyncChatEvent event) {
        FileConfiguration config = PLUGIN.getConfig();
        if (!config.getBoolean("proximity-chat")) {
            return;
        }

        String globalChatPrefix = config.getString("global-chat-prefix", "");
        if (!globalChatPrefix.isBlank() && ChatFormatter.getPlainTextSerializer()
                .serialize(event.originalMessage())
                .startsWith(globalChatPrefix)) {
            event.message(event.message().replaceText(builder -> builder
                    .matchLiteral("!")
                    .replacement("")
                    .once()));

            return;
        }

        double range = config.getDouble("proximity-range");
        if (range == 0) {
            return;
        }

        Player source = event.getPlayer();

        event.viewers().removeIf(audience ->
                audience.get(Identity.UUID)
                        .map(uuid -> source.getServer().getPlayer(uuid))
                        .filter(player -> player.getLocation().distanceSquared(source.getLocation()) >
                                range * range)
                        .isPresent()
        );
    }
}
