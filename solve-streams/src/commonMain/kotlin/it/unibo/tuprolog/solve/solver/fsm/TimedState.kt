package it.unibo.tuprolog.solve.solver.fsm

import it.unibo.tuprolog.solve.TimeInstant

/**
 * Interface representing a state with a notion of "time"
 *
 * @author Enrico
 */
interface TimedState : State {

    /** Returns current time instant in some notion of "time" */
    fun getCurrentTime(): TimeInstant
}
