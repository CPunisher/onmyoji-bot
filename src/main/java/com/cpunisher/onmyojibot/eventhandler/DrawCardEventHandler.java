package com.cpunisher.onmyojibot.eventhandler;

import com.cpunisher.onmyojibot.OnmyojiBotConfig;
import com.cpunisher.onmyojibot.PluginMain;
import com.cpunisher.onmyojibot.ShikigamiData;
import com.cpunisher.onmyojibot.database.Database;
import com.cpunisher.onmyojibot.database.model.DrawResult;
import com.cpunisher.onmyojibot.database.model.Player;
import com.cpunisher.onmyojibot.util.DrawHelper;
import com.cpunisher.onmyojibot.util.ImageHelper;
import com.cpunisher.onmyojibot.util.ResourceHelper;
import com.j256.ormlite.dao.Dao;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.*;

import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DrawCardEventHandler extends AbstractMessageEventHandler {

    private final int count;

    public DrawCardEventHandler(int count, String... triggerStrings) {
        super(triggerStrings);
        this.count = count;
    }

    @Override
    public void trigger(GroupMessageEvent event) {
        int maxCount = OnmyojiBotConfig.INSTANCE.getMaxDrawCount();
        int newCount = checkCount(event.getSender().getId());
        if (newCount > -1) {
            List<ShikigamiData.Shikigami> result = DrawHelper.draw(this.count);
            appendResult(event.getSender().getId(), result);
            MessageChainBuilder builder = new MessageChainBuilder()
                    .append(new At(event.getSender().getId()))
                    .append(new PlainText(" 抽卡结果"))
                    .append(new PlainText("(" + (maxCount - newCount) + "/" + maxCount + ")\n"));

            List<java.awt.Image> images = result.stream()
                    .map(s -> "avatar/" + s.getId() + ".jpg")
                    .map(PluginMain.INSTANCE::getResourceAsStream)
                    .filter(Objects::nonNull)
                    .map(ImageHelper::uncheckedRead)
                    .collect(Collectors.toList());
            BufferedImage bufferedImage = ImageHelper.makeGrid(images, Math.min(4, this.count), 90);
            Image image = event.getSubject().uploadImage(ResourceHelper.loadBufferedImage(bufferedImage));
            builder.append(image);
            event.getSubject().sendMessage(builder.build());
        } else {
            MessageChainBuilder builder = new MessageChainBuilder()
                    .append(new At(event.getSender().getId()))
                    .append(new PlainText(" 票不够啦"));
            event.getSubject().sendMessage(builder.build());
        }
    }

    private void appendResult(long senderId, List<ShikigamiData.Shikigami> result) {
        Dao<DrawResult, Integer> resultDao = Database.INSTANCE.getResultDao();
        Dao<Player, Long> playerDao = Database.INSTANCE.getPlayerDao();

        int[] rateCount = new int[4];

        try {
            for (ShikigamiData.Shikigami s : result) {
                rateCount[s.getRate()]++;
                if (s.getRate() == 2 || s.getRate() == 3) {
                    resultDao.create(new DrawResult(senderId, s.getId()));
                }
            }

            Player player = playerDao.queryForId(senderId);
            player.setrCount(player.getrCount() + rateCount[0]);
            player.setSrCount(player.getSrCount() + rateCount[1]);
            player.setSsrCount(player.getSsrCount() + rateCount[2]);
            player.setSpCount(player.getSpCount() + rateCount[3]);
            playerDao.update(player);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private int checkCount(long senderId) {
        Dao<Player, Long> playerDao = Database.INSTANCE.getPlayerDao();
        int maxDrawCount = OnmyojiBotConfig.INSTANCE.getMaxDrawCount();

        try {
            Player player = playerDao.queryForId(senderId);
            if (player == null) {
                int newCount = maxDrawCount - this.count;
                playerDao.create(new Player(senderId, newCount));
                return newCount;
            }

            if (player.getDrawCount() >= this.count) {
                player.setDrawCount(player.getDrawCount() - this.count);
                playerDao.update(player);
                return player.getDrawCount();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }
}
