package com.cpunisher.onmyojibot.eventhandler;

import net.mamoe.mirai.event.events.GroupMessageEvent;

import java.util.List;

public interface IMessageEventHandler {

    List<String> getTriggerStrings();

    void trigger(GroupMessageEvent event);
}
