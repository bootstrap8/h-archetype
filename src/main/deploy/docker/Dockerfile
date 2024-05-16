# FROM cicdcsy.harbor.cmss.com:18080/elasticip/oraclejdk:8u202
FROM openjdk:8

MAINTAINER dsp-esp-waf

ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

WORKDIR /opt/app/${artifactId}

ENV LANG=C.UTF-8

COPY / .

ENTRYPOINT ["/bin/bash", "/opt/app/${artifactId}/deploy/docker/bootstrap.sh"]
