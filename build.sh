#!/bin/bash

# kill fake-server on interrupts or when process exits
trap "cleanup" 0 2 9 15
cleanup(){
    kill -9 $(jobs -p)
    cp env/production/application.properties assets/application.properties
}

ruby fake-server.rb &
cp env/local/application.properties assets/application.properties
ant clean run-unit-tests 
if [ $? -ne 0 ]; then exit 1 ;fi 
ant uninstall
if [ $? -ne 0 ]; then exit 1 ;fi 
# do the following in a subshell so this script stays in root dir
(cd functional-tests; ant clean run-tests)



