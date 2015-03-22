#!/bin/bash
set -ev

echo "========================================================================="
echo " Test Preparation Start"
echo "========================================================================="

cur_dir=$(pwd)

echo cur_dir

rm -rf ../src/main/resources/configure.properties
rm -rf ../src/test/resources/configure.properties
ls
ls ../
ls ../test
ls ../test/resources

cp ../src/test/resources/travis-ci.configure.properties ../src/main/resources/
mv ../src/main/resources/travis-ci.configure.properties ../src/main/resources/configure.properties
mv ../src/test/resources/travis-ci.configure.properties ../src/test/resources/configure.properties

echo "========================================================================="
echo " Test Preparation End"
echo "========================================================================="