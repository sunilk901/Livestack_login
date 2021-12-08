FROM maven:ibmjava-alpine
WORKDIR /app
COPY . /app
RUN mvn install
CMD ["mvn", "spring-boot:run"]