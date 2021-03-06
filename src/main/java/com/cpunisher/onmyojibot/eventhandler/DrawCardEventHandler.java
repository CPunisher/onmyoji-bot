package com.cpunisher.onmyojibot.eventhandler;

import com.cpunisher.onmyojibot.ShikigamiData;
import com.cpunisher.onmyojibot.util.DrawHelper;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.PlainText;

import java.util.Arrays;
import java.util.List;

public class DrawCardEventHandler implements IMessageEventHandler {
    @Override
    public List<String> getTriggerStrings() {
        return Arrays.asList("十连");
    }

    @Override
    public void trigger(GroupMessageEvent event) {
        List<ShikigamiData.Shikigami> result = DrawHelper.draw(10);
        MessageChainBuilder builder = new MessageChainBuilder()
                .append(new At(event.getSender().getId()))
                .append(new PlainText(" 十连结果\n"));

        for (ShikigamiData.Shikigami shikigami : result) {
            builder.append(shikigami.getName()).append(" ");
        }
        event.getSubject().sendMessage(builder.build());
    }
}
