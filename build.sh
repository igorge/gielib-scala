#!/bin/sh

PROJECT_ROOT="."

sbtpublish_impl(){
  echo ">>>>>>>>>>>>>" $1
  sbt clean
  sbt publish-local
  sbt clean
}

sbtpublish(){
  ( cd "${PROJECT_ROOT}/$1" ; sbtpublish_impl "$1") 
}

sbtpublish "gielib"

sbtpublish "gielib-scodec"



