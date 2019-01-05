#!/usr/bin/env bash

git fetch upstream
git checkout master
git merge upstream/master

echo "Update done... git push as the next step."