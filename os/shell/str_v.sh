#!/bin/bash

var1="kor1"
var2=""

echo "print var1 : $var1"
echo "print var2 : $var2"

echo ""
echo "print var1 : ${var1:=test2}"
echo "print var2 : ${var2:=test2}"

