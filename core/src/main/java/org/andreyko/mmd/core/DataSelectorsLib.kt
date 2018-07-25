package org.andreyko.mmd.core

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ObjectNode
import java.util.StringTokenizer
import kotlin.collections.ArrayList
import kotlin.collections.Iterator
import kotlin.collections.iterator
import kotlin.collections.toTypedArray
import kotlin.coroutines.experimental.buildIterator


class JsonQueryExpr private constructor(val expr: Array<ExprNode>) {
    abstract class ExprNode private constructor() {
        class Key(val value: String) : ExprNode()
        class Star : ExprNode()
    }

    fun execute(data: JsonNode?): Iterator<JsonNode> {
        return execute(0, data)
    }

    private fun execute(pos:Int, ctx: JsonNode?): Iterator<JsonNode> {
        if (ctx == null || ctx.isNull || ctx.isMissingNode) {
            return EmptyIterator()
        }
        if (pos >= expr.size) {
            return arrayOf(ctx).iterator()
        }
        val step = expr[pos]
        if (step is ExprNode.Key) {
            val objectNode = (ctx as ObjectNode)
            return execute(pos+1, objectNode[step.value])
        }
        if (step is ExprNode.Star) {
            return buildIterator {
                for (node in ctx.elements()) {
                    yieldAll(execute(pos+1, node))
                }
            }
        }
        throw Exception("unsupported expression step ${step.javaClass.simpleName}")
    }


    companion object {

        fun parse(expr: String): JsonQueryExpr {
            val itor = StringTokenizer(expr, "/")
            val exprList = ArrayList<ExprNode>()
            while (itor.hasMoreTokens()) {
                val step = itor.nextToken()
                if (step == "*") {
                    exprList.add(ExprNode.Star())
                } else {
                    exprList.add(ExprNode.Key(step))
                }
            }
            return JsonQueryExpr(exprList.toTypedArray())
        }
    }

}


class ExprDataSelector(val expr: JsonQueryExpr) : (JsonNode) -> Iterator<JsonNode> {

    constructor(expr: String) : this(JsonQueryExpr.parse(expr))

    override fun invoke(msg: JsonNode): Iterator<JsonNode> {
        return expr.execute(msg)
    }
}
