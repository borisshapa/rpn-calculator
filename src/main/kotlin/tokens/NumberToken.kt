package tokens

import visitors.TokenVisitor

class NumberToken(val number: Long): Token {
    override fun accept(visitor: TokenVisitor) {
        visitor.visit(this)
    }

    override fun toString(): String {
        return "NUMBER(${number})"
    }
}
