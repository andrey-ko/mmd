package org.andreyko.mmd.infra

import io.vertx.core.AbstractVerticle
import io.vertx.core.buffer.Buffer
import org.andreyko.mmd.core.Producer
import org.andreyko.mmd.core.mapper
import org.slf4j.LoggerFactory


class ProducerVerticle(val address: String, val producer: Producer) : AbstractVerticle() {
    private val log = LoggerFactory.getLogger(javaClass)
    var timer: Long = INVALID_TIMER
    val pollInterval = 1000L // 1 sec., in milliseconds

    override fun start() {
        timer = vertx.setPeriodic(pollInterval) {
            doPoll()
        }
    }

    override fun stop() {
        val timer = this.timer
        this.timer = INVALID_TIMER
        if (timer != INVALID_TIMER) {
            vertx.cancelTimer(timer)
        }
    }

    private fun doPoll() {
        val json = producer.poll() ?: return
        val buffer = Buffer.buffer()
        buffer.appendBytes(
                mapper.writeValueAsBytes(json)
        )
        vertx.eventBus().publish(address, buffer)
    }

    companion object {
        const val INVALID_TIMER = -1L
    }
}