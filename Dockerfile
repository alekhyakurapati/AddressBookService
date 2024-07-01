FROM openjdk:17
EXPOSE 8080
ADD target/svc-branch-manager-address-book-0.0.1-SNAPSHOT.jar svc-branch-manager-address-book.jar
ENTRYPOINT ["java", "-jar", "/svc-branch-manager-address-book.jar"]