#!/usr/bin/env bash

me="$(basename "$(test -L "$0" && readlink "$0" || echo "$0")")"

if [ -z $AZURE_WA_USERNAME -o -z $AZURE_WA_PASSWORD ]; then
    echo -e "ERROR: AZURE_WA_USERNAME or AZURE_WA_PASSWORD varaibles can not be found. Exit."
    exit 1
fi

AZURE_ENV="$1"
if [ -z "$AZURE_ENV" ]; then
    echo -e "\n[$me] You must specify environment you want to deploy\n"
    exit 1
fi

AZURE_REPO="$2"
if [ -z "$AZURE_REPO" ]; then
  AZURE_REPO=$AZURE_ENV
fi

if [ -d "$AZURE_ENV" ]; then
    echo -e "\n[$me] Directory $AZURE_ENV exists. Removing it now\n"
    rm -rf $AZURE_ENV
fi

echo -e "\n[$me] Going to clone $AZURE_ENV repository using https://${AZURE_WA_USERNAME}:${AZURE_WA_PASSWORD}@"$AZURE_ENV".scm.azurewebsites.net:443/"$AZURE_REPO".git\n"

if ! git clone https://${AZURE_WA_USERNAME}:${AZURE_WA_PASSWORD}@"$AZURE_ENV".scm.azurewebsites.net:443/"$AZURE_REPO".git; then
    echo -e "\nERROR: can not clone source. Exit.\n"
    exit 1
fi

echo -e "\n[$me] Executing Rsync\n"

# exclude your AZURE_REPO here!
rsync -av --delete --exclude '.git' --exclude 'ci' --exclude "$AZURE_REPO" . "$AZURE_REPO"/

cd "$AZURE_REPO"/

echo -e "\n[$me] Deploying to $AZURE_REPO:\n"
echo -e "[$me] Working in: $(pwd)\n"

echo -e "\n[$me] AZURE_REPO $AZURE_REPO content to be deployed:\n"
ls -l
echo

git add .
git status
git commit -m "Release build $TRAVIS_BUILD_NUMBER" || echo "[$me] Nothing to commit!"
git push origin master

echo -e "[$me] Done.\n"