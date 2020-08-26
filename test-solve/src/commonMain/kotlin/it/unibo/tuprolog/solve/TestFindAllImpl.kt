package it.unibo.tuprolog.solve

import it.unibo.tuprolog.dsl.theory.prolog
import it.unibo.tuprolog.solve.exception.error.InstantiationError
import it.unibo.tuprolog.solve.exception.error.TypeError
import kotlin.collections.listOf as ktListOf

internal class TestFindAllImpl(private val solverFactory: SolverFactory) : TestFindAll {

    override fun testFindXInDiffValues() {
        prolog {
            val solver = solverFactory.solverWithDefaultBuiltins()

            val query = findall("X", ("X" `=` 1) or ("X" `=` 2), "S")
            val solutions = solver.solve(query, mediumDuration).toList()

            assertSolutionEquals(
                ktListOf(query.yes("S" to listOf(1, 2))),
                solutions
            )
        }
    }

    override fun testFindSumResult() {
        prolog {
            val solver = solverFactory.solverWithDefaultBuiltins()

            val query = findall("+"("X", "Y"), "X" `=` 1, "S")
            val solutions = solver.solve(query, mediumDuration).toList()

            assertSolutionEquals(
                ktListOf(query.yes("S" to listOf(1 + "Y"))),
                solutions
            )
        }
    }

    override fun testFindXinFail() {
        prolog {
            val solver = solverFactory.solverWithDefaultBuiltins()

            val query = findall("X", fail, "L")
            val solutions = solver.solve(query, mediumDuration).toList()

            assertSolutionEquals(
                ktListOf(query.yes("L" to emptyList)),
                solutions
            )
        }
    }

    override fun testFindXinSameXValues() {
        prolog {
            val solver = solverFactory.solverWithDefaultBuiltins()

            val query = findall("X", ("X" `=` 1) or ("X" `=` 1), "S")
            val solutions = solver.solve(query, mediumDuration).toList()

            assertSolutionEquals(
                ktListOf(query.yes("S" to listOf(1, 1))),
                solutions
            )
        }
    }

    override fun testResultListIsCorrect() {
        prolog {
            val solver = solverFactory.solverWithDefaultBuiltins()

            val query = findall("X", ("X" `=` 2) or ("X" `=` 1), listOf(1, 2))
            val solutions = solver.solve(query, mediumDuration).toList()

            assertSolutionEquals(
                ktListOf(query.no()),
                solutions
            )
        }
    }

    override fun testFindXtoDoubleAssigment() {
        prolog {
            val solver = solverFactory.solverWithDefaultBuiltins()

            val query = findall("X", ("X" `=` 1) or ("X" `=` 2), listOf("X", "Y"))
            val solutions = solver.solve(query, mediumDuration).toList()

            assertSolutionEquals(
                    ktListOf(query.yes("X" to 1, "Y" to 2)),
                    solutions
            )
        }
    }

    override fun testFindXinGoal() {
        prolog {
            val solver = solverFactory.solverWithDefaultBuiltins()

            val query = findall("X", "Goal", "S")
            val solutions = solver.solve(query, mediumDuration).toList()

            assertSolutionEquals(
                ktListOf(
                    query.halt(
                        InstantiationError.forGoal(
                            DummyInstances.executionContext,
                            Signature("findall", 3),
                            varOf("X")
                        )
                    )
                ),
                solutions
            )
        }
    }

    override fun testFindXinNumber() {
        prolog {
            val solver = solverFactory.solverWithDefaultBuiltins()

            val query = findall("X", 4, "S")
            val solutions = solver.solve(query, mediumDuration).toList()

            assertSolutionEquals(
                ktListOf(
                    query.halt(
                        TypeError.forGoal(
                            DummyInstances.executionContext,
                            Signature("findall", 3),
                            TypeError.Expected.CALLABLE,
                            numOf(4)
                        )
                    )
                ),
                solutions
            )
        }
    }

    override fun testFindXinCall() {
        prolog {
            val solver = solverFactory.solverWithDefaultBuiltins()

            val query = findall("X", call(1), "S")
            val solutions = solver.solve(query, mediumDuration).toList()

            assertSolutionEquals(
                ktListOf(
                    query.halt(
                        TypeError.forGoal(
                            DummyInstances.executionContext,
                            Signature("findall", 3),
                            TypeError.Expected.CALLABLE,
                            numOf(4)
                        )
                    )
                ),
                solutions
            )
        }
    }
}