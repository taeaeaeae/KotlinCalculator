import operator.AbstractOperation

class Calculator() {
    fun operate(operator: AbstractOperation, num1: Double, num2: Double): Double = operator.operate(num1,num2)
}
