#!/bin/bash

str1="str1"
str2=""
error_msg="Error"

echo ${str1:?$error_msg}
echo ${str2:?$error_msg}

