FROM mysql:8.0.33
# docker build -f Dockerfile -t <image_tag> . && docker run -p 3306:3306 <image_tag> --default-authentication-plugin=mysql_native_password
ENV MYSQL_DATABASE game
ENV MYSQL_ALLOW_EMPTY_PASSWORD true

EXPOSE 3306

COPY game.sql /docker-entrypoint-initdb.d/game.sql