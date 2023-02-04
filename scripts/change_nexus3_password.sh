#!/bin/bash
set -eo pipefail

OLD_PASSWORD=$(docker exec fly-gradle-nexus3 cat /nexus-data/admin.password)
export OLD_PASSWORD
export NEW_PASSWORD="admin123"

curl -ifu admin:"${OLD_PASSWORD}" -XPUT -H 'Content-Type: text/plain' \
  --data "${NEW_PASSWORD}" \
  http://localhost:9908/service/rest/v1/security/users/admin/change-password
