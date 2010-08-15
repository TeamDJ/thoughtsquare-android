#!/bin/bash

ruby fake-server.rb &
# kill fake-server on interrupts or when process exits
trap "cleanup()" 0 2 9 15

cp env/local/application.properties assets/application.properties
ant clean uninstall 
cd functional-tests
ant clean run-tests

cleanup(){
    kill -9 $(jobs -p)
    cp env/production/application.properties assets/application.properties
}


