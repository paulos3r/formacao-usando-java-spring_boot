# formacao-usando-java-spring_boot
Alura: Formação Java web - Crie aplicação usando Spring boot, projeto se trata da formação java especialista!

### documentação

[API DOCS](http://localhost:8080/v3/api-docs)

[SWAGGER](http://localhost:8080/swagger-ui/index.html#/)

### deploy

java 
    -Dspring.profiles.active=prod 
    -DDATASOURCE_URL=urldobanco://ip/nomebanco
    -DDATASOURCE_USERNAME=usernamedobanco
    -DDATASOURCE_PASSWORD=senhadobanco
-jar target/api-0.0.1-SNAPSHOT.jar

EXEMPLO
java -Dspring.profiles.active=prod -DDATASOURCE_URL=jdbc:mysql://localhost/bancodedados -DDATASOURCE_USERNAME=usernamedobanco -DDATASOURCE_PASSWORD=senhadobanco -jar target/api-0.0.1-SNAPSHOT.jar

