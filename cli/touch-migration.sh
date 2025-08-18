#!/bin/sh

input="$*"
description=$(echo "$input" | sed -r 's/[ -_.]+/_/g')
filename="V$(date +%y%m%d%H%M%S)__${description:=describe_me}.sql"

touch "migrations/${filename}"

echo "Created ./migrations/${filename}"
