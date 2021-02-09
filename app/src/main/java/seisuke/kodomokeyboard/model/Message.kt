package seisuke.kodomokeyboard.model

sealed class Message {
    object Katakana : Message()
    object Hiragana : Message()
    object DakuonOn : Message()
    object DakuonOff : Message()
    object Delete : Message()
    class ClickKey(val text: String) : Message()
}
