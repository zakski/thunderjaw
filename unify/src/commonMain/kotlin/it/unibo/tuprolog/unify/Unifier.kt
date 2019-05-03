package it.unibo.tuprolog.unify

import it.unibo.tuprolog.core.Substitution
import it.unibo.tuprolog.core.Term
import it.unibo.tuprolog.core.Var
import toEquations

interface Unifier {

    val context: Substitution

    fun mgu(term1: Term, term2: Term): Substitution

    fun unify(term1: Term, term2: Term): Boolean {
        return try {
            mgu(term1, term2)
            true
        } catch (e: NoUnifyException) {
            false
        }
    }

    companion object {

        val default by lazy { naive() }

        infix fun Term.unifiesWith(other: Term): Substitution {
            return default.mgu(this, other)
        }

        fun naive(context: Substitution = emptyMap()) : Unifier {
            return object : AbstractUnifier(context.toEquations()) {
                override fun Var.isEqualTo(other: Var): Boolean {
                    return name == other.name
                }
            }
        }
    }
}