package com.cpunisher.onmyojibot.eventhandler;

import com.cpunisher.onmyojibot.PluginMain;
import com.cpunisher.onmyojibot.ShikigamiData;
import com.cpunisher.onmyojibot.util.DrawHelper;
import com.cpunisher.onmyojibot.util.ImageHelper;
import com.cpunisher.onmyojibot.util.ResourceHelper;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

        List<java.awt.Image> images = result.stream()
                .map(s -> "avatar/" + s.getId() + ".jpg")
                .map(PluginMain.INSTANCE::getResourceAsStream)
                .filter(Objects::nonNull)
                .map(ImageHelper::uncheckedRead)
                .collect(Collectors.toList());
        BufferedImage bufferedImage = ImageHelper.makeGrid(images, 4, 90);
        Image image = event.getSubject().uploadImage(ResourceHelper.loadBufferedImage(bufferedImage));
        builder.append(image);
        event.getSubject().sendMessage(builder.build());
    }
}
