package it.unibo.tuprolog.collections.impl

import it.unibo.tuprolog.collections.AbstractMutableReteClauseCollection
import it.unibo.tuprolog.collections.MutableClauseMultiSet
import it.unibo.tuprolog.collections.rete.custom.ReteTree
import it.unibo.tuprolog.core.Clause
import it.unibo.tuprolog.theory.TheoryUtils

internal class MutableReteClauseMultiSet private constructor(
    private val rete: ReteTree
) : MutableClauseMultiSet, AbstractMutableReteClauseCollection<MutableReteClauseMultiSet>(rete) {

    /** Construct a [MutableReteClauseMultiSet] from given clauses */
    constructor(clauses: Iterable<Clause>) : this(ReteTree.unordered(clauses)) {
        TheoryUtils.checkClausesCorrect(clauses)
    }

    override fun count(clause: Clause): Long =
        rete.get(clause).count().toLong()


    override fun get(clause: Clause): Sequence<Clause> =
        rete.get(clause)

}