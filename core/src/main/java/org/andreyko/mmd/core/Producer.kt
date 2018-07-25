package org.andreyko.mmd.core

import com.fasterxml.jackson.databind.JsonNode

interface Producer{
    fun poll(): JsonNode?
}