package org.andreyko.mmd.infra

import com.fasterxml.jackson.databind.ObjectMapper
import io.netty.buffer.ByteBufInputStream
import io.vertx.core.AbstractVerticle
import io.vertx.core.Handler
import io.vertx.core.buffer.Buffer
import io.vertx.core.eventbus.Message
import io.vertx.core.eventbus.MessageConsumer
import org.anreyko.mmd.core.Consumer
import org.anreyko.mmd.core.mapper
import org.slf4j.LoggerFactory


class ConsumerVerticle(val address: String, val consumer: Consumer) : AbstractVerticle(), Handler<Message<Buffer>> {
    private val log = LoggerFactory.getLogger(javaClass)
    var vertxConsumer: MessageConsumer<Buffer>? = null

    override fun start() {
        vertxConsumer = vertx.eventBus().consumer(address, this)
    }

    override fun stop() {
        val vertxConsumer = this.vertxConsumer
        this.vertxConsumer = null

        if (vertxConsumer != null) {
            try {
                vertxConsumer.unregister()
            } catch (ex: Throwable) {
                // swallow exception
                log.error("failed to unregister message consumer", ex)
            }
        }
    }

    override fun handle(event: Message<Buffer>) {
        val json = mapper.readTree(ByteBufInputStream(event.body().byteBuf))
        consumer.processData(json)
    }
}