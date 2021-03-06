package it.unibo.tuprolog.solve.stdlib.primitive

import it.unibo.tuprolog.dsl.prolog
import it.unibo.tuprolog.dsl.theory.PrologScopeWithTheories
import it.unibo.tuprolog.solve.*
import it.unibo.tuprolog.solve.exception.error.TypeError
import it.unibo.tuprolog.solve.testutils.SolverTestUtils.createSolveRequest
import kotlin.test.Test

/**
 * Test class for [Not]
 *
 * @author Enrico
 */
internal class NotTest {

    @Test
    fun notRevertsProvidedNoResponse() {
        val query = prolog { "\\+"(false) }
        val solutions = Not.wrappedImplementation(
            createSolveRequest(query, primitives = mapOf(Not.descriptionPair, Call.descriptionPair))
        ).map { it.solution }.asIterable()

        assertSolutionEquals(listOf(query.yes()), solutions)
    }

    @Test
    fun notRevertsProvidedYesResponse() {
        val query = prolog { "\\+"(true) }
        val solutions = Not.wrappedImplementation(
            createSolveRequest(query, primitives = mapOf(Not.descriptionPair, Call.descriptionPair))
        ).map { it.solution }.asIterable()

        assertSolutionEquals(listOf(query.no()), solutions)
    }

    @Test
    fun notReturnsOnlyOneFailResponseIfMoreTrueOnes() {
        val query = prolog { "\\+"("a") }
        val solutions = Not.wrappedImplementation(
            createSolveRequest(
                query,
                primitives = mapOf(Not.descriptionPair, Call.descriptionPair),
                database = PrologScopeWithTheories.empty().theory({ "a" }, { "a" })
            )
        ).map { it.solution }.asIterable()

        assertSolutionEquals(listOf(query.no()), solutions)
    }

    @Test
    fun notReturnsAsIsAnHaltSolution() {
        val query = prolog { "\\+"(1) }
        val solutions = Not.wrappedImplementation(
            createSolveRequest(
                query,
                primitives = mapOf(Not.descriptionPair, Call.descriptionPair, Throw.descriptionPair)
            )
        ).map { it.solution }.asIterable()

        assertSolutionEquals(
            listOf(
                query.halt(
                    TypeError.forGoal(
                        DummyInstances.executionContext,
                        Not.signature,
                        TypeError.Expected.CALLABLE,
                        prolog { numOf(1) })
                )
            ), solutions
        )
    }

}
