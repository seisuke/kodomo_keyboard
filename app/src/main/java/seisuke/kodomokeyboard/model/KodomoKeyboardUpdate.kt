package seisuke.kodomokeyboard.model

import seisuke.kodomokeyboard.elm.Update

class KodomoKeyboardUpdate : Update<KeyboardState, Message> {
    override fun update(msg: Message, model: KeyboardState): KeyboardState =
        when (msg) {
            Message.Katakana -> model.copy(katakana = true)
            Message.Hiragana -> model.copy(katakana = false)
            Message.DakuonOn -> model.copy(dakuon = true)
            Message.DakuonOff -> model.copy(dakuon = false)
        }
}
