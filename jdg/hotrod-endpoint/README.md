hotrod-endpoint: Use JDG remotely through Hotrod
================================================
Plagarized by:  JA Bride
Author: Tristan Tarrant, Martin Gencur
Level: Intermediate
Technologies: Infinispan, Hot Rod
Summary: Demonstrates how to use Infinispan remotely using the Hot Rod protocol.
Target Product: JDG

What is it?
-----------

Hot Rod is a binary TCP client-server protocol used in JBoss Data Grid. The Hot Rod protocol facilitates faster client and server interactions in comparison to other text based protocols and allows clients to make decisions about load balancing, failover and data location operations.

This quickstart demonstrates how to connect remotely to JBoss Data Grid (JDG) to store, retrieve, and remove data from cache using the Hot Rod protocol. It is a simple Football Manager console application allows you to add and remove teams, add players to or remove players from teams, or print a list of the current teams and players using the Hot Rod based connector.

the build script creates an lab environment with the following JVMs:

1)  JBoss Data Grid node:  gpe00
        - configures a distributed cache with the the name of:  team

2)  JBoss Data Grid node:  gpe01
        - configures a distributed cache with the the name of:  team

3)  JBoss EAP6.0* JVM configured using the 'standard' profile
        - used to host the the RESTful service containing the embedded hotrod client


System requirements
-------------------

All you need to build this project is Java 6.0 (Java SDK 1.6)  and ant version 1.8.3 or greater
The application this project produces is designed to be run on JBoss Data Grid 6.1 


Procedure
-----------
1)  review build.properties and build.xml of this project
2)  execute:   ant
3)  curl -v -X GET http://$HOSTNAME:8080/hotrod-endpoint/teams
4)  observe thread count of JDG nodes:
        ps -o thcount -p <PID of JDG nodes>
