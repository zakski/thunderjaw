package it.unibo.tuprolog.dsl.unify

import kotlin.test.Test

class Prova {

    @Test
    fun testProlog() {
        prolog {
            println("f"("X") mguWith "f"(1))
        }
    }
}