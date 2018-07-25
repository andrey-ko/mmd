package org.andreyko.mmd.core

import com.fasterxml.jackson.databind.JsonNode

class Rule(
    val extractKey: (msg: JsonNode)->String?,
    val extractData: (msg: JsonNode) -> Iterator<JsonNode>,
    val aggregate: (acc:JsonNode?, el:JsonNode)->JsonNode
)