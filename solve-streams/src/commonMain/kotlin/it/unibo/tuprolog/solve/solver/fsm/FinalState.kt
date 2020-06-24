package it.unibo.tuprolog.solve.solver.fsm

import it.unibo.tuprolog.solve.Solve

/**
 * Represents a Final [State] of the Prolog solver state-machine
 *
 * @author Enrico
 */
internal interface FinalState : State {

    /** The [Solve.Response] characterizing this Final State */
    override val solve: Solve.Response

}
