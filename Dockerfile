# Build image = docker build -t springboot_crud:1.0 .

# Port 8000 is exposed in 'resources/application.yaml'
# Run image = docker run -p 8000:8000 springboot_crud:1.0
FROM openjdk:21

# This sets the working directory
WORKDIR /usr/app

COPY ./target/Inventory-1.0.jar ./Inventory-1.0.jar

# Expose the  port 6009 as an internal port
# Port for main endpoint and swagger
EXPOSE 6009

# Expose the port 9000 as a internal port
# For Actuator
EXPOSE 9000

# The following command 'npm start' will be executed when the container is started.
# if we simply did 'npm start' without the 'CMD', then 'npm start' would have executed when the image was created not when the container started.
#CMD ["npm","start"]
ENTRYPOINT ["java","-jar","./Inventory-1.0.jar"]
