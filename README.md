# Flut Trade Calculator

## Requirements
- Java 17
- Maven
- Spring Boot

## Build Instructions
1. Clone the repository.
2. Navigate to the project directory.
3. Run `mvn clean install` to build the project.

## Run Instructions
1. Start the application: `mvn spring-boot:run`.
2. Use the `/api/v1/trade/calculate` endpoint to upload a text file containing the input data.
2.1 Such a file could be found under `example_input/input.txt`.\
2.2 Use the following curl command to upload the file:
```shell
curl --location 'http://localhost:8080/api/v1/trade/calculate' --form 'file=@"{PATH_FROM_YOUR_SYSTEM}/example_input/input.txt"'

```
3. The API will return the results in String format.

