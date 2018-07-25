package org.andreyko.mmd.infra

import com.hazelcast.config.Config
import io.vertx.core.*
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager
import kotlin.coroutines.experimental.suspendCoroutine

suspend inline fun <reified T> vertxAwait(crossinline block: (Handler<AsyncResult<T>>) -> Unit): T {
    return suspendCoroutine { cont ->
        block(Handler { ar ->
            if (!ar.succeeded()) {
                cont.resumeWithException(ar.cause())
            } else {
                cont.resume(ar.result())
            }
        })
    }
}

suspend fun createVertx(): Vertx{
    val hzCfg = Config()

    val clusterManager = HazelcastClusterManager(hzCfg)
    val vertxOptions = VertxOptions().apply {
        this.clusterManager = clusterManager
        this.isClustered = true
    }

    val vertx = vertxAwait<Vertx> {
        Vertx.clusteredVertx(vertxOptions, it)
    }
    return vertx
}

