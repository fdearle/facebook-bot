package com.dearle.bot

import com.amazonaws.services.lambda.runtime.Context
import groovy.json.JsonOutput
import groovy.json.JsonSlurper;
import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;

class MessengerBotService extends FacebookMessengerClient {


    AmazonS3 s3Client = new AmazonS3Client(new DefaultAWSCredentialsProviderChain())
    def logger

    Map respond(json, Context context) {
        logger = context.logger
        logger.log "Reacted to $json"
        logger.log JsonOutput.toJson(json)

        clients(json)

        for (int n = 0; n < json.entry[0]?.messaging.size(); n++) {
            if (processed(json.entry[0]?.messaging[n])) {
                if (json.entry[0].messaging[n]?.message?.text) {
                    logger.log "Send message to " + json.entry[0].messaging[n]?.sender?.id + " You said : ${json.entry[0]?.messaging[n]?.message?.text}!"
                    handleMessage(json.entry[0]?.messaging[n]?.sender?.id, json.entry[0]?.messaging[n]?.message?.text)
                } else if (json.entry[0]?.messaging[n]?.postback) {
                    logger.log "Got a postback" + json.entry[0]?.messaging[n]?.sender?.id + json.entry[0]?.messaging[n]?.postback
                    handlePostback(json.entry[0]?.messaging[n]?.sender?.id, json.entry[0]?.messaging[n]?.postback)
                }
            }
        }
        [success: true]
    }

    boolean processed(message) {
        if (message && !message?.message)
            return true

        def sender = message?.sender?.id
        def seq = message?.message?.seq

        if (!isExistS3("fb-messenger-state", "bot-${bot}-${sender}/${seq}")) {
            InputStream is = new ByteArrayInputStream("".getBytes())

            ObjectMetadata metadata = new ObjectMetadata()
            metadata.setContentLength(0)
            PutObjectRequest putObjectRequest = new PutObjectRequest("fb-messenger-state", "bot-${bot}-${sender}/${seq}", is, metadata)
            s3Client.putObject(putObjectRequest);

            logger.log "processed : true"
            return true
        }
        logger.log "processed : false"
        false
    }

    Map clients(map) {
        def sender = map.entry[0]?.messaging[0]?.sender?.id
        def clients = [clients: [sender]]

        if(!isExistS3("fb-messenger-state", "bot-${bot}-${sender}/state.json")) {
            def state = [state:[expectingAnswer: true]]
            // Set initial state object
            def json = JsonOutput.toJson(state)
            def is = new ByteArrayInputStream(json.getBytes())

            def metadata = new ObjectMetadata()
            metadata.setContentLength(json.getBytes().size())
            def putObjectRequest = new PutObjectRequest("fb-messenger-state", "bot-${bot}-${sender}/state.json", is, metadata)
            s3Client.putObject(putObjectRequest);
        }

        if (!isExistS3("fb-messenger-state", "clients.json")) {
            String json = JsonOutput.toJson(clients)
            InputStream is = new ByteArrayInputStream(json.getBytes())

            ObjectMetadata metadata = new ObjectMetadata()
            metadata.setContentLength(json.getBytes().size())
            PutObjectRequest putObjectRequest = new PutObjectRequest("fb-messenger-state", "clients.json", is, metadata)
            s3Client.putObject(putObjectRequest);
        } else {
            S3Object object = s3Client.getObject( new GetObjectRequest("fb-messenger-state", "clients.json"))

            InputStream objectData = object.getObjectContent()

            clients = new JsonSlurper().parse(objectData)
            objectData.close()

            logger.log "Clients " + clients

            if (!clients.clients.contains(sender)) {
                logger.log "Contains $sender"
                clients.clients.add(sender)

                String json = JsonOutput.toJson(clients)

                logger.log "Augmented $json"
                InputStream is = new ByteArrayInputStream(json.getBytes())

                ObjectMetadata metadata = new ObjectMetadata()
                metadata.setContentLength(json.getBytes().size())
                PutObjectRequest putObjectRequest = new PutObjectRequest("fb-messenger-state", "clients.json", is, metadata)
                s3Client.putObject(putObjectRequest);
            }
        }

        clients
    }


    public boolean isExistS3(String bucketName, String file) {

        ObjectListing objects = s3Client.listObjects(new ListObjectsRequest().withBucketName(bucketName).withPrefix(file));

        for (S3ObjectSummary objectSummary: objects.getObjectSummaries()) {
            if (objectSummary.getKey().equals(file)) {
                return true;
            }
        }
        return false;
    }

    def hasWords(words, str) {
        def strWords = str.toLowerCase().split(/\s+/)
        words.findAll { it.toLowerCase() in strWords }
    }

    def hasAllWords(words, str) {
        def strWords = str.toLowerCase().split(/\s+/)
        !(words.findAll { !(it.toLowerCase() in strWords) })
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

    def handleMessage(userId, String message) {

    }

    def handlePostback(userId, postback) {
    }

}
