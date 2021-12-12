import tokens.*
import java.lang.Character.isDigit

class TokenizerException(msg: String): RuntimeException(msg)

class Tokenizer {
    companion object {
        fun tokenize(input: String): List<Token> {
            val context = TokenizerContext()
            for (ch in "$input$") {
                context.state.parse(ch)
            }

            if (context.state !is EndState) {
                throw RuntimeException()
            }
            return context.tokens
        }
    }

    abstract class State(val context: TokenizerContext) {
        abstract fun parse(char: Char)
    }

    class StartState(context: TokenizerContext) : State(context) {
        override fun parse(char: Char) {
            when (char) {
                '(' -> context.addToken(LeftBrace)
                ')' -> context.addToken(RightBrace)
                '+' -> context.addToken(Plus)
                '-' -> context.addToken(Minus)
                '*' -> context.addToken(Mul)
                '/' -> context.addToken(Div)
                in '0'..'9' -> {
                    context.state = NumberState(context)
                    context.state.parse(char)
                }
                in " \t\n\r" -> {}
                '$' -> context.state = EndState(context)
                else -> context.state =
                    ErrorState(context, "Expected [(, ), +, -, *, /, 0..9, ' ', '\\t', '\\n', '\\r'], found $char")
            }
        }
    }

    class NumberState(context: TokenizerContext) : State(context) {
        private val stringBuilder = StringBuilder()

        override fun parse(char: Char) {
            if (isDigit(char)) {
                stringBuilder.append(char)
            } else {
                context.addToken(NumberToken(stringBuilder.toString().toLong()))
                context.state = StartState(context)
                context.state.parse(char)
            }
        }

    }

    class EndState(context: TokenizerContext) : State(context) {
        override fun parse(char: Char) {}
    }

    class ErrorState(context: TokenizerContext, val message: String) : State(context) {
        override fun parse(char: Char) {
            throw TokenizerException(message)
        }
    }

    class TokenizerContext {
        val tokens = mutableListOf<Token>()
        var state: State = StartState(this)

        fun addToken(token: Token) {
            tokens.add(token)
        }
    }
}