package com.cpunisher.onmyojibot.eventhandler;

import com.cpunisher.onmyojibot.OnmyojiBotConfig;
import com.cpunisher.onmyojibot.PluginMain;
import com.cpunisher.onmyojibot.database.Database;
import com.cpunisher.onmyojibot.database.model.DrawResult;
import com.cpunisher.onmyojibot.database.model.Player;
import com.cpunisher.onmyojibot.util.ImageHelper;
import com.cpunisher.onmyojibot.util.ResourceHelper;
import com.j256.ormlite.dao.Dao;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.PlainText;

import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ShowDrawResultEventHandler extends AbstractMessageEventHandler {

    public ShowDrawResultEventHandler() {
        super("抽卡结果");
    }

    @Override
    public void trigger(GroupMessageEvent event) {
        Dao<DrawResult, Integer> resultDao = Database.INSTANCE.getResultDao();
        Dao<Player, Long> playerDao = Database.INSTANCE.getPlayerDao();
        int maxCount = OnmyojiBotConfig.INSTANCE.getMaxDrawCount();

        List<DrawResult> resultList = new LinkedList<>();
        Player player = new Player();

        try {
            resultList = resultDao.queryBuilder()
                    .where()
                    .eq(DrawResult.PLAYER_ID_FIELD, event.getSender().getId())
                    .query();
            player = playerDao.queryForId(event.getSender().getId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        MessageChainBuilder builder = new MessageChainBuilder()
                .append(new At(event.getSender().getId()))
                .append(new PlainText("(" + (maxCount - player.getDrawCount()) + "/" + maxCount + ")\n"))
                .append(new PlainText("数量R/SR/SSR/SP: " +
                        player.getrCount() + "/" +
                        player.getSrCount() + "/" +
                        player.getSsrCount() + "/" +
                        player.getSpCount() + "\n"));
        if (resultList.size() > 0) {
            List<java.awt.Image> images = resultList.stream()
                    .map(dr -> "avatar/" + dr.getShikigamiId() + ".jpg")
                    .map(PluginMain.INSTANCE::getResourceAsStream)
                    .filter(Objects::nonNull)
                    .map(ImageHelper::uncheckedRead)
                    .collect(Collectors.toList());
            BufferedImage bufferedImage = ImageHelper.makeGrid(images, Math.min(4, resultList.size()), 90);
            Image image = event.getSubject().uploadImage(ResourceHelper.loadBufferedImage(bufferedImage));
            builder.append(image);
        }
        event.getSubject().sendMessage(builder.build());
    }
}
