package org.anreyko.mmd.core

import com.fasterxml.jackson.databind.JsonNode

interface Producer{
    fun poll(): JsonNode?
}