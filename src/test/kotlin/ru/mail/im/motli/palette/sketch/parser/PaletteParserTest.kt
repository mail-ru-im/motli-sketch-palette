package ru.mail.im.motli.palette.sketch.parser

import org.junit.Test

import org.junit.Assert.*
import java.io.File

class PaletteParserTest {

    @Test
    fun parse() {
        val palette = File(javaClass.classLoader.getResource("test-palette.sketch").toURI())
        val themes = PaletteParser(palette).parse()
        assertEquals(2, themes.size)

        var theme = themes[0]
        assertEquals("left_theme", theme.name)
        assertEquals(3, theme.colors.size)
        assertEquals("ghost_inverse", theme.colors[0].name)
        assertEquals("#ffa164f7", theme.colors[0].value.toHexString())
        assertEquals("primary", theme.colors[1].name)
        assertEquals("#ff23c7de", theme.colors[1].value.toHexString())
        assertEquals("secondary_base_pastel", theme.colors[2].name)
        assertEquals("#ffff4797", theme.colors[2].value.toHexString())

        theme = themes[1]
        assertEquals("right_theme", theme.name)
        assertEquals(3, theme.colors.size)
        assertEquals("ghost_inverse", theme.colors[0].name)
        assertEquals("#ff40d295", theme.colors[0].value.toHexString())
        assertEquals("primary", theme.colors[1].name)
        assertEquals("#fffe9a3f", theme.colors[1].value.toHexString())
        assertEquals("secondary_base_pastel", theme.colors[2].name)
        assertEquals("#ff2e91ff", theme.colors[2].value.toHexString())
    }
}