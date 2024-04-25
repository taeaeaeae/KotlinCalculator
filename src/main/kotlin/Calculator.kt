import java.util.Stack

class Calculator(inputs: String) {
    val change = Changs()
    var num = change.num(inputs)
    var sign = change.sign(inputs)


    fun calc(): Double {
        num.forEach{println(it)}
        sign.forEach{println(it)}
        firstCalc()
        val result = lastCalc()

        return result
    }

    private fun firstCalc() {
        val mul = MultiplyOperation()
        val div = DivideOperation()

        var i = 0
        while (i < (sign.size)) {
            if (sign[i].equals("*")) {
                num[i] = mul.operate(num[i], num[i + 1])
                num.removeAt(i + 1)
                sign.removeAt(i)
                println("${num[i]}++++++++++++++++${i}")

            } else if (sign[i].equals("/")) {
                num[i] = div.operate(num[i], num[i + 1])
                num.removeAt(i + 1)
                sign.removeAt(i)
                println("${num[i]}++++++++++++++++${i}")
            } else {
                i++
            }
        }
    }

    private fun lastCalc(): Double {
        val add = AddOperation()
        val sub = SubstractOperation()
        //남은 연산
        var result = num[0]

        for (j in sign.indices) {
            if (sign[j].equals("+")) {
                result = add.operate(result, num[j + 1])
                println("${result}++++++++++++++++${j}")
            } else if (sign[j].equals("-")) {
                println(result)
                result = sub.operate(result, num[j + 1])
                println("${result}---------------${j}")
            }
        }
        return result
    }
}
