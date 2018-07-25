package org.andreyko.mmd.infra

import org.anreyko.mmd.core.Consumer
import org.anreyko.mmd.core.Processor
import org.anreyko.mmd.core.Producer

fun deployProducer(address: String, producer: Producer) {
    sync {
        val vertx = createVertx()
        vertx.deployVerticle(
                ProducerVerticle(address, producer)
        )
    }
}

fun deployConsumer(address: String, producer: Consumer) {
    sync {
        val vertx = createVertx()
        vertx.deployVerticle(
                ConsumerVerticle(address, producer)
        )
    }
}

fun deployProcessor(consumerAddress: String, producerAddress: String, processor: Processor) {
    sync {
        val vertx = createVertx()
        vertx.deployVerticle(
                ProcessorVerticle(consumerAddress, producerAddress, processor)
        )
    }
}