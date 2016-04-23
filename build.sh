#!/bin/sh

PROJECT_ROOT="."

sbtpublish_impl(){
  echo ">>>>>>>>>>>>>" $1
  sbt clean test publish-local clean
}

sbtpublish(){
  ( cd "${PROJECT_ROOT}/$1" ; sbtpublish_impl "$1") 
}

sbtpublish "gielib"

sbtpublish "gielib-scodec"

sbtpublish "gielib-javafx"



