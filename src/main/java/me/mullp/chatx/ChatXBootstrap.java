package me.mullp.chatx;

import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import me.mullp.chatx.commands.ChatXCommand;

public class ChatXBootstrap implements PluginBootstrap {
    @Override
    public void bootstrap(BootstrapContext context) {
        context.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS,
                commands -> commands.registrar().register(new ChatXCommand().getCommand()));
    }
}
