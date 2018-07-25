package org.anreyko.mmd.core

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.IntNode

class ConstKeySelector(val value: String) : (JsonNode) -> String? {
    override fun invoke(msg: JsonNode): String {
        return value
    }
}

class PathKeySelector(val path: String) : (JsonNode) -> String? {
    override fun invoke(msg: JsonNode): String {
        return msg.at(path).textValue()
    }
}
