# KotlinCalculator
- kotlin을 사용하여 만든 계산기이다. 문장으로 수식을 입력하면 그에 따른 결과를 출력해주며 공백을 허용한다.

  ## 입력 처리
  - readln()으로 String타입의 데이터 입력
  ```kotlin
  var inputs: String = readln()
  ```
  - 받은 데이터에서 숫자와 부호 분리 후 스택으로 변환 (정규식 사용)
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
