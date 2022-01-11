#!/bin/bash
set -eu

KVSP_PATH="kvsp/kvsp"
TEST_FILES="core/src/test/c/*"
TEST_BINARY_DIR="core/src/test/binary"

SCRIPT_DIR=$(cd $(dirname $0); pwd)
cd $SCRIPT_DIR

for FILE_PATH in $TEST_FILES; do
    FILE=`basename $FILE_PATH`
    $KVSP_PATH genTest $FILE_PATH > ${TEST_BINARY_DIR}/test-${FILE}.json
done
