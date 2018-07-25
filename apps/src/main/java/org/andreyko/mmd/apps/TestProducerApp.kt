@file: JvmName("TestProducerApp")
package org.andreyko.mmd.apps

import com.fasterxml.jackson.databind.JsonNode
import org.andreyko.mmd.infra.deployProducer
import org.andreyko.mmd.core.Producer
import org.andreyko.mmd.core.mapper


class TestProducer : Producer {
    val data = mapper.readTree("""{
      "name": "Andrey",
      "interviews": {
        "2018-07-24 10:00:00": {
          "type": "Coding",
          "interviewer": "Raj",
          "duration": 2,
          "duration_unit": "hours"
        },
        "2018-07-24 12:00:00": {
          "type": "Coding",
          "interviewer": "Mark",
          "duration": 1,
          "duration_unit": "hours"
        }
      }
    }""")

    override fun poll(): JsonNode? {
        return data
    }
}

fun main(vararg args: String) {
    deployProducer("data", TestProducer())
}

