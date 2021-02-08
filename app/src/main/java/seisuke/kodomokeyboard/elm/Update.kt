package seisuke.kodomokeyboard.elm

interface Update<Model, Message> {
    /**
     * Updates the current state and returns it.
     *
     * @param msg Incoming message.
     * @param model Current application state.
     *
     */
    fun update(msg: Message, model: Model): Model
}
