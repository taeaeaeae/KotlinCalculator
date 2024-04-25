import java.util.Stack

class Calculator(inputs: String) {
    val change = Changs()
    var num = change.num(inputs)
    var sign = change.sign(inputs)


    fun calc(): Double {
        num.forEach{println(it)}
        sign.forEach{println(it)}
        firstCalc()
        //남은 연산 수행
        val result = lastCalc()

        return result
    }

    private fun firstCalc() {
        val mul = MultiplyOperation()
        val div = DivideOperation()

        //먼저 계산할 곱셈을 먼저 처리하기 위한  연산 시행
        var i = 0
        while (i < (sign.size)) {            //부호의 갯수만큼 연산
            if (sign[i].equals("*")) {        //곱셈일때
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
                i++                    // 곱셈이나 나눗셈이 아닐 때 다음인덱스로 넘어가기 위함
            }
        }
    }

    private fun lastCalc(): Double {
        val add = AddOperation()
        val sub = SubstractOperation()
        //남은 연산
        var result = num[0]

        for (j in sign.indices) {            // 연산 부호의 갯수만큼 차례로 진행
            if (sign[j].equals("+")) {        // +일때 덧셈
                result = add.operate(result, num[j + 1])
                println("${result}++++++++++++++++${j}")
            } else if (sign[j].equals("-")) {    //-일때 뺄셈
                println(result)
                result = sub.operate(result, num[j + 1])
                println("${result}---------------${j}")
            }
        }
        return result
    }
}
