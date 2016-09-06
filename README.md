# axonframework-showcase #

Part of a bigger system supporting management of AGH BIT Scientific Group - events organization and management subsystem. 

The conjuction of Domain-Driven Desig approach along with Microservices Architecture Pattern let us build fine-grained systems with clear, not leaking business domain. CQRS/ES techniques ensures extensibility and scalability. 

### Bounded context overview ###

| Bounded Context | Description |
|-----------|-------|
|events-bc-app|Core Domain. This is the part of Events BC containing most of business logic. It handles whole Event lifecycle - creation, surveying, voting, enrollment. We have decided to use two interesting architectural patterns - Command Query Responsibility Segregation and Event Sourcing. Place, where AxonFramework shines|
|announcement-bc|Module responsible for publishing announcements on various social medias - including Twitter, Facebook and Google Groups|
|notification-bc|Provides real time notifications about various events. Under the hood it uses Web Sockets|
|rs-integration-bc|Small module, written in Scala/Akka, responsible for integration with external Room Booking System|

### Inter-service communication ###

Microservices communicate through REST interfaces. Additionally, we use Apache Kafka - distributed publish-subscribe messaging system as a form of a global commit log. Each subsystem produces multiple Domain Events. These events are published to Kafka and finally consumed by other BCs. ```knbit-events-bc``` use RabbitMQ as well, as a form of internal communication. Diagram belows depicts integration patterns

![KNBIT System integration patterns](docs/inter-service-communication-patterns.png)

### Further work ###
For the time being, further work is being frozen. If anyone is interested in moving this project forward, we're more than happy to help you. Just drop an email to me, or @slnowak.

