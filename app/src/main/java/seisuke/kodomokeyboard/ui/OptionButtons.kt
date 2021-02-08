package seisuke.kodomokeyboard.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.loadVectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.GlobalScope
import seisuke.kodomokeyboard.R
import seisuke.kodomokeyboard.elm.SandBox
import seisuke.kodomokeyboard.model.KeyboardState
import seisuke.kodomokeyboard.model.KodomoKeyboardUpdate
import seisuke.kodomokeyboard.model.Message

@ExperimentalCoroutinesApi
@Composable
fun OptionButtons(
    modifier: Modifier,
    sandBox: SandBox<KeyboardState, Message>,
    deleteAction: () -> Unit
) {
    val collectAsState = sandBox.stateFlow.collectAsState() // ?
    val state = remember { collectAsState }.value

    Column(
        modifier = modifier,
    ) {

        Button(
            onClick = deleteAction
        ) {
            val image = loadVectorResource(id = R.drawable.ic_baseline_backspace_24)
            image.resource.resource?.let {
                Image(imageVector = it, "backspace")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            modifier = Modifier.height(48.dp),
            onClick = {
                val message = if (state.katakana) {
                    Message.Hiragana
                } else {
                    Message.Katakana
                }
                sandBox.accept(message)
            }
        ) {
            val image = if (state.katakana) {
                loadVectorResource(id = R.drawable.ic_katakana)
            } else {
                loadVectorResource(id = R.drawable.ic_hiragana)
            }
            image.resource.resource?.let {
                Image(imageVector = it, "hiragana")
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        val alpha = if (state.dakuon) {
            1.0f
        } else {
            .4f
        }
        val buttonColors = buttonColors(
            backgroundColor = MaterialTheme.colors.primary.copy(alpha = alpha),
        )

        Button(
            modifier = Modifier.height(48.dp),
            colors = buttonColors,
            onClick = {
                val message = if (state.dakuon) {
                    Message.DakuonOff
                } else {
                    Message.DakuonOn
                }
                sandBox.accept(message)
            }
        ) {
            Text(text = "ばぱ\nゃっ", fontSize = 10.sp)
        }
    }
}

@ExperimentalCoroutinesApi
@FlowPreview
@Composable
@Preview
private fun PreviewOptionButton() {
    OptionButtons(
        Modifier,
        SandBox.create(
            KeyboardState(
                katakana = false,
                dakuon = false
            ),
            KodomoKeyboardUpdate(),
            GlobalScope
        )
    ) {}
}
