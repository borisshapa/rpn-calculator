package visitors

import tokens.*

class ParserVisitorException(msg: String): RuntimeException(msg)

class ParserVisitor: TokenVisitor {
    private val stack = ArrayDeque<Token>()
    private val result = mutableListOf<Token>()

    override fun visit(token: NumberToken) {
        result.add(token)
    }

    override fun visit(token: Brace) {
        if (token is LeftBrace) {
            stack.add(token)
        } else {
            while (stack.isNotEmpty()) {
                val lastToken = stack.removeLastOrNull()
                if (lastToken is LeftBrace) {
                    break;
                }
                if (lastToken !is NumberToken && lastToken !is Operation) {
                    throw ParserVisitorException("Expected number or operation, found $lastToken")
                }
                result.add(lastToken)
            }
        }
    }

    override fun visit(token: Operation) {
        while (stack.isNotEmpty()) {
            val operation = stack.last()
            if (operation is Operation && operation.priority <= token.priority) {
                result.add(stack.removeLast())
            } else {
                break
            }
        }
        stack.add(token)
    }
    
    fun getRPN(): List<Token> {
        while (stack.isNotEmpty()) {
            val token = stack.removeLast()
            if (token !is Operation) {
                throw ParserVisitorException("Expected operation, found $token")
            }
            result.add(token)
        }
        return result
    }
}