# JDK 17 이미지 사용
FROM openjdk:17-ea-11-jdk-slim

VOLUME /tmp

# 컨테이너에 jar 파일 이름을 변경하여 저장
COPY build/libs/delivery-moa-0.0.1-SNAPSHOT.jar delivery-moa.jar

# 빌드된 이미지가 Run될 때 실행할 명령어
ENTRYPOINT ["java","-jar","delivery-moa.jar"]