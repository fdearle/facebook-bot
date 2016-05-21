# This script setups a welcome page for the bot

echo "Reading config...." >&2
source config.cfg

curl -X POST -H "Content-Type: application/json" -d '{
  "setting_type":"call_to_actions",
  "thread_state":"new_thread",
  "call_to_actions":[
    {
      "message":{
        "text":"Hey there, want to talk all things Arsenal FC?"
      }
    }
  ]
}' "https://graph.facebook.com/v2.6/$page_id/thread_settings?access_token=$access_token"