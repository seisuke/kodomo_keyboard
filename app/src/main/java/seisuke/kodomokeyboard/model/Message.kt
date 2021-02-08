package seisuke.kodomokeyboard.model

sealed class Message {
    object Katakana : Message()
    object Hiragana : Message()
    object DakuonOn : Message()
    object DakuonOff : Message()
}
