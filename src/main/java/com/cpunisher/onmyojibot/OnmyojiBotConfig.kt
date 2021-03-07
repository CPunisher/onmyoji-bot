package com.cpunisher.onmyojibot

import kotlinx.serialization.Serializable
import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.value

object OnmyojiBotConfig : AutoSavePluginConfig("setting") {

    @Serializable
    data class DrawProbability(
        val r: Int = 7875,
        val sr: Int = 2000,
        val ssr: Int = 100,
        val sp: Int = 25
    )

    @ValueDescription("设定抽卡时各稀有度的概率.")
    val probability: DrawProbability by value(DrawProbability())
}