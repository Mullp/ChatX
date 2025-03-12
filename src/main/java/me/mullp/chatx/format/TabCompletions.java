package me.mullp.chatx.format;

import me.mullp.chatx.ChatX;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class TabCompletions {
    private List<String> completions = List.of();

    public void reloadCompletions() {
        FileConfiguration config = ChatX.getInstance().getConfig();

        completions = config.getMapList("placeholders").stream()
                .filter(map -> Optional.ofNullable(map.get("auto-complete"))
                        .map(Object::toString)
                        .map(Boolean::parseBoolean)
                        .orElse(false))
                .map(map -> Objects.toString(map.get("name"), ""))
                .filter(name -> !name.isEmpty())
                .toList();
    }

    public void setCompletions(@NotNull Player player) {
        player.setCustomChatCompletions(completions);
    }

    public void setCompletions(@NotNull Collection<? extends Player> players) {
        players.forEach(this::setCompletions);
    }
}
