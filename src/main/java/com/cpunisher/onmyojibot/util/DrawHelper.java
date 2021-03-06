package com.cpunisher.onmyojibot.util;

import com.cpunisher.onmyojibot.OnmyojiBotConfig;
import com.cpunisher.onmyojibot.ShikigamiData;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class DrawHelper {

    public static ShikigamiData.Shikigami drawOne() {
        OnmyojiBotConfig.DrawProbability p = OnmyojiBotConfig.INSTANCE.getProbability();
        int maxR = p.getR();
        int maxSr = maxR + p.getSr();
        int maxSsr = maxSr + p.getSsr();
        int maxSp = maxSsr + p.getSp();
        Random random = new Random();;

        List<ShikigamiData.Shikigami> list = null;
        int type = random.nextInt(maxSp);
        if (type < maxR) list = ShikigamiData.INSTANCE.getR();
        else if (type < maxSr) list = ShikigamiData.INSTANCE.getSR();
        else if (type < maxSsr) list = ShikigamiData.INSTANCE.getSSR();
        else if (type < maxSp) list = ShikigamiData.INSTANCE.getSP();

        // TODO solve Null exception
        return list.get(random.nextInt(list.size()));
    }

    public static List<ShikigamiData.Shikigami> draw(int count) {
        List<ShikigamiData.Shikigami> result = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            result.add(drawOne());
        }
        return result;
    }
}
