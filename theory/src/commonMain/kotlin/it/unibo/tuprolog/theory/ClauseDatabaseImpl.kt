package it.unibo.tuprolog.theory

import it.unibo.tuprolog.core.*
import kotlin.collections.List as KtList

internal class ClauseDatabaseImpl private constructor(private val reteTree: ReteTree<*>) : ClauseDatabase {

    /** Construct a Clause database from given clauses */
    constructor(clauses: Iterable<Clause>) : this(ReteTree.of(clauses))


    override val clauses: KtList<Clause> by lazy { reteTree.clauses.toList() }

    override val rules: KtList<Rule> by lazy { super.rules.toList() }
    override val directives: KtList<Directive> by lazy { super.directives.toList() }


    override fun plus(clauseDatabase: ClauseDatabase): ClauseDatabase =
            ClauseDatabaseImpl(clauses + clauseDatabase.clauses)

    override fun contains(clause: Clause): Boolean = reteTree.get(clause).any()
    override fun contains(head: Struct): Boolean = contains(Rule.of(head, Var.anonymous()))

    override fun get(clause: Clause): Sequence<Clause> = reteTree.get(clause)
    override fun get(head: Struct): Sequence<Clause> = get(Rule.of(head, Var.anonymous()))

    override fun assertA(clause: Clause): ClauseDatabase =
            ClauseDatabaseImpl(reteTree.deepCopy().apply { put(clause, before = true) })

    override fun assertZ(clause: Clause): ClauseDatabase =
            ClauseDatabaseImpl(reteTree.deepCopy().apply { put(clause, before = false) })

    override fun retract(clause: Clause): RetractResult {
        val newTheory = reteTree.deepCopy()
        val retracted = newTheory.remove(clause)

        return if (retracted.none()) {
            RetractResult.Failure(this)
        } else {
            RetractResult.Success(ClauseDatabaseImpl(newTheory), retracted.asIterable())
        }
    }

    override fun retractAll(clause: Clause): RetractResult {
        val newTheory = reteTree.deepCopy()
        val retracted = newTheory.removeAll(clause)

        return if (retracted.none()) {
            RetractResult.Failure(this)
        } else {
            RetractResult.Success(ClauseDatabaseImpl(newTheory), retracted.asIterable())
        }
    }

    override fun iterator(): Iterator<Clause> = clauses.iterator()

    override fun toString(): String = clauses.joinToString(".\n", "", ".\n")

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as ClauseDatabaseImpl

        if (clauses != other.clauses) return false

        return true
    }

    override fun hashCode(): Int = clauses.hashCode()
}