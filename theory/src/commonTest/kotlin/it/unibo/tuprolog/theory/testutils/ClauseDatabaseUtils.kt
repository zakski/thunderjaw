package it.unibo.tuprolog.theory.testutils

import it.unibo.tuprolog.core.*
import it.unibo.tuprolog.theory.ClauseDatabase

/**
 * Utils singleton for testing [ClauseDatabase]
 *
 * @author Enrico
 */
internal object ClauseDatabaseUtils {

    /** Contains well formed clauses (the head is a [Struct] and the body doesn't contain [Numeric] values) */
    internal val wellFormedClauses by lazy { ReteTreeUtils.mixedClauses }

    /** Contains a pair which has in its first element half clauses from [wellFormedClauses] in the second element the other half */
    internal val wellFormedClausesHelves by lazy {
        Pair(wellFormedClauses.subList(0, wellFormedClauses.count() / 2),
                wellFormedClauses.subList(wellFormedClauses.count() / 2, wellFormedClauses.count()))
    }

    /** Contains well formed clauses queries based on [wellFormedClauses] and expected responses from the ClauseDatabase */
    internal val clausesQueryResultsMap by lazy { ReteTreeUtils.mixedClausesQueryResultsMap }

    /** Contains clauses queries that have Variable as body, to be used when testing the methods accepting only heads */
    internal val rulesQueryWithVarBodyResultsMap by lazy {
        clausesQueryResultsMap
                .filterKeys { it is Rule && it.body.isVariable }
                .mapKeys { it.key as Rule }
    }

    /** Contains not well formed clauses (with [Numeric] values in body) */
    internal val notWellFormedClauses by lazy {
        listOf(
                Clause.of(Struct.of("test", Var.anonymous()), Struct.of("b", Var.anonymous()), Integer.of(1)),
                Directive.of(Atom.of("execute_this"), Real.of(1.5)),
                Rule.of(Struct.of("f2", Atom.of("a")), Atom.of("do_something"), Numeric.of(1.5f))
        )
    }
}