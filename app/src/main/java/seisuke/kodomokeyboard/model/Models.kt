package seisuke.kodomokeyboard.model

import seisuke.kodomokeyboard.elm.Update

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

sealed class Message {
    object Katakana : Message()
    object Hiragana : Message()
    object DakuonOn : Message()
    object DakuonOff : Message()
}

class KodomoKeyboardUpdate : Update<KeyboardState, Message> {
    override fun update(msg: Message, model: KeyboardState): KeyboardState =
        when (msg) {
            Message.Katakana -> model.copy(katakana = true)
            Message.Hiragana -> model.copy(katakana = false)
            Message.DakuonOn -> model.copy(dakuon = true)
            Message.DakuonOff -> model.copy(dakuon = false)
        }
}
