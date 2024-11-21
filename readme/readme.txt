
****************************
1) Metadata
*****************************
a) URI
------------
http://localhost:6009/swagger-ui/index.html


********************************************
2) Running the application without Docker
********************************************
A) Method #1
---------------
mvn spring-boot:run

B) Method #2
-----------------------
#Generate jar file with
mvn install -DskipTests=true

#Run
java -jar ./target/Inventory-1.0.jar


C) Method #3
------------------
Run the 'InventoryApplication.java' from the IDE.

D) Method #4
------------------
./run.sh dev skip

where:-
dev = profile to use
skip = skip tests


************************************
3) Running the application with Docker
************************************
# Build output artifact
mvn clean install -DskipTests

# Build image:
docker build -t inventory:1.0 .

# Port 6009 is exposed in 'resources/application-dev.properties'
# Run image
docker run -p 8000:6009 inventory:1.0 -p 9000:9000 inventory:1.0

# Test:
http://localhost:8000/swagger-ui/index.html

************************************
4) Contract
************************************
Webservice provider responsibility
----------------------------------------
If you want a client to execute your webservice, then you have to create the Contract of this service
and pass on to the client.

For generating the contract, do the following:-

Browser > http://localhost:6009/swagger-ui.html#/
>
Click on the link : http://localhost:7000/v2/api-docs?group=Inventory Management Microservice
(This is an automatically generated link by swagger)
>
This will show a lot of JSON content
Copy all of this content and paste it at the link below (in the LEFT section)

Browser > https://editor.swagger.io/

>

File > Save as YAML (This is your contract> : e.g. 'contract.yaml'

Webservice Client responsibility
----------------------------------------
The webservice client will take this contract 'contract.yaml'
>
Browser > https://editor.swagger.io
>
Generate Client (Menu) > java

You will be able to download sample code to execute this webservice.


********************************
5) Actuator
********************************
### Actuator endpoints (Management port = 9000 in application.properties)
http://localhost:9000/actuator
http://localhost:9000/actuator/beans
http://localhost:9000/actuator/caches
http://localhost:9000/actuator/health
http://localhost:9000/actuator/info
http://localhost:9000/actuator/conditions
http://localhost:9000/actuator/env
http://localhost:9000/actuator/loggers
http://localhost:9000/actuator/heapdump
http://localhost:9000/actuator/threaddump
http://localhost:9000/actuator/metrics
http://localhost:9000/actuator/sbom
http://localhost:9000/actuator/scheduledtasks
http://localhost:9000/actuator/mappings
http://localhost:9000/actuator/auditevents
http://localhost:9000/actuator/beans
http://localhost:9000/actuator/configprops
http://localhost:9000/actuator/autoconfig
http://localhost:9000/actuator/trace
http://localhost:9000/actuator/httptrace
http://localhost:9000/actuator/jolokia
http://localhost:9000/actuator/liquibase
http://localhost:9000/actuator/intentiongraph
http://localhost:9000/actuator/refresh
http://localhost:9000/actuator/flyway
http://localhost:9000/actuator/prometheus
http://localhost:9000/actuator/sessions
http://localhost:9000/actuator/startup