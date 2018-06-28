#!/bin/sh
java --illegal-access=deny --add-modules java.xml.bind --add-modules java.activation --add-opens java.base/java.util=java.xml.bind -cp /home/greg/test/build/classes:/home/greg/test/cricket-1.2.46.jar org.cricketmsf.Runner -c src/admin.json -s Microsite -r
