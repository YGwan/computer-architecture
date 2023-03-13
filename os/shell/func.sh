#!/bin/bash

function print() {
	echo "hello, function"
	echo $1
}

function print1() {
	echo "print1 funtcion"
	echo $1
	echo $2
}

print "hello, $1 print"
print1 "hello, $1 print $2 print1"
print1 10 12
