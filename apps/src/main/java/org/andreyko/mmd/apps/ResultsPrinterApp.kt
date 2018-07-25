@file: JvmName("ResultsPrinterApp")
package org.andreyko.mmd.apps

import com.fasterxml.jackson.databind.JsonNode
import org.andreyko.mmd.infra.deployConsumer
import org.andreyko.mmd.core.Consumer
import org.andreyko.mmd.core.mapper

class PrintConsumer : Consumer {
    override fun processData(msg: JsonNode) {
        println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(msg))
    }
}

fun main(vararg args: String) {
    deployConsumer("results", PrintConsumer())
}

