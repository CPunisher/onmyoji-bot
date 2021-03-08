package com.cpunisher.onmyojibot;

import com.cpunisher.onmyojibot.database.Database;
import com.cpunisher.onmyojibot.eventhandler.MessageEventDispatcher;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.events.GroupMessageEvent;

public class PluginMain extends JavaPlugin {
    public static final PluginMain INSTANCE = new PluginMain();

    private PluginMain() {
        super(new JvmPluginDescriptionBuilder("com.cpunisher.onmyojibot", "1.0.0").author("CPunisher").build());
    }

    @Override
    public void onEnable() {
        this.reloadPluginConfig(OnmyojiBotConfig.INSTANCE);
        this.reloadPluginData(ShikigamiData.INSTANCE);
        MessageEventDispatcher.registerAll();
        Database.INSTANCE.connect();
        GlobalEventChannel.INSTANCE.subscribeAlways(GroupMessageEvent.class, new MessageEventDispatcher());
    }

    @Override
    public void onDisable() {
        Database.INSTANCE.close();
    }
}
