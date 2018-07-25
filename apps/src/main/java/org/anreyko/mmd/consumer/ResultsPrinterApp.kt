package org.anreyko.mmd.consumer

import com.fasterxml.jackson.databind.JsonNode
import org.andreyko.mmd.infra.deployConsumer
import org.anreyko.mmd.core.Consumer
import org.anreyko.mmd.core.mapper

class PrintConsumer : Consumer {
    override fun processData(msg: JsonNode) {
        println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(msg))
    }
}

fun main(vararg args: String) {
    deployConsumer("results", PrintConsumer())
}

