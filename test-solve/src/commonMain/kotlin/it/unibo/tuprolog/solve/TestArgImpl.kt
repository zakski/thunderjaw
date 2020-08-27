package it.unibo.tuprolog.solve

import it.unibo.tuprolog.dsl.theory.prolog
import it.unibo.tuprolog.solve.exception.error.DomainError
import it.unibo.tuprolog.solve.exception.error.InstantiationError
import it.unibo.tuprolog.solve.exception.error.TypeError
import kotlin.collections.listOf as ktListOf

internal class TestArgImpl(private val solverFactory: SolverFactory) : TestArg {

    override fun testArgFromFoo() {
        prolog {
            val solver = solverFactory.solverWithDefaultBuiltins()

            val query = arg(1, "foo"("a", "b"), "a")
            val solutions = solver.solve(query, mediumDuration).toList()

            assertSolutionEquals(
                ktListOf(query.yes()),
                solutions
            )
        }
    }

    override fun testArgFromFooX() {
        prolog {
            val solver = solverFactory.solverWithDefaultBuiltins()

            val query = arg(1, "foo"("a", "b"), "X")
            val solutions = solver.solve(query, mediumDuration).toList()

            assertSolutionEquals(
                ktListOf(query.yes("X" to "a")),
                solutions
            )
        }
    }

    override fun testArgFromFoo2() {
        prolog {
            val solver = solverFactory.solverWithDefaultBuiltins()

            val query = arg(1, "foo"("X", "b"), "a")
            val solutions = solver.solve(query, mediumDuration).toList()

            assertSolutionEquals(
                ktListOf(query.yes("X" to "a")),
                solutions
            )
        }
    }

    override fun testArgFromFooInF() {
        prolog {
            val solver = solverFactory.solverWithDefaultBuiltins()

            val query = arg(2, "foo"("a", "f"("X", "b"), "c"), "f"("a", "Y"))
            val solutions = solver.solve(query, mediumDuration).toList()

            assertSolutionEquals(
                with(query) {
                    ktListOf(
                        yes("X" to "a"),
                        yes("Y" to "b")
                    )
                },
                solutions
            )
        }
    }

    override fun testArgFromFooY() {
        prolog {
            val solver = solverFactory.solverWithDefaultBuiltins()

            val query = arg(1, "foo"("X", "b"), "Y")
            val solutions = solver.solve(query, mediumDuration).toList()

            assertSolutionEquals(
                ktListOf(query.yes("Y" to "X")),
                solutions
            )
        }
    }

    override fun testArgFromFooInSecondTerm() {
        prolog {
            val solver = solverFactory.solverWithDefaultBuiltins()

            val query = arg(1, "foo"("a", "b"), "b")
            val solutions = solver.solve(query, mediumDuration).toList()

            assertSolutionEquals(
                ktListOf(query.no()),
                solutions
            )
        }
    }

    override fun testArgFromFooInFoo() {
        prolog {
            val solver = solverFactory.solverWithDefaultBuiltins()

            val query = arg(1, "foo"("a", "b"), "foo")
            val solutions = solver.solve(query, mediumDuration).toList()

            assertSolutionEquals(
                ktListOf(query.no()),
                solutions
            )
        }
    }

    override fun testArgNumberFromFoo() {
        prolog {
            val solver = solverFactory.solverWithDefaultBuiltins()

            val query = arg(3, "foo"(3, 4), "N")
            val solutions = solver.solve(query, mediumDuration).toList()

            assertSolutionEquals(
                ktListOf(query.no()),
                solutions
            )
        }
    }

    override fun testArgXFromFoo() {
        prolog {
            val solver = solverFactory.solverWithDefaultBuiltins()

            val query = arg("X", "foo"("a", "b"), "a")
            val solutions = solver.solve(query, mediumDuration).toList()

            assertSolutionEquals(
                ktListOf(
                    query.halt(
                        InstantiationError.forGoal(
                            DummyInstances.executionContext,
                            Signature("arg", 3),
                            varOf("X")
                        )
                    )
                ),
                solutions
            )
        }
    }

    override fun testArgNumberFromX() {
        prolog {
            val solver = solverFactory.solverWithDefaultBuiltins()

            val query = arg(1, "X", "a")
            val solutions = solver.solve(query, mediumDuration).toList()

            assertSolutionEquals(
                ktListOf(
                    query.halt(
                        InstantiationError.forGoal(
                            DummyInstances.executionContext,
                            Signature("arg", 3),
                            varOf("X")
                        )
                    )
                ),
                solutions
            )
        }
    }

    override fun testArgFromAtom() {
        prolog {
            val solver = solverFactory.solverWithDefaultBuiltins()

            val query = arg(0, atom("X"), "A")
            val solutions = solver.solve(query, mediumDuration).toList()

            assertSolutionEquals(
                ktListOf(
                    query.halt(
                        TypeError.forGoal(
                            DummyInstances.executionContext,
                            Signature("arg", 3),
                            TypeError.Expected.ATOM,
                            numOf(4)
                        )
                    )
                ),
                solutions
            )
        }
    }

    override fun testArgFromNumber() {
        prolog {
            val solver = solverFactory.solverWithDefaultBuiltins()

            val query = arg(0, 3, "A")
            val solutions = solver.solve(query, mediumDuration).toList()

            assertSolutionEquals(
                ktListOf(
                    query.halt(
                        TypeError.forGoal(
                            DummyInstances.executionContext,
                            Signature("arg", 3),
                            TypeError.Expected.COMPOUND,
                            numOf(3)
                        )
                    )
                ),
                solutions
            )
        }
    }

    override fun testNegativeArgFromFoo() {
        prolog {
            val solver = solverFactory.solverWithDefaultBuiltins()

            val query = arg(-3, "foo"("a", "b"), "A")
            val solutions = solver.solve(query, mediumDuration).toList()

            assertSolutionEquals(
                ktListOf(
                    query.halt(
                        DomainError.forGoal(
                            DummyInstances.executionContext,
                            Signature("arg", 3),
                            DomainError.Expected.NOT_LESS_THAN_ZERO,
                            numOf(-3)
                        )
                    )
                ),
                solutions
            )
        }
    }

    override fun testArgAFromFoo() {
        prolog {
            val solver = solverFactory.solverWithDefaultBuiltins()

            val query = arg("a", "foo"("a", "b"), "X")
            val solutions = solver.solve(query, mediumDuration).toList()

            assertSolutionEquals(
                ktListOf(
                    query.halt(
                        TypeError.forGoal(
                            DummyInstances.executionContext,
                            Signature("arg", 3),
                            TypeError.Expected.INTEGER,
                            atomOf("a")
                        )
                    )
                ),
                solutions
            )
        }
    }
}