#!/bin/bash

str1="hello_str"

function print() {
	local str2="I am str2"
	echo $str1
	echo $str2
}

print
echo "global $str1"
echo "local $str2"
