esolver(name='artifactory', root='http://artifactory:8081/artifactory/repo1',  m2Compatible=true)
@Grapes([
    @Grab(group='net.sourceforge.nekohtml', module='nekohtml', version='1.9.22'),
    @Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7.1')
])
import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*

def accessToken = 'CAAGvmeTzzmcBAEngaZBRtZAVtrMexkl5Nz6uOjRvxBHbgXgyTfXELE789eKY4NA33NJNjtghtj8LwoD5uqruZA1wZB04rOMLJd6H77lQmtZCassJ9skqEbZBy7hp2vd9PzYZCgFNQRRYTUUcZA15y90ypVoeOUBBLLT7vQof52mV3oj9sfYjlQMBZBEOuV1Pukw2WiEmQ1rAcRQZDZD'

def http = new HTTPBuilder('https://graph.facebook.com/')
http.request( POST, JSON ) {
    uri.path = '/v2.6/me/messages'
    uri.query = [access_token: accessToken]
    
    body =  [
        recipient: [
            id: 1094997037238723
            ],
        message: [
            text: 'From Groovy Console']
            ]

    response.success = { resp, json ->
        println "POST response status: ${resp.statusLine}"
    }
}

