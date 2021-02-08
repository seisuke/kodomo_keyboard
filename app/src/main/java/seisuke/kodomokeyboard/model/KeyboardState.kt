package seisuke.kodomokeyboard.model

data class KeyboardState(
    val katakana: Boolean,
    val dakuon: Boolean
) {
    companion object {
        fun create(): KeyboardState = KeyboardState(
            katakana = false,
            dakuon = false
        )
    }
}
