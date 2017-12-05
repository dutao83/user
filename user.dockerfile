FROM registry.docker-cn.com/library/java:alpine
MAINTAINER david.du
COPY ${SERVICE_VERSION} /var/lib/shanshui/user/
RUN java -jar /var/lib/shanshui/user/${SERVICE_VERSION} --spring.profiles.active=test