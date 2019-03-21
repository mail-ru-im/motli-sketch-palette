package ru.mail.im.motli.palette.sketch.writer

import ru.mail.im.motli.palette.sketch.parser.ThemePalette
import java.io.File

class WriterV1 : AbstractWriter() {

    override fun write(paletteList: List<ThemePalette>, output: File) {
        val themes = mutableMapOf<String, Map<String, String>>()
        paletteList.forEach {
            themes[getName(it)] = it.colors.associate { it.name to it.value.toHexString() }
        }
        writeJson(PaletteDto(1, themes), output)
    }

    private fun getName(palette: ThemePalette) = palette.name.replace("_theme", "")

    internal data class PaletteDto(val version: Int,
                                   val themes: Map<String, Map<String, String>>)
}