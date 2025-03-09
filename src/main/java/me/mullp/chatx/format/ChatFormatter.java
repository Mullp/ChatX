package me.mullp.chatx.format;

import me.clip.placeholderapi.PlaceholderAPI;
import me.mullp.chatx.ChatX;
import me.mullp.chatx.format.placeholders.ItemPlaceholder;
import me.mullp.chatx.format.placeholders.MessagePlaceholder;
import me.mullp.chatx.format.placeholders.NamePlaceholder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.standard.StandardTags;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ChatFormatter {
    private static final MiniMessage MINI_MESSAGE = MiniMessage.builder()
            .tags(StandardTags.defaults())
            .build();
    private static final PlainTextComponentSerializer PLAIN_TEXT_SERIALIZER = PlainTextComponentSerializer.plainText();

    public static PlainTextComponentSerializer getPlainTextSerializer() {
        return PLAIN_TEXT_SERIALIZER;
    }

    @NotNull
    public static Component deserializeMessage(@NotNull String string,
                                               @NotNull Player player,
                                               @NotNull Component message) {
        if (ChatX.getInstance().getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            string = PlaceholderAPI.setPlaceholders(player, string);
        }

        return MINI_MESSAGE.deserialize(string,
                new NamePlaceholder(player),
                new ItemPlaceholder(player),
                new MessagePlaceholder(message));
    }

    /**
     * Deserializes most of the placeholders that only needs information about the player.
     * Currently, this is the {@link NamePlaceholder} and {@link ItemPlaceholder}.
     * 
     * @param string The string to deserialize
     * @param player The relevant player
     * @return The deserialized string as a component
     */
    @NotNull
    public static Component deserializePlaceholders(@NotNull String string, @NotNull Player player) {
        if (ChatX.getInstance().getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            string = PlaceholderAPI.setPlaceholders(player, string);
        }

        return MINI_MESSAGE.deserialize(string,
                new NamePlaceholder(player),
                new ItemPlaceholder(player.getInventory().getItemInMainHand()));
    }
}
