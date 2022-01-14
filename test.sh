#!/bin/bash

set -u

KVSP_PATH="kvsp/kvsp"
TEST_FILES="core/src/test/c/*"

SCRIPT_DIR=$(cd $(dirname $0); pwd)
cd $SCRIPT_DIR

./gen_tests.sh

echo "Testing core.."
cd core
#sbt test
cd $SCRIPT_DIR

echo "Testing Iyokan plain mode..."
for FILE_PATH in $TEST_FILES; do
    echo "  Test ${FILE_PATH}"
    $KVSP_PATH plaintest $FILE_PATH &> /dev/null
    if [ $? -ne 0 ]; then
        echo "test failed"
        exit 1
    fi
done
echo "Test passed"

SKEY=/tmp/sk
BKEY=/tmp/bk

$KVSP_PATH genkey -o $SKEY &> /dev/null
$KVSP_PATH genbkey -i $SKEY -o $BKEY &> /dev/null
echo "Testing Iyokan TFHE mode..."
for FILE_PATH in $TEST_FILES; do
    echo "  Test ${FILE_PATH}"
    $KVSP_PATH tfhetest -k $SKEY -bkey $BKEY -i $FILE_PATH -g 2 &> /dev/null
    if [ $? -ne 0 ]; then
        echo "test failed"
        exit 1
    fi
done

rm $SKEY $BKEY
exit 0
