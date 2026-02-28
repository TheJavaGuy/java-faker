#!/usr/bin/env bash
# Creates a PR from the current branch targeting this repo's master branch.
# Usage: ./create-pr.sh [title] [body]
#   If title/body are omitted, they are derived from the branch name and commit log.

set -euo pipefail

REPO="TheJavaGuy/java-faker"
BASE="master"
BRANCH=$(git rev-parse --abbrev-ref HEAD)

if [[ "$BRANCH" == "$BASE" ]]; then
  echo "Error: already on $BASE — create a feature branch first." >&2
  exit 1
fi

# Use provided title or derive from branch name (kebab-case → sentence)
TITLE="${1:-$(echo "$BRANCH" | tr '-' ' ')}"

# Use provided body or build from commits since master
if [[ $# -ge 2 ]]; then
  BODY="$2"
else
  COMMITS=$(git log "$BASE".."$BRANCH" --oneline)
  BODY="$(cat <<EOF
## Summary

$(git log "$BASE".."$BRANCH" --pretty=format:"- %s")

## Commits

\`\`\`
$COMMITS
\`\`\`

## Test plan

- [ ] All tests pass: \`./mvnw test\`
EOF
)"
fi

echo "Creating PR: '$TITLE'"
echo "  repo:   $REPO"
echo "  base:   $BASE"
echo "  head:   $BRANCH"
echo ""

gh pr create \
  --repo "$REPO" \
  --base "$BASE" \
  --head "$BRANCH" \
  --title "$TITLE" \
  --body "$BODY"
