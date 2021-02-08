package seisuke.kodomokeyboard.ui.model

typealias KeyList = List<List<Key>>

sealed class Key {
    data class Letter(
        val hiragana: String,
        val katakana: String? = null,
        val hiraganaDakuon: String? = null,
        val katakanaDakuon: String? = null
    ) : Key()
    object Space : Key()
}
