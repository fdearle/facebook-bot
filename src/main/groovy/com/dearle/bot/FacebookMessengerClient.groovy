package com.dearle.bot

import groovy.json.*
import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*

class FacebookMessengerClient {
//    def bot = 1
//    def accessToken = 'access_token'
//    def bot = 2
//    def accessToken = 'access_token'
    def bot = 3
    def accessToken = 'access_token'

    void sendTextMessage(userId, message) {
        def http = new HTTPBuilder('https://graph.facebook.com/')
        http.request( POST, JSON ) {
            uri.path = '/v2.6/me/messages'
            uri.query = [access_token: accessToken]
            body =  [
                    recipient: [
                            id: userId
                    ],
                    message: [
                            text: message.toString()
                    ]
            ]

            response.success = { resp, json ->
                logger.log "POST response status: ${resp.statusLine}"
            }
            response.failure = { resp ->
                logger.log "POST response status: ${resp.statusLine}"
            }
        }
    }

    void sendImageMessage(userId, url) {
        def http = new HTTPBuilder('https://graph.facebook.com/')
        http.request( POST, JSON ) {
            uri.path = '/v2.6/me/messages'
            uri.query = [access_token: accessToken]
            body =  [
                    recipient: [
                            id: userId
                    ],
                    message: [
                            attachment: [
                                    type: "image",
                                    payload: [
                                            url: url
                                    ]
                            ]
                    ]
            ]

            response.success = { resp, json ->
                logger.log "POST response status: ${resp.statusLine}"
            }
            response.failure = { resp ->
                logger.log "POST response status: ${resp.statusLine}"
            }
        }
    }

    void sendTemplate(userId, template) {
        def http = new HTTPBuilder('https://graph.facebook.com/')

        logger.log "Send Template $template"
        http.request( POST, JSON ) {
            uri.path = '/v2.6/me/messages'
            uri.query = [access_token: accessToken]
            body =  [
                    recipient: [
                            id: userId
                    ],
                    message: [
                            attachment: [
                                    type: "template",
                                    payload: template
                            ]
                    ]
            ]
            logger.log "Send body $body"
            response.success = { resp, json ->
                logger.log "POST response status: ${resp.statusLine}"
            }
            response.failure = { resp ->
                logger.log "POST response status: ${resp.statusLine}"
            }
        }
    }
}
