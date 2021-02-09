package seisuke.kodomokeyboard.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.ExperimentalCoroutinesApi
import seisuke.kodomokeyboard.elm.SandBox
import seisuke.kodomokeyboard.model.KeyboardState
import seisuke.kodomokeyboard.model.Message
import seisuke.kodomokeyboard.ui.viewdata.Key
import seisuke.kodomokeyboard.ui.viewdata.KeyList

@ExperimentalCoroutinesApi
@Composable
fun RootView(sandBox: SandBox<KeyboardState, Message>) {
    MaterialTheme(
        colors = originalLightColors()
    ) {
        Row {
            KeyboardView(
                modifier = Modifier
                    .weight(0.1f)
                    .padding(top = 8.dp),
                RootViewConst.KEY_LIST,
                sandBox,
            )

            OptionButtons(
                Modifier
                    .wrapContentWidth()
                    .padding(top = 8.dp, start = 8.dp, end = 8.dp),
                sandBox
            )
        }
    }
}

private fun originalLightColors(): Colors = lightColors(
    primary = RootViewConst.Red700,
    primaryVariant = RootViewConst.Red900,
    onPrimary = Color.White,
    secondary = RootViewConst.Red700,
    secondaryVariant = RootViewConst.Red900,
    onSecondary = Color.White,
    error = RootViewConst.Red800
)

class RootViewConst {
    companion object {
        val Red700 = Color(0xffdd0d3c)
        val Red800 = Color(0xffd00036)
        val Red900 = Color(0xffc20029)

        val KEY_LIST: KeyList = listOf(
            listOf(
                Key.Letter("あ", "ア", "ぁ", "ァ"),
                Key.Letter("い", "イ", "ぃ", "ィ"),
                Key.Letter("う", "ウ", "ぅ", "ゥ"),
                Key.Letter("え", "エ", "ぇ", "ェ"),
                Key.Letter("お", "オ", "ぉ", "ォ"),
            ),
            listOf(
                Key.Letter("か", "カ", "が", "ガ"),
                Key.Letter("き", "キ", "ぎ", "ギ"),
                Key.Letter("く", "ク", "ぐ", "グ"),
                Key.Letter("け", "ケ", "げ", "ゲ"),
                Key.Letter("こ", "コ", "ご", "ゴ"),
            ),
            listOf(
                Key.Letter("さ", "サ", "ざ", "ザ"),
                Key.Letter("し", "シ", "じ", "ジ"),
                Key.Letter("す", "ス", "ず", "ズ"),
                Key.Letter("せ", "セ", "ぜ", "ゼ"),
                Key.Letter("そ", "ソ", "ぞ", "ゾ"),
            ),
            listOf(
                Key.Letter("た", "タ", "だ", "ダ"),
                Key.Letter("ち", "チ", "ぢ", "ヂ"),
                Key.Letter("つ", "ツ", "づ", "ヅ"),
                Key.Letter("て", "テ", "で", "デ"),
                Key.Letter("と", "ト", "ど", "ド"),
            ),
            listOf(
                Key.Letter("な", "ナ"),
                Key.Letter("に", "ニ"),
                Key.Letter("ぬ", "ヌ", "っ", "ッ"),
                Key.Letter("ね", "ネ"),
                Key.Letter("の", "ノ"),
            ),
            listOf(
                Key.Letter("は", "ハ", "ば", "バ"),
                Key.Letter("ひ", "ヒ", "び", "ビ"),
                Key.Letter("ふ", "フ", "ぶ", "ブ"),
                Key.Letter("へ", "ヘ", "べ", "ベ"),
                Key.Letter("ほ", "ホ", "ぼ", "ボ"),
            ),
            listOf(
                Key.Letter("ま", "マ", "ぱ", "パ"),
                Key.Letter("み", "ミ", "ぴ", "ピ"),
                Key.Letter("む", "ム", "ぷ", "プ"),
                Key.Letter("め", "メ", "ぺ", "ペ"),
                Key.Letter("も", "モ", "ぽ", "ポ"),
            ),
            listOf(
                Key.Letter("や", "ヤ", "ゃ", "ャ"),
                Key.Space,
                Key.Letter("ゆ", "ユ", "ゅ", "ュ"),
                Key.Space,
                Key.Letter("よ", "ヨ", "ょ", "ョ"),
            ),
            listOf(
                Key.Letter("ら", "ラ"),
                Key.Letter("り", "リ"),
                Key.Letter("る", "ル"),
                Key.Letter("れ", "レ"),
                Key.Letter("ろ", "ロ"),
            ),
            listOf(
                Key.Letter("わ", "ワ"),
                Key.Letter("を", "ヲ"),
                Key.Letter("ん", "ン"),
                Key.Letter("ー", "ー"),
                Key.Space,
            ),
            listOf(
                Key.Letter("1"),
                Key.Letter("2"),
                Key.Letter("3"),
                Key.Letter("4"),
                Key.Letter("5"),
            ),
            listOf(
                Key.Letter("6"),
                Key.Letter("7"),
                Key.Letter("8"),
                Key.Letter("9"),
                Key.Letter("0"),
            ),
        )
    }
}
