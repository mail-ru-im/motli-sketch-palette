package ru.mail.im.motli.palette.sketch.writer

import com.google.gson.Gson
import ru.mail.im.motli.palette.sketch.parser.ThemePalette
import java.io.File
import java.io.FileWriter

abstract class AbstractWriter {
    abstract fun write(paletteList: List<ThemePalette>, output: File)

    protected fun writeJson(json: Any, output: File) {
        FileWriter(output).use {
            Gson().toJson(json, it)
        }
    }
}