#! /bin/sh

BRANCH=gh-pages

cd $WORKSPACE

version_line=$(cat pom.xml | egrep -o "<version>(.+)</version>" | head -n 1)
version=$(expr match $version_line "<version>\(.\+\)</version>")

if [[ $version == *-SNAPSHOT ]]; then
    dirname="apidocs-dev"
    commit_version="$version-b$BUILD_NUMBER"
else
    dirname="apidocs"
    commit_version="$version"
fi

rm -rf /tmp/${JOB_NAME}_apidocs
cp -R target/site/apidocs /tmp/${JOB_NAME}_apidocs

git checkout $BRANCH
git pull origin $BRANCH

rm -rf ./$dirname
mv /tmp/${JOB_NAME}_apidocs ./$dirname
git add . && git add -u
git commit -m "Update javadoc for $commit_version"

git push origin $BRANCH
git checkout master
