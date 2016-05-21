echo "Reading config...." >&2
source config.cfg

curl -ik -X POST "https://graph.facebook.com/v2.6/me/subscribed_apps?access_token=$access_token"