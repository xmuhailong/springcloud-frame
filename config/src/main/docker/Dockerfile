# 基于哪个镜像，其中frolvlad/alpine-oraclejdk8:slim也是一个image
FROM frolvlad/alpine-oraclejdk8:slim
# 将本地文件夹挂载到当前容器
VOLUME /tmp
# 拷贝文件到容器，config-0.0.1-SNAPSHOT.jar这里是maven打包后的名字
ADD config-0.0.1-SNAPSHOT.jar app.jar
#RUN bash -c 'touch /app.jar'
# 配置容器启动后执行的命令  默认
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
# 暴露的端口
EXPOSE 2583
