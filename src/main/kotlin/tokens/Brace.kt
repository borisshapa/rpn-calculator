package tokens

import visitors.TokenVisitor

sealed class Brace : Token {
    override fun accept(visitor: TokenVisitor) {
        return visitor.visit(this)
    }
}

object LeftBrace : Brace() {
    override fun toString(): String {
        return "LEFT"
    }
}

object RightBrace : Brace() {
    override fun toString(): String {
        return "RIGHT"
    }
}