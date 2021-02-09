package seisuke.kodomokeyboard.ui.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.GlobalScope
import seisuke.kodomokeyboard.elm.SandBox
import seisuke.kodomokeyboard.model.KeyboardState
import seisuke.kodomokeyboard.model.KodomoKeyboardUpdate
import seisuke.kodomokeyboard.ui.KeyboardView
import seisuke.kodomokeyboard.ui.OptionButtons
import seisuke.kodomokeyboard.ui.viewdata.Key

@FlowPreview
@ExperimentalCoroutinesApi
@Composable
@Preview
private fun PreviewKeyboardView() {
    KeyboardView(
        Modifier,
        listOf(
            listOf(
                Key.Letter("あ"),
                Key.Space,
                Key.Letter("い"),
            ),
            listOf(
                Key.Letter("か"),
                Key.Letter("き"),
                Key.Letter("く"),
            )
        ),
        emptySandBox()
    )
}

@ExperimentalCoroutinesApi
@FlowPreview
@Composable
@Preview
private fun PreviewOptionButton() {
    OptionButtons(
        Modifier,
        emptySandBox()
    )
}

@FlowPreview
private fun emptySandBox() = SandBox.create(
    KeyboardState(
        katakana = false,
        dakuon = false
    ),
    KodomoKeyboardUpdate(
        {},
        {}
    ),
    GlobalScope
)
