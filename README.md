# The Jolly Project

![The Jolly Logo](https://github.com/JDA8106/jolly/raw/master/jolly2.png)

## Jolly, a Fault-Tolerance Library for Java

The Jolly project is a simple fault-tolerance library inspired by the [Polly](https://github.com/App-vNext/Polly) Project.

Our [GitHub repository](https://github.com/JDA8106/jolly)

Our [documentations](https://jda8106.github.io/jolly/)

## Open Sourced on GitHub

Jolly is open sourced on GitHub and is licensed under the [MIT License](http://opensource.org/licenses/MIT).

## Release Notes:
###Software Features for this Release
####Retry Asynchronous
####Circuit-breaker
  - Synchronous
  - Asynchronous 
  
####Timeout
  - Synchronous
  - Asynchronous
  
####Cache
  - Synchronous
  - Asynchronous 
####Fallback
  - Synchronous
  - Asynchronous 
  
####Bulkhead Isolation
  - Synchronous
  - Asynchronous 
  
###Bug Fixes Since Last Release (April 2018):
####Adding asynchronous implementation for Retry
####Changing Policy super class to accommodate generic types
###Known Defects:
#####PolicyWrap: Though not promised, this pattern was a part of our backlog and Resilience4j, but we could not get to it because of other sprint goals and time constraints. 
#####Documentations: Lack navigation features 

##Install Guide:
###Pre-requisites:
####Java 8 SDK
####Gradle
####Text editor or IDE (we recommend IntelliJ)
###Dependent Libraries Required:
####Other dependencies (namely, Resilience4j) will be installed by Gradle
###Download Instructions:
![Download](https://github.com/JDA8106/jolly/raw/master/Download%20ZIP.png)
####Download zip file from GitHub repo
####Unzip it
####Open the code directory in terminal (use cd linux command)
###Build instructions (if needed):
if you are providing the raw source code rather
than a binary build, how will the customer and users create the required executable
application?
###Installation of actual application: 
what steps have to be taken after the software is
built? What directories are required for installation?
###Run instructions:
what does the user/customer have to do to get the software to
Execute?
###Troubleshooting: 
#####Ensure your Java and Gradle versions are compatible
#####Ensure proper installation of dependencies
