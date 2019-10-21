FROM openjdk:8-alpine

ADD build/libs/exam-0.0.1.jar .

CMD java -jar exam-0.0.1.jar