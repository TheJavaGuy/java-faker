---
name: cleanup-branch
description: Switches to master, pulls latest changes, and deletes the branch you were on. Safe to use after a PR is merged.
disable-model-invocation: true
allowed-tools: Bash
---

Run the following commands in order:

```bash
# Capture current branch name before switching
BRANCH=$(git rev-parse --abbrev-ref HEAD)

# Switch to master and pull
git checkout master && git pull

# Delete the feature branch (-D handles branches already merged via remote)
[ "$BRANCH" != "master" ] && git branch -D "$BRANCH" && echo "Deleted branch: $BRANCH"
```

Report the branch that was deleted and the current state of master (latest commit).
