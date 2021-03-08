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

    @Serializable
    data class DatabaseConfig(
        val address: String = "localhost",
        val user: String = "root",
        val password: String = "",
    )

    @ValueDescription("设定抽卡时各稀有度的概率.")
    val probability: DrawProbability by value(DrawProbability())

    @ValueDescription("每个用户每日抽卡次数上限.")
    val maxDrawCount: Int by value(10)

    @ValueDescription("数据库设定.")
    val database: DatabaseConfig by value(DatabaseConfig())
}