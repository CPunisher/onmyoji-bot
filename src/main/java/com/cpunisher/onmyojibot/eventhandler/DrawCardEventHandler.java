package com.cpunisher.onmyojibot.eventhandler;

import com.cpunisher.onmyojibot.OnmyojiBotConfig;
import com.cpunisher.onmyojibot.PluginMain;
import com.cpunisher.onmyojibot.ShikigamiData;
import com.cpunisher.onmyojibot.database.Database;
import com.cpunisher.onmyojibot.database.model.Player;
import com.cpunisher.onmyojibot.util.DrawHelper;
import com.cpunisher.onmyojibot.util.ImageHelper;
import com.cpunisher.onmyojibot.util.ResourceHelper;
import com.j256.ormlite.dao.Dao;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.sql.SQLException;
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
        if (checkCount(event.getSender().getId())) {
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
        } else {
            int maxDrawCount = OnmyojiBotConfig.INSTANCE.getMaxDrawCount();
            MessageChainBuilder builder = new MessageChainBuilder()
                    .append(new At(event.getSender().getId()))
                    .append(new PlainText(" 每天最多只能抽" + maxDrawCount + "次十连噢"));
            event.getSubject().sendMessage(builder.build());
        }
    }

    private boolean checkCount(long senderId) {
        Dao<Player, Long> playerDao = Database.INSTANCE.getPlayerDao();
        int maxDrawCount = OnmyojiBotConfig.INSTANCE.getMaxDrawCount();

        try {
            Player player = playerDao.queryForId(senderId);
            if (player == null) {
                playerDao.create(new Player(senderId, maxDrawCount - 1));
                return true;
            }

            if (player.getDrawCount() > 0) {
                player.setDrawCount(player.getDrawCount() - 1);
                playerDao.update(player);
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
