#!/usr/bin/env bash
# 要Bash,Curl,jq

WORDNAME=""
KEYWORD=$(echo "${WORDNAME}" | nkf -WwMQ | sed 's/=$//g' | tr = % | tr -d '\n')
TOKEN=$(curl \
    -H "user-agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.102 Safari/537.36" \
    -s \
    "https://tver.jp/api/access_token.php?_t=$(date +%s)" \
    | jq -r '.token')
read -p "検索キーワードは${WORDNAME}です。\nTOKENは${TOKEN}。"
curl \
    -s \
    -H "user-agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.102 Safari/537.36" \
    "https://api.tver.jp/v4/search?keyword=${KEYWORD}&catchup=1&soon=1&recommend=1&watched=&token=${TOKEN}&pref=&_=$(date +%s)" \
    | jq -r '.data[] | .href' \
    | xargs -i ./  --output "\\\\YOUR-PC\\C$\\Tver\" https://tver.jp{}