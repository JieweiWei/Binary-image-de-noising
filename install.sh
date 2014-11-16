#!/bin/bash

# Author: Jiewei Wei
# Copyright: Jiewei Wei
# Script follows here:

if which java > /dev/null;
then
  echo "you have installed Java"
else
  echo "please install Java first"
fi

if which ant > /dev/null;
then
  echo "you have installed ant"
else
  echo "please install ant first!"
  if uname -a| grep "Ubuntu";
  then
    sudo apt-get install ant
  fi
fi
