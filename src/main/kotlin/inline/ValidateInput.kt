package inline

inline fun validateInput(inputs: String) : Boolean {
    val rex= "\\d+(\\s*(\\+|\\-|\\/|\\*)\\s*\\d+)+".toRegex()

    if (!rex.matches(inputs)) { return false}
    return true
}