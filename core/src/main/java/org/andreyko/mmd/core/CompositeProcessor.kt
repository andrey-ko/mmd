package org.andreyko.mmd.core

import com.fasterxml.jackson.databind.JsonNode

class CompositeProcessor(val processors: Map<String, Processor>) : Processor {

    override fun poll(): JsonNode? {
        val objectNode = jsonObject()
        for ((key, proc) in processors) {
            val changeSet = proc.poll() ?: continue
            objectNode.replace(key, changeSet)
        }
        if (objectNode.size() <= 0) return null
        return objectNode
    }

    override fun processData(msg: JsonNode) {
        for (proc in processors.values) {
            proc.processData(msg)
        }

    }
}