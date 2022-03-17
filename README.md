
# Itunes Google Assesment

## Introduction

I had an assesment from the company Kramp Hub in Netherlans, that holpfully i passed:

*Using Java framework / libraries build a service, that will accept a request with text parameter on input. It will return maximum of 5 books and maximum of 5 albums that are related to the input term. The response elements will only contain title, authors(/artists) and information whether it's a book or an album. For albums please use the iTunes API. For books please use Google Books API. Sort the result by title alphabetically. Make sure the software is production-ready from resilience, stability and performance point of view. The stability of the downstream service may not be affected by the stability of the upstream services. Results originating from one upstream service (and its stability /performance) may not affect the results originating from the other upstream service.*

## Technologies 

 - Lombok
 - Controller Advice
 - Webflux
 - Resilience4j
 - Docker
 - Kubernetes

## Lombok

As almost all my projects i use lombok in order to simplify some classes to avoid code verbosity. To use it in the project, you need to write this in **pom.xml** file:
```
		<dependency>
		    <groupId>org.projectlombok</groupId>
		    <artifactId>lombok</artifactId>
		    <scope>provided</scope>
		</dependency>	
```

## Controller Advice

To centralize exception treatments i use controller advice, so i can manage in one unique place the exception that are thrown all over the system.

## Webflux

This is the key of this development.  I had to make two calls to differents APIs that no one of this APIs depends on the other one. So if one of them throw an exception, the other musn't has to be affected. To achive this, i used **webflux**. 

```
		<dependency>
    			<groupId>org.springframework.boot</groupId>
	    		<artifactId>spring-boot-starter-webflux</artifactId>
		</dependency>
```

