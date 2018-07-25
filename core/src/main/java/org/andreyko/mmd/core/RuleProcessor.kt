package org.andreyko.mmd.core

import com.fasterxml.jackson.databind.JsonNode

class RuleProcessor(val rule: Rule) : Processor {
    var changeSet = HashMap<String, JsonNode>()

    override fun poll(): JsonNode? {
        if (changeSet.isEmpty()) return null
        val objectNode = jsonObject()
        for ((key, acc) in changeSet) {
            objectNode.replace(key, acc)
        }
        changeSet.clear()
        return objectNode
    }

    override fun processData(msg: JsonNode) {
        val key = rule.extractKey(msg) ?: return
        var acc = changeSet[key]
        for (element in rule.extractData(msg)) {
            acc = rule.aggregate(acc, element)
        }
        if (acc != null) {
            changeSet[key] = acc
        }
    }
}