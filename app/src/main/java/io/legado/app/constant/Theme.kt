package io.legado.app.constant

import io.legado.app.help.config.AppConfig
import io.legado.app.utils.ColorUtils

enum class Theme {
    Dark, Light, Auto, Transparent, EInk;

    companion object {
        fun getTheme() = when {
            AppConfig.isEInkMode -> EInk
            AppConfig.isNightTheme -> Dark
            else -> Light
        }

        @Suppress("unused")
        fun fromBackground(backgroundColor: Int) =
            if (ColorUtils.isColorLight(backgroundColor)) Light
            else Dark

    }
}