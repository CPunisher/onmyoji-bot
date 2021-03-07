package com.cpunisher.onmyojibot.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.awt.image.BufferedImage;

public class ImageHelper {

    public static BufferedImage makeGrid(List<Image> images, int col, int size) {
        int row = (images.size() - 1) / col + 1;

        BufferedImage bufferedImage = new BufferedImage(col * size, row * size, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setBackground(Color.WHITE);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                int index = i * col + j;
                if (index >= images.size()) break;
                graphics2D.drawImage(images.get(index), j * size, i * size, 90, 90, null);
            }
        }

        return bufferedImage;
    }

    public static BufferedImage uncheckedRead(InputStream inputStream) {
        try {
            return ImageIO.read(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new BufferedImage(0, 0, BufferedImage.TYPE_3BYTE_BGR);
    }
}
