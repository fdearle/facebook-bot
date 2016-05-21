package com.dearle.bot

import com.amazonaws.services.lambda.runtime.Context
import groovy.json.JsonOutput

import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*

class BasicBotService extends FacebookMessengerClient {


    def logger

    Map respond(json, Context context) {
        logger = context.logger
        logger.log "Reacted to $json"
        logger.log JsonOutput.toJson(json)

        [success: true]
    }

    Map verify(request, Context context) {
        logger = context.logger
        logger.log "Reacted to $request"
        logger.log JsonOutput.toJson(request)

        int response
        if (request.params.querystring["hub.verify_token"] == "let_me_in") {
            response = Integer.valueOf(request.params.querystring["hub.challenge"])
        } else {
            response = -1
        }

        [challenge: response]
    }

}
