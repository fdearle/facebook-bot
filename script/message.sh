echo "Reading config...." >&2
source config.cfg

curl -X POST -H "Content-Type: application/json" -d '{
    "recipient":{
        "id": "$user_id"
    },
    "message":{
        "text":"hello, world!"
    }
}' "https://graph.facebook.com/v2.6/me/messages?access_token=$access_token"