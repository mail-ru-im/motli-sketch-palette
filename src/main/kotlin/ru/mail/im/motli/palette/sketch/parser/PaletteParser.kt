package ru.mail.im.motli.palette.sketch.parser

import com.google.gson.GsonBuilder
import java.io.File
import java.io.InputStream
import java.io.InputStreamReader
import java.util.*
import java.util.function.Predicate
import java.util.zip.ZipFile
import kotlin.Comparator

class PaletteParser(private val file: File) {

    private companion object {
        fun isRectangleOrShapeGroup(layer: Layer) = "shapeGroup" == layer.className || "rectangle" == layer.className
    }

    private val themePredicate = Predicate<Layer> { isRectangleOrShapeGroup(it) && it.name.endsWith("_theme") }

    private val colorPredicate = Predicate<Layer> { isRectangleOrShapeGroup(it) && it.name.startsWith("#") }

    private val gson = GsonBuilder().create()

    private val layerWalker = LayerWalker()

    fun parse(): List<ThemePalette> {
        val result = ArrayList<ThemePalette>()
        ZipFile(file).use {
            val entries = it.entries()
            while (entries.hasMoreElements()) {
                val entry = entries.nextElement()
                if (entry.name.startsWith("pages/")) {
                    result.addAll(parsePage(it.getInputStream(entry)))
                }
            }
        }
        result.sortWith(Comparator { t1, t2 -> t1.name.compareTo(t2.name)})
        return result
    }

    private fun parsePage(inputStream: InputStream): List<ThemePalette> {
        val page = InputStreamReader(inputStream).use { reader -> gson.fromJson(reader, Page::class.java) }
        val themes = layerWalker.find(themePredicate, page.layers)
        val colors = layerWalker.find(colorPredicate, page.layers)
        return themes.map { buildTheme(it, colors) }
    }

    private fun buildTheme(theme: Layer, colorLayers: List<Layer>): ThemePalette {
        val themeRect = Rect(theme.frame)
        val colors = ArrayList<PaletteColor>(colorLayers.size)

        colorLayers.forEach {
            val colorRect = Rect(it.frame)
            if (themeRect.contains(colorRect)) {
                colors.add(PaletteColor(it.name.substring(1), getFillColor(it)))
            }
        }

        colors.sortWith(Comparator { c1, c2 -> c1.name.compareTo(c2.name)})

        return ThemePalette(theme.name, colors)
    }

    private fun getFillColor(colorLayer: Layer): Rgb {
        for ((color, className) in colorLayer.style.fills) {
            if ("fill" == className) {
                val a = Math.round(color.alpha * 255)
                val r = Math.round(color.red * 255)
                val g = Math.round(color.green * 255)
                val b = Math.round(color.blue * 255)
                return Rgb(a, r, g, b)
            }
        }
        throw IllegalStateException("No fill color")
    }

    private class Rect(frame: Frame) {

        val right = frame.x
        val left = frame.x + frame.width
        val top = frame.y
        val bottom = frame.y + frame.height

        internal operator fun contains(rect: Rect): Boolean {
            return inside(rect.left, rect.top)
                    && inside(rect.right, rect.top)
                    && inside(rect.left, rect.bottom)
                    && inside(rect.right, rect.bottom)
        }

        internal fun inside(x: Float, y: Float): Boolean {
            return x in right..left && y in top..bottom
        }
    }
}