package seisuke.kodomokeyboard.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import seisuke.kodomokeyboard.model.KeyboardState
import seisuke.kodomokeyboard.ui.model.Key
import seisuke.kodomokeyboard.ui.model.KeyList

@ExperimentalCoroutinesApi
@Composable
fun KeyboardView(
    modifier: Modifier,
    keyList: KeyList,
    stateFlow: StateFlow<KeyboardState>,
    onClickAction: (String) -> Unit
) {
    val collectAsState = stateFlow.collectAsState() // ?
    val state = remember { collectAsState }.value

    LazyRow(
        modifier = modifier,
        reverseLayout = true
    ) {
        items(items = keyList) { letterList ->
            LineOfA(letterList, state, onClickAction)
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}

@Composable
fun LineOfA(
    letterList: List<Key>,
    state: KeyboardState,
    onClickAction: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.wrapContentWidth()
    ) {
        items(letterList) { key ->
            KeyButton(key, state, onClickAction)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun KeyButton(
    key: Key,
    state: KeyboardState,
    onClickAction: (String) -> Unit
) {
    when (key) {
        is Key.Letter -> {
            val text = state.getTextFrom(key)
            Button(
                enabled = text != null,
                modifier = Modifier
                    .wrapContentWidth()
                    .width(36.dp)
                    .height(36.dp),
                contentPadding = PaddingValues(8.dp),
                onClick = {
                    if (text != null) {
                        onClickAction(text)
                    }
                },
                shape = RoundedCornerShape(10.dp),
            ) {
                Text(
                    text = text ?: key.hiragana
                )
            }
        }
        is Key.Space -> {
            Spacer(
                Modifier
                    .wrapContentWidth()
                    .height(36.dp)
            )
        }
    }
}

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
        MutableStateFlow(KeyboardState.create())
    ) {}
}

private fun KeyboardState.getTextFrom(letter: Key.Letter): String? =
    when {
        katakana && !dakuon -> letter.katakana
        katakana && dakuon -> letter.katakanaDakuon
        !katakana && dakuon -> letter.hiraganaDakuon
        else -> letter.hiragana
    }
