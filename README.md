# The Jolly Project

![The Jolly Logo](https://github.com/JDA8106/jolly/raw/master/jolly2.png)
![The Jolly Team](https://github.com/JDA8106/jolly/raw/master/Contact.png)

## Jolly, a Fault-Tolerance Library for Java

The Jolly project is a simple fault-tolerance library inspired by the [Polly](https://github.com/App-vNext/Polly) Project.

Our [GitHub repository](https://github.com/JDA8106/jolly)

Our [documentations](https://jda8106.github.io/jolly/)

## Open Sourced on GitHub

Jolly is open sourced on GitHub and is licensed under the [MIT License](http://opensource.org/licenses/MIT).

## Release Notes:
![The Jolly Patterns](https://github.com/JDA8106/jolly/raw/master/poster.png)
### Software Features for this Release
#### Retry Asynchronous
#### Circuit-breaker
  - Synchronous
  - Asynchronous
  
#### Timeout
  - Synchronous
  - Asynchronous
  
#### Cache
  - Synchronous
  - Asynchronous

#### Fallback
  - Synchronous
  - Asynchronous

#### Bulkhead Isolation
  - Synchronous
  - Asynchronous 
  
### Bug Fixes Since Last Release (April 2018):
#### Adding asynchronous implementation for Retry
#### Changing Policy super class to accommodate generic types
### Known Defects:
##### PolicyWrap: Though not promised, this pattern was a part of our backlog and Resilience4j, but we could not get to it because of other sprint goals and time constraints. 
##### Documentations: Lack navigation features 

## Install Guide:
### Pre-requisites:
- Java 8 SDK
- Gradle
- Text editor or IDE (we recommend IntelliJ)
### Dependent Libraries Required:
- Other dependencies (namely, Resilience4j) will be installed by Gradle
### Installation into your application:
To your `build.gradle` file, make the following additions:
- Add `maven { url "https://jolly.bintray.com/jolly" }` to the `repositories` section.
- Add `compile { "io.github.jolly:jolly" }` to the `dependencies` section.
### Download Instructions (from source):
**Note that downloading the source is only needed for development on the library itself.  Go back to "Installation" to use the library in your application.**

![Download](https://github.com/JDA8106/jolly/raw/master/Download%20ZIP.png)
1. Download zip file from GitHub repo
2. Unzip it
### Build instructions (from source):
1. Open the code directory in terminal (use cd linux command)
2. Run `gradle build`
### Run instructions (from source):
To demo the library, run the file `src/main/java/io/github/jolly/demo/JollySample.java`.
### Troubleshooting: 
- Ensure your Java and Gradle versions are compatible
- Ensure proper installation of dependencies
