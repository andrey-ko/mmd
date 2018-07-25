package org.andreyko.mmd.core

import com.fasterxml.jackson.databind.JsonNode

interface Consumer{
    fun processData(msg: JsonNode)
}