package uk.co.telegraph.bot

import com.amazonaws.services.lambda.runtime.Context;

import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*

class MessengerBotService {

    def accessToken = 'CAAGvmeTzzmcBAEngaZBRtZAVtrMexkl5Nz6uOjRvxBHbgXgyTfXELE789eKY4NA33NJNjtghtj8LwoD5uqruZA1wZB04rOMLJd6H77lQmtZCassJ9skqEbZBy7hp2vd9PzYZCgFNQRRYTUUcZA15y90ypVoeOUBBLLT7vQof52mV3oj9sfYjlQMBZBEOuV1Pukw2WiEmQ1rAcRQZDZD'

    Map respond(json, Context context) {
        context.logger.log "Reacted to $json"

        if (json?.entry[0]?.messaging[0]?.message?.text) {
            context.logger.log "Send message to " + json?.entry[0]?.messaging[0]?.sender?.id + " You said : ${json?.entry[0]?.messaging[0]?.message?.text}!"
            sendTextMessage(json?.entry[0]?.messaging[0]?.sender?.id, "You said : ${json?.entry[0]?.messaging[0]?.message?.text}!")
            sendTemplate(json?.entry[0]?.messaging[0]?.sender?.id)
        }


        [success: true]
    }

    Map verify(request, Context context) {
        context.logger.log "Reacted to $request"
        int response
        if (request.params.querystring["hub.verify_token"] == "let_me_in") {
            response = Integer.valueOf(request.params.querystring["hub.challenge"])
        } else {
            response = -1
        }

        [challenge: response]
    }

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
                println "POST response status: ${resp.statusLine}"
            }
            response.failure = { resp ->
                println "POST response status: ${resp.statusLine}"
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
                println "POST response status: ${resp.statusLine}"
            }
            response.failure = { resp ->
                println "POST response status: ${resp.statusLine}"
            }
        }
    }

    void sendTemplate(userId) {
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
                                    type: "template",
                                    payload: [
                                            template_type: "button",
                                            text: "Navigate to a section",
                                            buttons: [
                                                    [
                                                        type: "web_url",
                                                        url: "http://www.telegraph.co.uk",
                                                        title: "News"
                                                    ],
                                                    [
                                                            type: "web_url",
                                                            url: "http://www.telegraph.co.uk/sport/",
                                                            title: "Sport"
                                                    ],
                                                    [
                                                            type: "web_url",
                                                            url: "http://www.telegraph.co.uk/culture/",
                                                            title: "Culture"
                                                   ]
                                            ]
                                    ]
                            ]
                    ]
            ]

            response.success = { resp, json ->
                println "POST response status: ${resp.statusLine}"
            }
            response.failure = { resp ->
                println "POST response status: ${resp.statusLine}"
            }
        }
    }
}
