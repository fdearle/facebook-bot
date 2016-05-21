package bot
//@Grapes([
//        @Grab(group='net.sourceforge.nekohtml', module='nekohtml', version='1.9.22'),
//        @Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7.1')
//])

import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*


class Facebook {
    def accessToken = 'CAAGvmeTzzmcBAEngaZBRtZAVtrMexkl5Nz6uOjRvxBHbgXgyTfXELE789eKY4NA33NJNjtghtj8LwoD5uqruZA1wZB04rOMLJd6H77lQmtZCassJ9skqEbZBy7hp2vd9PzYZCgFNQRRYTUUcZA15y90ypVoeOUBBLLT7vQof52mV3oj9sfYjlQMBZBEOuV1Pukw2WiEmQ1rAcRQZDZD'

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

    void sendTemplate(userId, template) {
        def http = new HTTPBuilder('https://graph.facebook.com/')

        println "Send Template $template"
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
            println "Send body $body"
            response.success = { resp, json ->
                println "POST response status: ${resp.statusLine}"
            }
            response.failure = { resp ->
                println "POST response status: ${resp.statusLine}"
            }
        }
    }
    def hasWords(words, str) {
        def strWords = str.toLowerCase().split(/\s+/)
        words.findAll { it.toLowerCase() in strWords }
    }

    def hasAllWords(words, str) {
        def strWords = str.toLowerCase().split(/\s+/)
        !(words.findAll { !(it.toLowerCase() in strWords) })
    }
}
