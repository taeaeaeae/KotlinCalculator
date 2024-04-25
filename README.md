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
    fun num(inputs: String) : MutableList<Double> {
        val num = inputs.split("\\D".toRegex()).map { it.toDoubleOrNull() }.filterNotNull().toMutableList()
        return num
    }

    fun sign(inputs: String) : MutableList<String> {
        val sign =("\\+|\\-|\\/|\\*".toRegex()).findAll(inputs).map { it.value }.toMutableList()
        return sign
    }
    ```

  - ㅁ
