#!/bin/bash

str="hello park yong gwan hello park"

echo ${str/hello/hi}
echo ${str//hello/hi}

echo ""
echo ${str/hello}
echo ${str//hello}

echo ""
echo ${str/#he/what?}
echo ${str/%lo/what?}
