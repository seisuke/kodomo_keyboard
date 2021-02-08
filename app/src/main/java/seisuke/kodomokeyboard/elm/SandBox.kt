package seisuke.kodomokeyboard.elm

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.launch

abstract class SandBox<Model, Message> private constructor() {

    abstract val stateFlow: StateFlow<Model>

    abstract fun accept(msg: Message)

    companion object {
        @FlowPreview
        @ExperimentalCoroutinesApi
        fun <Model, Message> create(
            initial: Model,
            update: Update<Model, Message>,
            coroutineScope: CoroutineScope
        ): SandBox<Model, Message> {

            val mutableMessageFlow = MutableSharedFlow<Message>()
            val mutableStateFlow = MutableStateFlow(initial)

            mutableMessageFlow
                .scan(mutableStateFlow.value) { model, msg -> update.update(msg, model) }
                .onEach { model -> mutableStateFlow.value = model }
                .launchIn(CoroutineScope(Dispatchers.IO))

            return object : SandBox<Model, Message>() {

                override val stateFlow = mutableStateFlow

                override fun accept(msg: Message) {
                    coroutineScope.launch {
                        mutableMessageFlow.emit(msg)
                    }
                }
            }
        }
    }
}
