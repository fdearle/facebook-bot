@Grapes([
        @Grab(group='net.sourceforge.nekohtml', module='nekohtml', version='1.9.22'),
        @Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7.1')
])

import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*


class Facebook {
    def bot = 1
    def accessToken = 'EAACtWxlZBPvUBANOqyQL1vrh4YtJhcK6ZAazZCeHjjDRJa9NOM4o3ZCTVmrsdgu5YMZCw6wVS8T4PjhBIInjgkSKLfYRKaStEBIH0j8OZCEHWWFyuoQ4WTuRGtD4GFeb6xmJ6PAYMrKaS0u5KCHSiPjwH3KqZB0Th8cUmozL8a31gZDZD'
//    def bot = 2
//    def accessToken = 'EAAYNReg0EgsBAODE8PtKHXlMcBl49rQUgc9O96bhhjpmQeRwhMfL3bOFG9ohxQkuDsmh1WcOy1PZB5psRXLJJBz5Yiii06QlOKdjtEvJR9ZB02zqk8n3HOs7anihbpgZCLZCDTdqwP98aIxlprw3YoJiMVF0hj1GeUeF0m6higZDZD'
//    def bot = 3
//    def accessToken = 'EAADQZCWqeQsYBAKW3sYiewZARXYhfXiTPysxJZCEMV1bjw3Iq4jlCOZAZBvZBO4qZAQ8zGrQW3j2VuZBQb4u4OGeZBxYb5SihZBde3FIKyRmZBeJVDC7HiC0lfYa6nvUZC4PDsG0ZB0tmQ7NM9p9jhtwD0EOurdZAgpZC8VzxDQiNDWlu1HuQZDZD'

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
