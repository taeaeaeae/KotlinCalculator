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


<details><summary>내일 정리</summary>
1. 우선 AbstractOperation.kt 를 생성하여 인터페이스를 만들어줬다.

 - 관리의 편의도를 위해 operator라는 패키지를 새로 생성
```kotlin
package operator
interface AbstractOperation {
    fun operate(num1: Double, num2:Double): Double
}
```

2. 상속받아 사칙연산 클래서 오버라이딩
```kotlin
package operator

class AddOperation : AbstractOperation {
    override fun operate(num1: Double, num2: Double): Double {
        return num1+num2
    }
}
```

3. Calculator 클래스에서 구현
```kotlin
class Calculator() {
    fun operate(operator: AbstractOperation, num1: Double, num2: Double): Double = operator.operate(num1,num2)
}
```

4. OrderCalculator.kt 추가

- 기존 Calculator에서 생성한 함수들 실행하여 Calculator는 연산만 시행함

+ firstCalc 수정
```kotlin
// 클래스에 배열을 만들어서 관리하지 않고 받아온 배열을 반환
// 대신 sign은 함수 실행할때 "*"와 "/"제거
private fun firstCalc(num: MutableList<Double>, sign: MutableList<String>): MutableList<Double> {
    var i = 0	//num의 인덱스
    var j = 0	//sign의 인덱스
    while (i < (num.size-1)) {
        if (sign[j].equals("*")) {
            num[i] = calc.operate(MultiplyOperation(), num[i], num[i + 1])
            num.removeAt(i + 1)	// 현재 인덱스 제거
            j++		//현재 인덱스를 제거하여 기존 sign이 사용되므로 sign의 인덱스 증가
        } else if (sign[j].equals("/")) {
            num[i] = calc.operate(DivideOperation(), num[i], num[i + 1])
            num.removeAt(i + 1)
            j++
        } else {
            i++		//곱셈이나 나눗셈이 아닐 때 다음으로 넘어감
            j++
        }
    }
    return num		// 곱셈 나눗셈 처리를 완료한 숫자리스트 반환
}
```



- lastCalculator는 추상화메소드 사용부분만 변경

-  order 함수 생성
```kotlin
fun order(num: MutableList<Double>, sign: MutableList<String>): Double {
    val number = firstCalc(num, sign)		// 곱셈나눗셈 먼저 연산하여 반환된 숫자리스트 저장
    sign.removeAll(arrayOf("*", "/"))		// 연산 완료한 "*","/" 모두 제거
    val result = lastCalc(number, sign)		// 남은 덧셈 뺄셈 진행 후 값 리턴
    return result
}
```

- 전체코드
```kotlin
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
}
```

5. Main.kt
```kotlin
fun main() {

    println("수식을 입력해주세요 ex)1+2-3*4/5")
    var inputs: String = readln()

    while (!validateInput(inputs)){
        println("수식을 다시 입력해주세요")
        inputs = readln()
    }

    val change = Changs()			// 원래 Calculator에서 했던 작업을 메인으로 가져옴
    val num = change.num(inputs)
    val sign = change.sign(inputs)
    val order = OrderCalculator()	

    // order.order(num,sign)		
    // 예전 코드처럼 input을 통째로 가지고다니는게 비효율적이라고 생각해서
    // firstCalc함수를 수정하여 num반환 후 sing 배열의 부호를 따로 삭제


    println("${inputs}의 결과는 ${order.order(num,sign)}입니다")
}
```
</detail>
