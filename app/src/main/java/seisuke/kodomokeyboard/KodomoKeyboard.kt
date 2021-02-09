package seisuke.kodomokeyboard

import android.inputmethodservice.InputMethodService
import android.view.KeyEvent
import android.view.View
import androidx.compose.ui.platform.ComposeView
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
import seisuke.kodomokeyboard.model.KeyboardState
import seisuke.kodomokeyboard.model.KodomoKeyboardUpdate
import seisuke.kodomokeyboard.ui.RootView

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

        val clickKeyAction: (String) -> Unit = { text ->
            currentInputConnection.apply {
                commitText(text, 1)
            }
        }

        val sandBox = SandBox.create(
            KeyboardState.create(),
            KodomoKeyboardUpdate(
                clickKeyAction,
                ::upDownDeleteKey
            ),
            lifecycleScope
        )

        view.setContent {
            RootView(sandBox)
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
}
