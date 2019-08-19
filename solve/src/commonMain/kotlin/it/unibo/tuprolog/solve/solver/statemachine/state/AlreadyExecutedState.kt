package it.unibo.tuprolog.solve.solver.statemachine.state

/**
 * A wrapper class representing States that should not be executed again, because already executed their behaviour
 *
 * @author Enrico
 */
internal class AlreadyExecutedState(internal val wrappedState: State) : State by wrappedState {
    override val hasBehaved: Boolean = true
}

/** Extension method to wrap a [State], marking it as already executed */
internal fun State.alreadyExecuted(): State =
        (this as? AlreadyExecutedState)
                ?.let { it }
                ?: AlreadyExecutedState(this)
