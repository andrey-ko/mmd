package org.andreyko.mmd.core

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.IntNode

class SumAggregator : (JsonNode?, JsonNode) -> JsonNode {
    override fun invoke(acc: JsonNode?, el: JsonNode): JsonNode {
        var v = el.asInt()
        if (acc != null) {
            v += acc.asInt()
        }
        return IntNode(v)
    }
}

class ArrayAggregator : (JsonNode?, JsonNode) -> JsonNode {
    override fun invoke(acc: JsonNode?, el: JsonNode): JsonNode {
        if (acc != null) {
            (acc as ArrayNode).add(el)
            return acc
        }
        return jsonArray(el)
    }
}

class CounterAggregator : (JsonNode?, JsonNode) -> JsonNode {
    override fun invoke(acc: JsonNode?, el: JsonNode): JsonNode {
        var v = 1
        if (acc != null) {
            v += acc.asInt()
        }
        return IntNode(v)
    }
}