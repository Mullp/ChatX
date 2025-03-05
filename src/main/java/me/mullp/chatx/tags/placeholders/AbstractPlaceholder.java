package me.mullp.chatx.tags.placeholders;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractPlaceholder implements TagResolver.WithoutArguments {
    private final @NotNull String tag;
    private final @NotNull Component component;

    protected AbstractPlaceholder(@NotNull String tag, @NotNull Component component) {
        this.tag = tag;
        this.component = component;
    }

    @Override
    @Nullable
    public Tag resolve(@NotNull String name) {
        if (!name.equals(tag)) {
            return null;
        }

        return Tag.selfClosingInserting(component);
    }
}
