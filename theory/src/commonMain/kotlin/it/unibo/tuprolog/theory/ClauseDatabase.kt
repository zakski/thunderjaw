package it.unibo.tuprolog.theory

import it.unibo.tuprolog.core.*
import kotlin.js.JsName
import kotlin.jvm.JvmStatic

interface ClauseDatabase : Iterable<Clause> {

    /** All [Clause]s in this database */
    @JsName("clauses")
    val clauses: Iterable<Clause>

    /** Only [clauses] that are [Rule]s */
    @JsName("rules")
    val rules: Iterable<Rule>
        get() = clauses.filterIsInstance<Rule>()

    /** Only [clauses] that are [Directive]s */
    @JsName("directives")
    val directives: Iterable<Directive>
        get() = clauses.filterIsInstance<Directive>()

    /** The amount of clauses in this [ClauseDatabase] */
    @JsName("size")
    val size: Long

    /** Adds given ClauseDatabase to this */
    @JsName("plusClauseDatabase")
    operator fun plus(clauseDatabase: ClauseDatabase): ClauseDatabase

    /** Adds given Clause to this ClauseDatabase */
    @JsName("plus")
    operator fun plus(clause: Clause): ClauseDatabase = assertZ(clause)

    /** Checks if given clause is contained in this database */
    @JsName("contains")
    operator fun contains(clause: Clause): Boolean

    /** Checks if given clause is present in this database */
    @JsName("containsHead")
    operator fun contains(head: Struct): Boolean

    /** Checks if clauses exists in this database having the specified [Indicator] as head; this should be [well-formed][Indicator.isWellFormed] */
    @JsName("containsIndicator")
    operator fun contains(indicator: Indicator): Boolean

    /** Retrieves matching clauses from this database */
    @JsName("get")
    operator fun get(clause: Clause): Sequence<Clause>

    /** Retrieves matching rules from this database */
    @JsName("getByHead")
    operator fun get(head: Struct): Sequence<Rule>

    /** Retrieves all rules in this database having the specified [Indicator] as head; this should be [well-formed][Indicator.isWellFormed] */
    @JsName("getByIndicator")
    operator fun get(indicator: Indicator): Sequence<Rule>

    /** Adds given clause before all other clauses in this database */
    @JsName("assertA")
    fun assertA(clause: Clause): ClauseDatabase

    /** Adds given clause before all other clauses in this database */
    @JsName("assertAFact")
    fun assertA(struct: Struct): ClauseDatabase = assertA(Fact.of(struct))

    /** Adds given clause after all other clauses in this database */
    @JsName("assertZ")
    fun assertZ(clause: Clause): ClauseDatabase

    /** Adds given clause after all other clauses in this database */
    @JsName("assertZFact")
    fun assertZ(struct: Struct): ClauseDatabase = assertZ(Fact.of(struct))

    /** Tries to delete a matching clause from this database */
    @JsName("retract")
    fun retract(clause: Clause): RetractResult

    /** Tries to delete a matching clause from this database */
    @JsName("retractByHead")
    fun retract(head: Struct): RetractResult = retract(Rule.of(head, Var.anonymous()))

    /** Tries to delete all matching clauses from this database */
    @JsName("retractAll")
    fun retractAll(clause: Clause): RetractResult

    /** Tries to delete all matching clauses from this database */
    @JsName("retractAllByHead")
    fun retractAll(head: Struct): RetractResult = retractAll(Rule.of(head, Var.anonymous()))

    /** An enhanced toString that prints the database in a Prolog program format, if [asPrologText] is `true` */
    @JsName("toStringAsProlog")
    fun toString(asPrologText: Boolean): String

    companion object {

        /** Creates an empty ClauseDatabase */
        @JvmStatic
        @JsName("empty")
        fun empty(): ClauseDatabase = of(emptySequence())

        /** Creates a ClauseDatabase with given clauses */
        @JvmStatic
        @JsName("of")
        fun of(vararg clause: Clause): ClauseDatabase = of(clause.asIterable())

        /** Let developers easily create a ClauseDatabase programmatically while avoiding variables names clashing */
        @JvmStatic
        @JsName("ofScopes")
        fun of(vararg clause: Scope.() -> Clause): ClauseDatabase = of(clause.map { Scope.empty(it) })

        /** Creates a ClauseDatabase with given clauses */
        @JvmStatic
        @JsName("ofSequence")
        fun of(clauses: Sequence<Clause>): ClauseDatabase = of(clauses.asIterable())

        /** Creates a ClauseDatabase with given clauses */
        @JvmStatic
        @JsName("ofIterable")
        fun of(clauses: Iterable<Clause>): ClauseDatabase = ClauseDatabaseReteImpl(clauses)


        // TODO trovare dei nomi migliori per i factory sottostanti

        /** Creates an empty ClauseDatabase */
        @JvmStatic
        @JsName("emptyListed")
        fun emptyListed(): ClauseDatabase = ofListed(emptySequence())

        /** Creates a ClauseDatabase with given clauses */
        @JvmStatic
        @JsName("ofListed")
        fun ofListed(vararg clause: Clause): ClauseDatabase = ofListed(clause.asIterable())

        /** Let developers easily create a ClauseDatabase programmatically while avoiding variables names clashing */
        @JvmStatic
        @JsName("ofListedScopes")
        fun ofListed(vararg clause: Scope.() -> Clause): ClauseDatabase = ofListed(clause.map { Scope.empty(it) })

        /** Creates a ClauseDatabase with given clauses */
        @JvmStatic
        @JsName("ofListedSequence")
        fun ofListed(clauses: Sequence<Clause>): ClauseDatabase = ofListed(clauses.asIterable())

        /** Creates a ClauseDatabase with given clauses */
        @JvmStatic
        @JsName("ofListedIterable")
        fun ofListed(clauses: Iterable<Clause>): ClauseDatabase = ClauseDatabaseListImpl(clauses)
    }
}
