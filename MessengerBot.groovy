class MessengerBot extends Facebook {
    def handleMessage(userId, String message) {

        if (hasWords(["help", "helping", "assist", "assistance"], message)) {
            help(userId)
        } else {
            sendTextMessage(userId, "You said, '${message}'")
            sendTextMessage(userId, "I don't quite understand your meaning! Type help for some hints as to how to talk ot me.")

        }
    }

    def handlePostback(userId, postback) {
        logger.log "In handlePostback ${postback.payload}"
        switch (postback.payload) {
            default:
                logger.log "No postbacks found"
        }
    }

    void help(userId) {
        sendTextMessage(userId,
                "Type a question and see if I understand")
    }

}