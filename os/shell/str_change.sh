#!/bin/bash

str="/etc/test1/hello.txt"

echo $str
echo ${str%/*}
echo ${str%%/*}

echo ""

echo ${str#*/}
echo ${str##*/}

echo ""

echo ${#str}
