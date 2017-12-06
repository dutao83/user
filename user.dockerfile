FROM registry.docker-cn.com/library/java:alpine
MAINTAINER david.du
ARG SERVICE_VERSION
ARG PROFILES_ACTIVE
COPY ${SERVICE_VERSION} /var/lib/shanshui/user/
ENTRYPOINT java -jar /var/lib/shanshui/user/${SERVICE_VERSION} --spring.profiles.active=${PROFILES_ACTIVE}