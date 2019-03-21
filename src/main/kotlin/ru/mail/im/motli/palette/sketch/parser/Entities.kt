package ru.mail.im.motli.palette.sketch.parser

import com.google.gson.annotations.SerializedName

data class Color(val alpha: Float, val red: Float, val green: Float, val blue: Float)

data class Fill(val color: Color,
                @SerializedName("_class") val className : String)

data class Frame(val x: Float, val y: Float, val width: Float, val height: Float)

data class Layer(val frame: Frame, val name: String, val style: Style, val layers: List<Layer>?,
                 @SerializedName("_class") val className : String)

data class Page(val layers: List<Layer>)

data class Style(val fills: List<Fill>)