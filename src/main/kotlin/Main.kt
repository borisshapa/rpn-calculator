import tokens.Token
import visitors.CalcVisitor
import visitors.ParserVisitor
import visitors.PrintVisitor
import visitors.TokenVisitor

fun applyVisitor(tokens: List<Token>, visitor: TokenVisitor){
    for (token in tokens) {
        token.accept(visitor)
    }
}

fun main() {
    val input = "(30 + 2) /8"

    try {
        var tokens = Tokenizer.tokenize(input)

        val parserVisitor = ParserVisitor()
        applyVisitor(tokens, parserVisitor)
        tokens = parserVisitor.getRPN()

        applyVisitor(tokens, PrintVisitor())
        println()

        val calcVisitor = CalcVisitor()
        applyVisitor(tokens, calcVisitor)
        print(calcVisitor.getResult())
    } catch (e: Exception) {
        print(e.message)
    }
}