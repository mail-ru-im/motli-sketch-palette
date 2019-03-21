package ru.mail.im.motli.palette.sketch

import ru.mail.im.motli.palette.sketch.parser.PaletteParser
import ru.mail.im.motli.palette.sketch.writer.PaletteWriterFactory
import java.io.File

private const val ERROR_NO_ENOUGH_PARAMS = 1
private const val ERROR_FILE_NOT_EXISTS = 2
private const val ERROR_INVALID_PALETTE = 3

fun main(args: Array<String>) {
    if (args.size != 2) {
        help()
        System.exit(ERROR_NO_ENOUGH_PARAMS)
    }

    val sketchFile = ensureExists(File(args[0]).absoluteFile)
    println("Parsing palette from ${sketchFile.absolutePath}")
    val paletteList = PaletteParser(sketchFile).parse()

    if (!PaletteValidator().validate(paletteList)) {
        System.err.println("Palette is invalid")
        System.exit(ERROR_INVALID_PALETTE)
    }

    val outputFile = File(args[1]).absoluteFile
    println("Writing palette to ${outputFile.absolutePath}")
    PaletteWriterFactory.createWriter(1).write(paletteList, outputFile)

    System.out.println("Done")
}

private fun ensureExists(file: File) : File {
    if (!file.exists()) {
        System.err.println("File ${file.absolutePath} not found")
        System.exit(ERROR_FILE_NOT_EXISTS)
    }
    return file
}

private fun help() {
    println("Usage:")
    println("java -jar motli-sketch-palette.jar SKETCH_FILE OUTPUT_FILE")
    println("SKETCH_FILE - path to the .sketch file with palette")
    println("OUTPUT_FILE - path to the output file where palette in JSON format will be put")
}
