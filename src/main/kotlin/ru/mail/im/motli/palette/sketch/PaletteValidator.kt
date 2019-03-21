package ru.mail.im.motli.palette.sketch

import ru.mail.im.motli.palette.sketch.parser.ThemePalette

class PaletteValidator {
    fun validate(paletteList: List<ThemePalette>): Boolean {
        val uniqueColorCount = paletteList.flatMap { it.colors.map { it.name } }.toSet().size
        return paletteList.all { it.colors.size == uniqueColorCount }
    }
}