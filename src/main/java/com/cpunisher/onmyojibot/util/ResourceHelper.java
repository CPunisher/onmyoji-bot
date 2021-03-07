package com.cpunisher.onmyojibot.util;

import com.cpunisher.onmyojibot.PluginMain;
import net.mamoe.mirai.utils.ExternalResource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ResourceHelper {

    public static ExternalResource loadSingleAvatar(long id) {
        InputStream inputStream = PluginMain.INSTANCE.getResourceAsStream("avatar/" + id + ".jpg");

        // TODO Exception handle
        if (inputStream == null) {
            System.out.println("Can't find avatar of id " + id);
        }

        ExternalResource externalResource = null;
        try {
            externalResource = ExternalResource.create(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return externalResource;
    }

    public static ExternalResource loadBufferedImage(BufferedImage bufferedImage) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ExternalResource externalResource = null;

        try {
            ImageIO.write(bufferedImage, "jpg", byteArrayOutputStream);
            externalResource = ExternalResource.create(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return externalResource;
    }

}
