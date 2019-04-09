package ru.mail.im.motli.palette.sketch.parser

import org.junit.Test

import org.junit.Assert.*

class RgbTest {

    @Test
    fun toHexString() {
        assertEquals("#ffaabbcc", Rgb(0xFF, 0xAA, 0xBB, 0xCC).toHexString())
    }
}