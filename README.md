# KotlinCalculator
- kotlin을 사용하여 만든 계산기이다. 문장으로 수식을 입력하면 그에 따른 결과를 출력해주며 공백을 허용한다.

  ## 입력 처리
  - readln()으로 String타입의 데이터 입력
  ```kotlin
  var inputs: String = readln()
  ```
  - 받은 데이터에서 숫자와 부호 분리 후 리스트로 저장 (정규식 사용)
    - 숫자는 \D를 사용하여 숫자가 아닌것들로 잘라서 추출 후 리스트에 저장
    - 부호는 데이터에서 사칙연산부호만 찾아서 리스트에 저장
    ```kotlin
    class Changs {
    
        fun num(inputs: String): MutableList<Double> {
            val num = inputs.split("\\D".toRegex()).map { it.toDoubleOrNull() }.filterNotNull().toMutableList()
            return num
        }
    
        fun sign(inputs: String): MutableList<String> {
            val sign = ("\\+|\\-|\\/|\\*".toRegex()).findAll(inputs).map { it.value }.toMutableList()
            return sign
        }
    }
    ```
  - 입력받을때 처리할수 없는 형식이면 다시 입력하라고 권유
    - 숫자와 숫자가 양 끝에 있으며 숫자사이에 사칙연산 부호가 있고 공백은 있어도되고 없어도되는 정규식 작성으로 판별
    ```kotlin
    inline fun validateInput(inputs: String) : Boolean {
        val rex= "\\d+(\\s*(\\+|\\-|\\/|\\*)\\s*\\d+)+".toRegex()
     
        if (!rex.matches(inputs)) { return false}
        return true
    }
    ```
    - 위 함수의 리턴값이 거짓이면 다시 입력
    ```kotlin
    println("수식을 입력해주세요 ex)1+2-3*4/5")
    var inputs: String = readln()
  
    while (!validateInput(inputs)){
        println("수식을 다시 입력해주세요")
        inputs = readln()
    }
    ```


  ## 연산처리
  - 곱셈 나눗셈 먼저 처리
  - 리스트를 순회하며 곱셈 나눗셈을 먼저 찾으면 찾았을때의 위치에서 바로 다음 숫자와 연산 수행 후 현재위치에 덮어 씌움, 그 후 연산에 쓴 뒷숫자 제거
  - Calculator.kt 안에서만 사용하기 때문에 private으로 선언
  - 곱하기나 나누기가 아닐시 다음으로 넘어감
  - 최종적으로 식을 덧셈과 뺄셈만 남도록 정리해줌 ex) 12+23*34/17-56/2 일때 12+46-28 만 남도록 함
  ```kotlin
    private fun firstCalc() {
         val mul = MultiplyOperation()
         val div = DivideOperation()
  
         var i = 0
         while (i < (sign.size)) {
             if (sign[i].equals("*")) {
                 num[i] = mul.operate(num[i], num[i + 1])
                 num.removeAt(i + 1)
                 sign.removeAt(i)
  
                 num.forEach { println(it) }
  
             } else if (sign[i].equals("/")) {
                 num[i] = div.operate(num[i], num[i + 1])
                 num.removeAt(i + 1)
                 sign.removeAt(i)
             } else {
                 i++
             }
         }
     }
  ```
  - 남은 연산 처리
  - 덧셈뺄셈만 남은 식을 엪에서부터 처리하여 return해줌
  ```kotlin
     private fun lastCalc(): Double {
       val add = AddOperation()
       val sub = SubstractOperation()

       var result = num[0]

       for (j in sign.indices) {
           if (sign[j].equals("+")) {
               result = add.operate(result, num[j + 1])
           } else if (sign[j].equals("-")) {
               result = sub.operate(result, num[j + 1])
           }
       }
       return result
  }
  ```
  - 모든 연산 처리하여 결과를 return
  ``` kotlin
  fun calc(): Double {

       firstCalc()
       val result = lastCalc()

       return result
    }
  ```

  ## Main.kt
  - readln으로 입력
  - validateInput(inputs) 함수를 생성해 형식이 맞는지 판별
  ```kotlin
  inline fun validateInput(inputs: String) : Boolean {
      val rex= "\\d+(\\s*(\\+|\\-|\\/|\\*)\\s*\\d+)+".toRegex()
  
      if (!rex.matches(inputs)) { return false}
      return true
  }
  ```
  - Calculator 클래스를 선언하고 calc함수를 호출
  - 입력값과 결과 출력
  ```kotlin
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
  ```

  ## 사칙연산
  <details><summary>추상화 전</summary>

  ```kotlin
  class AddOperation {
      fun operate(num1: Double, num2: Double): Double {
          return num1+num2
      }
  }
  ```
  ```kotlin
  class SubstructorOperation {
      fun operate(num1: Double, num2: Double): Double {
          return num1-num2
      }
  }
  ```
  ```kotlin
  class MultiplyOperation {
      fun operate(num1: Double, num2: Double): Double {
          return num1*num2
      }
  }
  ```
  ```kotlin
  class DivideOperation {
      fun operate(num1: Double, num2: Double): Double {
          return num1/num2
      }
  }
  ```
</details> 

  ## 실행결과
  <img width="339" alt="image" src="https://github.com/taeaeaeae/KotlinCalculator/assets/46617216/0c668e89-f158-4ce1-8ddd-f68c88e9758f">

