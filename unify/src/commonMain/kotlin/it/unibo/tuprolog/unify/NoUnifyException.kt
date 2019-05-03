package it.unibo.tuprolog.unify

import it.unibo.tuprolog.core.Term
import it.unibo.tuprolog.core.TuprologException

open class NoUnifyException(val term1: Term, val term2: Term, other: Throwable?) : TuprologException(other) {

    constructor(term1: Term, term2: Term) : this(term1, term2, null)

    override val message: String?
        get() = "Cannot mgu term `${term1}` with term `${term2}`"
}
