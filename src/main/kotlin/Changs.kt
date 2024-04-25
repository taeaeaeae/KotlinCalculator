import java.util.Stack

class Changs {

    fun num(inputs: String) : Stack<Double> {
        val num = inputs.split("\\D".toRegex()).map { it.toDoubleOrNull() }.filterNotNull().toList()
        val numbers = Stack<Double>()
        numbers.addAll(num)
        return numbers
    }

    fun sign(inputs: String) : Stack<String> {
        val sign =("\\+|\\-|\\/|\\*".toRegex()).findAll(inputs).map { it.value }.toList()
        val signs = Stack<String>()
        signs.addAll(sign)
        return signs
    }
}