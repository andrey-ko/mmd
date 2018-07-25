package org.anreyko.mmd.core

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.IntNode
import com.fasterxml.jackson.databind.node.ObjectNode

val mapper = ObjectMapper()

fun jsonArray(vararg values: JsonNode): ArrayNode {
    val res = mapper.nodeFactory.arrayNode()
    for(el in values){
        res.add(el)
    }
    return res
}

fun jsonObject(): ObjectNode {
    return mapper.nodeFactory.objectNode()
}

fun jsonNode(value: Int): IntNode {
    return IntNode(value)
}


