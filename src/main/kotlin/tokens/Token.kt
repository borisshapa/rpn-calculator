package tokens

import visitors.TokenVisitor

interface Token {
    fun accept(visitor: TokenVisitor)
}