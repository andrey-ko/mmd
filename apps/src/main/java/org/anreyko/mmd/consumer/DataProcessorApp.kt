package org.anreyko.mmd.consumer

import org.andreyko.mmd.infra.deployProcessor
import org.anreyko.mmd.core.*


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

