package ru.mail.im.motli.palette.sketch

import org.junit.Assert.*
import org.junit.Test
import ru.mail.im.motli.palette.sketch.parser.PaletteColor
import ru.mail.im.motli.palette.sketch.parser.Rgb
import ru.mail.im.motli.palette.sketch.parser.ThemePalette

class PaletteValidatorTest {

    @Test
    fun validateEmpty() {
        assertTrue(PaletteValidator().validate(emptyList()))
    }

    @Test
    fun validateValid() {
        val list = listOf(
                ThemePalette("theme1",
                        listOf(
                                PaletteColor("color1", Rgb(255, 255, 0, 0)),
                                PaletteColor("color2", Rgb(255, 0, 255, 0)))
                ),
                ThemePalette("theme2",
                        listOf(
                                PaletteColor("color1", Rgb(255, 0, 0, 0)),
                                PaletteColor("color2", Rgb(255, 0, 0, 255))
                        ))
        )
        assertTrue(PaletteValidator().validate(list))
    }

    @Test
    fun validateInvalidDifferentColorCount() {
        val list = listOf(
                ThemePalette("theme1",
                        listOf(
                                PaletteColor("color1", Rgb(255, 255, 0, 0)),
                                PaletteColor("color2", Rgb(255, 0, 255, 0)))
                ),
                ThemePalette("theme2",
                        listOf(
                                PaletteColor("color1", Rgb(255, 0, 0, 0)),
                                PaletteColor("color2", Rgb(255, 0, 0, 255)),
                                PaletteColor("color2", Rgb(255, 255, 255, 255))
                        ))
        )
        assertFalse(PaletteValidator().validate(list))
    }

    @Test
    fun validateInvalidDifferentName() {
        val list = listOf(
                ThemePalette("theme1",
                        listOf(
                                PaletteColor("color1", Rgb(255, 255, 0, 0)),
                                PaletteColor("color2", Rgb(255, 0, 255, 0)))
                ),
                ThemePalette("theme2",
                        listOf(
                                PaletteColor("color1", Rgb(255, 0, 0, 0)),
                                PaletteColor("color22", Rgb(255, 0, 0, 255))
                        ))
        )
        assertFalse(PaletteValidator().validate(list))
    }
}