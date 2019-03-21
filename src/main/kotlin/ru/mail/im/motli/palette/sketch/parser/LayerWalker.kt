package ru.mail.im.motli.palette.sketch.parser

import java.util.*
import java.util.function.Predicate

class LayerWalker {
    fun find(predicate: Predicate<Layer>, layers: List<Layer>): List<Layer> {
        val result = ArrayList<Layer>()
        findRecursively(predicate, layers, result)
        return result
    }

    private fun findRecursively(predicate: Predicate<Layer>, layers: List<Layer>, result: MutableList<Layer>) {
        for (layer in layers) {
            if (predicate.test(layer)) {
                result.add(layer)
            }
            if (layer.layers != null) {
                findRecursively(predicate, layer.layers, result)
            }
        }
    }
}