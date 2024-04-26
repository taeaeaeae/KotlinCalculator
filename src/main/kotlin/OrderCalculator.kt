import operator.AddOperation
import operator.DivideOperation
import operator.MultiplyOperation
import operator.SubstractOperation

class OrderCalculator() {

    val calc = Calculator()

    fun order(num: MutableList<Double>, sign: MutableList<String>): Double {
        val number = firstCalc(num, sign)
        sign.removeAll(arrayOf("*", "/"))
        val result = lastCalc(number, sign)
        return result
    }

    private fun firstCalc(num: MutableList<Double>, sign: MutableList<String>): MutableList<Double> {
        var i = 0
        var j = 0
        while (i < (num.size-1)) {
            if (sign[j].equals("*")) {
                num[i] = calc.operate(MultiplyOperation(), num[i], num[i + 1])
                num.removeAt(i + 1)
                j++
            } else if (sign[j].equals("/")) {
                println("${num.forEach { print(it) }}")
                num[i] = calc.operate(DivideOperation(), num[i], num[i + 1])
                num.removeAt(i + 1)
                j++
            } else {
                i++
                j++
            }
        }

        return num
    }

    private fun lastCalc(num: MutableList<Double>, sign: MutableList<String>): Double {

        var result = num[0]

        for (j in sign.indices) {
            if (sign[j].equals("+")) {
                result = calc.operate(AddOperation(), result, num[j + 1])
            } else if (sign[j].equals("-")) {
                result = calc.operate(SubstractOperation(), result, num[j + 1])
            }
        }
        return result
    }

    fun removeSign(sign: MutableList<String>) {
        sign.removeAll(arrayOf("*", "/"))
    }

}