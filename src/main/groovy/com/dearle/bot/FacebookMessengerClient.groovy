package com.dearle.bot

import groovy.json.*
import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*

class FacebookMessengerClient {
//    def bot = 1
//    def accessToken = 'EAACtWxlZBPvUBANOqyQL1vrh4YtJhcK6ZAazZCeHjjDRJa9NOM4o3ZCTVmrsdgu5YMZCw6wVS8T4PjhBIInjgkSKLfYRKaStEBIH0j8OZCEHWWFyuoQ4WTuRGtD4GFeb6xmJ6PAYMrKaS0u5KCHSiPjwH3KqZB0Th8cUmozL8a31gZDZD'
//    def bot = 2
//    def accessToken = 'EAAYNReg0EgsBAODE8PtKHXlMcBl49rQUgc9O96bhhjpmQeRwhMfL3bOFG9ohxQkuDsmh1WcOy1PZB5psRXLJJBz5Yiii06QlOKdjtEvJR9ZB02zqk8n3HOs7anihbpgZCLZCDTdqwP98aIxlprw3YoJiMVF0hj1GeUeF0m6higZDZD'
    def bot = 3
    def accessToken = 'EAADQZCWqeQsYBAKW3sYiewZARXYhfXiTPysxJZCEMV1bjw3Iq4jlCOZAZBvZBO4qZAQ8zGrQW3j2VuZBQb4u4OGeZBxYb5SihZBde3FIKyRmZBeJVDC7HiC0lfYa6nvUZC4PDsG0ZB0tmQ7NM9p9jhtwD0EOurdZAgpZC8VzxDQiNDWlu1HuQZDZD'

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
