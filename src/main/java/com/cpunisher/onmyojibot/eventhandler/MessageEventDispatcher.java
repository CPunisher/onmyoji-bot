package com.cpunisher.onmyojibot.eventhandler;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class MessageEventDispatcher implements Consumer<GroupMessageEvent> {

    private static int id = 0;
    private static final Map<Integer, IMessageEventHandler> handlerMap = new HashMap<>();

    private static void register(IMessageEventHandler eventHandler) {
        handlerMap.put(id++, eventHandler);
    }

    public static void registerAll() {
        register(new DrawCardEventHandler(1, "单抽"));
        register(new DrawCardEventHandler(10, "十连"));
        register(new ShowDrawResultEventHandler());
    }

    @Override
    public void accept(GroupMessageEvent event) {
        MessageChain msg = event.getMessage();
        Bot bot = event.getBot();
        boolean isCalled = msg.stream().anyMatch(singleMessage -> singleMessage instanceof At && ((At) singleMessage).getTarget() == bot.getId());
        bot.getLogger().debug("Is called: " + isCalled);
        if (isCalled) {
            msg.stream().filter(PlainText.class::isInstance).findFirst().map(PlainText.class::cast).ifPresent(plainText -> {
                bot.getLogger().debug("plain text: " + plainText);
                handlerMap.values()
                        .stream()
                        .filter(eventHandler -> eventHandler.getTriggerStrings()
                                .stream()
                                .anyMatch(str -> plainText.getContent().contains(str)))
                        .forEach(eventHandler -> eventHandler.trigger(event));
            });
        }
    }
}
