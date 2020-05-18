package it.unibo.tuprolog.theory.impl

import it.unibo.tuprolog.collections.ClauseQueue
import it.unibo.tuprolog.collections.RetrieveResult
import it.unibo.tuprolog.collections.impl.ReteClauseQueue
import it.unibo.tuprolog.collections.rete.custom.ReteTree
import it.unibo.tuprolog.core.*
import it.unibo.tuprolog.theory.AbstractClauseDatabase
import it.unibo.tuprolog.theory.ClauseDatabase
import it.unibo.tuprolog.theory.RetractResult
import it.unibo.tuprolog.theory.Theory.checkClauseCorrect
import it.unibo.tuprolog.theory.Theory.checkClausesCorrect

internal class IndexedClauseDatabase private constructor(private val queue:ClauseQueue) : AbstractClauseDatabase() {

    /** Construct a Clause database from given clauses */
    constructor(clauses: Iterable<Clause>) : this(ClauseQueue.Companion.of(clauses)) {
        checkClausesCorrect(clauses)
    }

    override val clauses: Iterable<Clause> by lazy { queue.toList() }

    override fun plus(clauseDatabase: ClauseDatabase): ClauseDatabase =
        IndexedClauseDatabase(
            clauses + checkClausesCorrect(
                clauseDatabase.clauses
            )
        )

    override fun get(clause: Clause): Sequence<Clause> = queue[clause]

    override fun assertA(clause: Clause): ClauseDatabase =
        IndexedClauseDatabase(
            ClauseQueue.Companion.of(clauses).apply { assertA(clause) }
        )

    override fun assertZ(clause: Clause): ClauseDatabase =
        IndexedClauseDatabase(
            ClauseQueue.Companion.of(clauses).apply { assertZ(clause) }
        )

    override fun retract(clause: Clause): RetractResult {
        val newTheory = ClauseQueue.Companion.of(clauses)
        val retracted = newTheory.retrieveFirst(clause)

        return when (retracted) {
            is RetrieveResult.Failure -> RetractResult.Failure(this)
            else -> RetractResult.Success(
                IndexedClauseDatabase(
                    newTheory
                ), retracted.collection
            )
        }
    }

    override fun retractAll(clause: Clause): RetractResult {
        val newTheory = ClauseQueue.Companion.of(clauses)
        val retracted = newTheory.retrieveAll(clause)

        return when (retracted) {
            is RetrieveResult.Failure -> RetractResult.Failure(this)
            else -> RetractResult.Success(
                IndexedClauseDatabase(
                    newTheory
                ), retracted.collection
            )
        }
    }
}