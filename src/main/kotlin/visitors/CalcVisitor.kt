package visitors

import tokens.*

class CalcVisitorException(msg: String) : RuntimeException(msg)

class CalcVisitor : TokenVisitor {
    private val stack = ArrayDeque<Double>()

    override fun visit(token: NumberToken) {
        stack.add(token.number.toDouble())
    }

    override fun visit(token: Brace) {
        throw CalcVisitorException("Expected number or operation, found brace")
    }

    override fun visit(token: Operation) {
        if (stack.size < 2) {
            throw CalcVisitorException("Expected 2 arguments in the stack, found ${stack.size}")
        }

        val right = stack.removeLast()
        val left = stack.removeLast()

        val operation: (Double, Double) -> Double = when (token) {
            Plus -> Double::plus
            Minus -> Double::minus
            Mul -> Double::times
            Div -> Double::div
        }
        stack.add(operation(left, right))
    }

    fun getResult(): Double {
        return stack.removeFirstOrNull() ?: throw CalcVisitorException("Expected result, found empty stack")
    }
}