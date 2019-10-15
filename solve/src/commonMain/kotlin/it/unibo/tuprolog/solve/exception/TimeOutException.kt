package it.unibo.tuprolog.solve.exception

import it.unibo.tuprolog.solve.ExecutionContext
import it.unibo.tuprolog.solve.TimeDuration

/**
 * Exception thrown if time for execution finished, before completion of solution process
 *
 * @param message the detail message string.
 * @param cause the cause of this exception.
 * @param context The current context at exception creation
 *
 * @author Enrico
 */
class TimeOutException(
        message: String? = null,
        cause: Throwable? = null,
        context: ExecutionContext,
        val deltaTime: TimeDuration // TODO: 14/09/2019 what's the semantic of this field? how should be filled?
        // [GC] deltaTime =def= actualTime - maxTime
        // TODO maybe this is useless... what do you think?
) : TuPrologRuntimeException(message, cause, context) {

    constructor(cause: Throwable?, context: ExecutionContext, deltaTime: TimeDuration)
            : this(cause?.toString(), cause, context, deltaTime)

    override fun updateContext(context: ExecutionContext): TimeOutException {
        return TimeOutException(message, cause, context, deltaTime)
    }
}