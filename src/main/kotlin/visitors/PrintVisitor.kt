package visitors

import tokens.Brace
import tokens.NumberToken
import tokens.Operation
import tokens.Token

class PrintVisitor : TokenVisitor {
    private fun printToken(token: Token) = print("$token ")

    override fun visit(token: NumberToken) {
        printToken(token)
    }

    override fun visit(token: Brace) {
        printToken(token)
    }

    override fun visit(token: Operation) {
        printToken(token)
    }
}