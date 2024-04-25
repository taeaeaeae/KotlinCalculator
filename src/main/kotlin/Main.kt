import inline.validateInput


fun main() {

    println("수식을 입력해주세요 ex)1+2-3*4/5")
    var inputs: String = readln()

    while (!validateInput(inputs)){
        println("수식을 다시 입력해주세요")
        inputs = readln()
    }

    val calc = Calculator(inputs)

    println("${inputs}의 결과는 ${calc.calc()}입니다")
}



