# build the web application
FROM node:latest as builder
WORKDIR /usr/src/app
COPY *.json ./
RUN npm install
COPY ./src ./src
RUN npm run-script build --prod

# serve webapp under Apache
FROM httpd:2.4
WORKDIR /usr/local/apache2/
COPY ./.htaccess ./htdocs/
COPY ./httpd.config ./conf/httpd.conf
COPY --from=builder /usr/src/app/dist/website/ ./htdocs/
