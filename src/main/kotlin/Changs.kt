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