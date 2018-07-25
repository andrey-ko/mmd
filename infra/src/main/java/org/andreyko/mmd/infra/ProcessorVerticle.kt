package org.andreyko.mmd.infra

import io.netty.buffer.ByteBufInputStream
import io.vertx.core.AbstractVerticle
import io.vertx.core.Handler
import io.vertx.core.buffer.Buffer
import io.vertx.core.eventbus.Message
import io.vertx.core.eventbus.MessageConsumer
import org.andreyko.mmd.core.Processor
import org.andreyko.mmd.core.mapper
import org.slf4j.LoggerFactory


class ProcessorVerticle(val consumerAddress: String, val producerAddress: String, val processor: Processor) : AbstractVerticle(), Handler<Message<Buffer>> {
    private val log = LoggerFactory.getLogger(javaClass)
    var vertxConsumer: MessageConsumer<Buffer>? = null
    var timer: Long = INVALID_TIMER
    val pollInterval = 1000L // 1 sec., in milliseconds

    override fun start() {
        vertxConsumer = vertx.eventBus().consumer(consumerAddress, this)
        timer = vertx.setPeriodic(pollInterval) {
            doPoll()
        }
    }

    override fun stop() {
        val vertxConsumer = this.vertxConsumer
        this.vertxConsumer = null

        if (vertxConsumer != null) {
            vertxConsumer.unregister()
        }

        val timer = this.timer
        this.timer = INVALID_TIMER
        if (timer != INVALID_TIMER) {
            vertx.cancelTimer(timer)
        }
    }

    override fun handle(event: Message<Buffer>) {
        val json = mapper.readTree(ByteBufInputStream(event.body().byteBuf))
        processor.processData(json)
    }

    private fun doPoll() {
        val json = processor.poll() ?: return
        val buffer = Buffer.buffer()
        buffer.appendBytes(
                mapper.writeValueAsBytes(json)
        )
        vertx.eventBus().publish(producerAddress, buffer)
    }

    companion object {
        const val INVALID_TIMER = -1L
    }
}