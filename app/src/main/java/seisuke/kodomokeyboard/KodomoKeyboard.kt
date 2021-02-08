package seisuke.kodomokeyboard

import android.inputmethodservice.InputMethodService
import android.view.KeyEvent
import android.view.View
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.lifecycle.ViewTreeViewModelStoreOwner
import androidx.lifecycle.lifecycleScope
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import androidx.savedstate.ViewTreeSavedStateRegistryOwner
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import seisuke.kodomokeyboard.elm.SandBox
import seisuke.kodomokeyboard.model.Key
import seisuke.kodomokeyboard.model.KeyList
import seisuke.kodomokeyboard.model.KeyboardState
import seisuke.kodomokeyboard.model.KodomoKeyboardUpdate
import seisuke.kodomokeyboard.ui.KeyboardView
import seisuke.kodomokeyboard.ui.OptionButtons

class KodomoKeyboard :
    InputMethodService(),
    LifecycleOwner,
    ViewModelStoreOwner,
    SavedStateRegistryOwner {

    private val lifecycleRegistry: LifecycleRegistry = LifecycleRegistry(this)
    private val savedStateRegistryController = SavedStateRegistryController.create(this)
    private val store = ViewModelStore()

    @ExperimentalCoroutinesApi
    @FlowPreview
    override fun onCreateInputView(): View = ComposeView(this).also { view ->
        ViewTreeLifecycleOwner.set(view, this)
        ViewTreeViewModelStoreOwner.set(view, this)
        ViewTreeSavedStateRegistryOwner.set(view, this)

        val keyboardAction: (String) -> Unit = { text ->
            currentInputConnection.apply {
                commitText(text, 1)
            }
        }

        val sandBox = SandBox.create(
            KeyboardState.create(),
            KodomoKeyboardUpdate(),
            lifecycleScope
        )

        view.setContent {
            MaterialTheme(
                colors = originalLightColors()
            ) {
                Row {
                    KeyboardView(
                        modifier = Modifier
                            .weight(0.1f)
                            .padding(top = 8.dp),
                        keyList = KEY_LIST,
                        stateFlow = sandBox.stateFlow,
                        onClickAction = keyboardAction
                    )

                    OptionButtons(
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(top = 8.dp, start = 8.dp, end = 8.dp),
                        sandBox,
                        ::upDownDeleteKey
                    )
                }
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        savedStateRegistryController.performRestore(null)
        handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
    }

    override fun onDestroy() {
        super.onDestroy()
        handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    }

    override fun getLifecycle(): Lifecycle = lifecycleRegistry

    override fun getSavedStateRegistry(): SavedStateRegistry =
        savedStateRegistryController.savedStateRegistry

    override fun getViewModelStore(): ViewModelStore = store

    private fun handleLifecycleEvent(event: Lifecycle.Event) =
        lifecycleRegistry.handleLifecycleEvent(event)

    private fun upDownDeleteKey() {
        currentInputConnection.sendKeyEvent(
            KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL)
        )
        currentInputConnection.sendKeyEvent(
            KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DEL)
        )
    }

    private fun originalLightColors(): Colors = lightColors(
        primary = Red700,
        primaryVariant = Red900,
        onPrimary = Color.White,
        secondary = Red700,
        secondaryVariant = Red900,
        onSecondary = Color.White,
        error = Red800
    )

    companion object {
        val Red700 = Color(0xffdd0d3c)
        val Red800 = Color(0xffd00036)
        val Red900 = Color(0xffc20029)

        private val KEY_LIST: KeyList = listOf(
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
