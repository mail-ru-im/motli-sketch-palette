package ru.mail.im.motli.palette.sketch.writer

object PaletteWriterFactory {
    fun createWriter(version: Int) = when (version) {
        1 -> WriterV1()
        else -> throw IllegalArgumentException("Unsupported version $version")
    }
}