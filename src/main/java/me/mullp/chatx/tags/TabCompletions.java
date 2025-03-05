package me.mullp.chatx.tags;

import me.mullp.chatx.ChatX;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

public class TabCompletions {
    private List<String> completions = List.of();

    public List<String> getCompletions() {
        return completions;
    }

    public void reloadCompletions() {
        FileConfiguration config = ChatX.getInstance().getConfig();

        completions = config.getMapList("placeholders").stream()
                .filter(map -> {
                    Object autocomplete = map.get("autocomplete");
                    if (autocomplete == null) {
                        return false;
                    }

                    return Boolean.parseBoolean(autocomplete.toString());
                })
                .map(map -> map.get("name").toString())
                .toList();
    }

    public void setCompletions(@NotNull Player player) {
        player.setCustomChatCompletions(completions);
    }

    public void setCompletions(Collection<? extends Player> players) {
        for (Player player : players) {
            setCompletions(player);
        }
    }
}
