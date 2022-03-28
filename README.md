
# Itunes Google Assesment

## Introduction

I had an assesment from the company [**Kramp Hub**](https://www.kramphub.nl/) in Netherlands.

*Using Java framework / libraries build a service, that will accept a request with text parameter on input. It will return maximum of 5 books and maximum of 5 albums that are related to the input term. The response elements will only contain title, authors(/artists) and information whether it's a book or an album. For albums please use the iTunes API. For books please use Google Books API. Sort the result by title alphabetically. Make sure the software is production-ready from resilience, stability and performance point of view. The stability of the downstream service may not be affected by the stability of the upstream services. Results originating from one upstream service (and its stability /performance) may not affect the results originating from the other upstream service.*

## Technologies 

 - Lombok
 - Controller Advice
 - Webflux
 - Resilience4j - Circuit Breaker
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

This is the key of this development.  I had to make two calls to differents APIs that no one of this APIs depends on the other one. So if one of them fails the other musn't nned to be affected. To achive this, i used **webflux**. 

```
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-webflux</artifactId>
</dependency>
```
I make two calls simultaneously, separetly, but in the end i aggregate both results in one response to the request received. This allow me to simplify all logic of doing two calls, and avoid failures problems. The main code looks like this:
```
public Mono<ResultAggregate> get(String term) {

	log.info("Starting search of term: {}.", term);

	return Mono.zip(
			this.itunesService.getItunes(term, limit),
			this.googleService.getGoogle(term, limit)
		)
	.map(this::combine);

}

private ResultAggregate combine(Tuple2<ItunesResults, GoogleApiDTO> tuple) {
	return ResultAggregate.create(
			tuple.getT2(), 
			tuple.getT1()
		);
}
```
So after calling to the Itunes API and Google Books API, i aggregate both results into one.

## Resilience4j - Circuit Breaker

In order to protect the stability of the API, i used a circuit breaker in an **AOP** way. So after many requests from a client, if one of the API -Itunes, Google API- throws an error, a **circuit breaker** that i implemented, will **OPEN** the circuit, and no other call will happend to that server.  

What is the gain? So, as i told before, one of the APIs doesn`t answer properly, the other won't be affected, and after some other negative requests, and when the circuit opens, the other APIs will still answer, and our API won't be hurt of this unproper behaivor.

To use this, i add this in the pom file:

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>	

<dependency>
    <groupId>io.github.resilience4j</groupId>
    <artifactId>resilience4j-circuitbreaker</artifactId>
</dependency>	

<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-circuitbreaker-reactor-resilience4j</artifactId>
</dependency>
```
I annotate those methods that can`t be called many times because of the circuit breaker, with an annotation like this:

```
@CircuitBreaker(name = "itunes", fallbackMethod = "fallback")
```
When the circuit opens, the fallback methods will execute, returning a new empty object like this:

```
private Mono<ItunesResults> fallback(String term, Integer limit, Throwable throwable) {
	log.error("Circuit breaker active: {}", throwable.getMessage(), throwable);
	return Mono.just(new ItunesResults());
}
```

So, in error cases, when the circuit its running, the fallback method will return an empty object that will be used in the API response.

## Docker / Kubernetes

Of course i did a simple Docker file in order to lend people play with images an so on. I added some kubernetes file, and using the file **run.sh** in the folder **kubernetes**, the developer can test the application inside a cluster.

## Testing

It is possible to test doing a GET to the aggregation endpoint as:

```
curl --location --request GET '<kubernetes-minikube-ip>:<my-service-port>/api/v1/aggregate/<term-to-search>'
```

## Feign Client

My first tentative of making this project, was using **FeignClient**. Thats why, if you search between the classes, you will see a lot of classes **@Deprecated**. Because after some time i prefer, rather than Feign, using WebFlux, because of the **aggregaton pattern** facility. 
I realized that with **WebFlux** i didnt need to control as much things that i need to control with Feign. But there is a different between them because, that **Webflux** its **reactive programming** and **Feign** is not. In such case, i didnt evaluate much time about those differences, but  i realized that with **Webflux**, it was really simplier. 
