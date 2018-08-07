export LEGACY_URL=http://localhost:8080/legacy

mvn -f ./pom.xml spring-boot:run -Dspring.profiles.active=dev