@file: JvmName("DataProcessorApp")
package org.andreyko.mmd.apps

import org.andreyko.mmd.core.*
import org.andreyko.mmd.infra.deployProcessor


fun main(vararg args: String) {

    val processors = HashMap<String, Processor>()

    processors["duration"] = RuleProcessor(
            Rule(
                    extractKey = PathKeySelector("/name"),
                    extractData = ExprDataSelector("/interviews/*/duration"),
                    aggregate = SumAggregator()
            )
    )

    processors["count"] = RuleProcessor(
            Rule(
                    extractKey = PathKeySelector("/name"),
                    extractData = ExprDataSelector("/interviews/*"),
                    aggregate = CounterAggregator()
            )
    )

    processors["interviewers"] = RuleProcessor(
            Rule(
                    extractKey = PathKeySelector("/name"),
                    extractData = ExprDataSelector("/interviews/*/interviewer"),
                    aggregate = ArrayAggregator()
            )
    )


    deployProcessor("data", "results", CompositeProcessor(processors))
}

