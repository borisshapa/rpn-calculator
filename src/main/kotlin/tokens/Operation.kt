package tokens

import visitors.TokenVisitor

sealed class Operation(val priority: Int, private val char: Char, private val stringRepresentation: String) : Token {
    override fun accept(visitor: TokenVisitor) {
        visitor.visit(this)
    }

    override fun toString(): String {
        return stringRepresentation
    }
}

object Plus: Operation(2, '+', "PLUS")
object Minus: Operation(2, '-', "MINUS")
object Mul: Operation(1, '*', "MUL")
object Div: Operation(1, '/', "DIV")