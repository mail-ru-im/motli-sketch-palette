package ru.mail.im.motli.palette.sketch.parser

data class Rgb(val alpha: Int, val red: Int, val green: Int, val blue: Int) {
    fun toHexString() = String.format("#%02x%02x%02x%02x", alpha, red, green, blue)

    override fun toString() = toHexString()
}
