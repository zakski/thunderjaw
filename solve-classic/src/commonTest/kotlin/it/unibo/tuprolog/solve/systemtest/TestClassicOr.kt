package it.unibo.tuprolog.solve.systemtest

import it.unibo.tuprolog.solve.*
import it.unibo.tuprolog.solve.channel.InputChannel
import it.unibo.tuprolog.solve.channel.OutputChannel
import it.unibo.tuprolog.solve.exception.PrologWarning
import it.unibo.tuprolog.solve.library.AliasedLibrary
import it.unibo.tuprolog.solve.library.Libraries
import it.unibo.tuprolog.solve.stdlib.DefaultBuiltins
import it.unibo.tuprolog.theory.Theory
import kotlin.test.Test

class TestClassicOr : TestOr, SolverFactory by ClassicSolverFactory {

    private val prototype = TestOr.prototype(this)

    @Test
    override fun testTrueOrFalse() {
        prototype.testTrueOrFalse()
    }

    @Test
    override fun testCutFalseOrTrue() {
        prototype.testCutFalseOrTrue()
    }

    @Test
    override fun testCutCall() {
        prototype.testCutCall()
    }

    @Test
    override fun testCutAssignedValue() {
        prototype.testCutAssignedValue()
    }

    @Test
    override fun testOrDoubleAssignment() {
        prototype.testOrDoubleAssignment()
    }


}