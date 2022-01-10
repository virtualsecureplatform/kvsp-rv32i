#!/bin/bash
set -eu

SCRIPT_DIR=$(cd $(dirname $0); pwd)
cd $SCRIPT_DIR

# Collect information
GIT_VERSION=`git rev-parse HEAD`
GIT_VERSION_SHORT=`git rev-parse --short HEAD`

# Temporary directory
BUILD_DIR=${SCRIPT_DIR}/kvsp-rv32i
rm -rf $BUILD_DIR
mkdir -p $BUILD_DIR

# Build Iyokan
rm -rf Iyokan/build
mkdir Iyokan/build
cd Iyokan/build
cmake -DCMAKE_BUILD_TYPE=Release -DIYOKAN_ENABLE_CUDA=On -DIYOKAN_ENABLE_CUDA=On -DCMAKE_CUDA_COMPILER=/usr/local/cuda/bin/nvcc -DCMAKE_C_COMPILER=clang-10 -DCMAKE_CXX_COMPILER=clang++-10 ..
make -j64
cp bin/iyokan $BUILD_DIR/.
cp bin/iyokan-packet $BUILD_DIR/.
cd $SCRIPT_DIR

# Build Core
cd core
sbt run
yosys build.ys
cp vsp-core-picorv32.json $BUILD_DIR/.
cd $SCRIPT_DIR

# Build kvsp
cd kvsp
go build
cp kvsp $BUILD_DIR/.
cp rv32i.toml $BUILD_DIR/.
cp -r rv32i-rt $BUILD_DIR/.
cd $SCRIPT_DIR

# Pack
cp README.md $BUILD_DIR/.
git rev-parse HEAD > $BUILD_DIR/version
tar -zcvf kvsp-rv32i-${GIT_VERSION_SHORT}.tar.gz kvsp-rv32i