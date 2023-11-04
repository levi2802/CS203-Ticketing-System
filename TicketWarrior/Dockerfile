# Basic nginx dockerfile starting with Ubuntu 20.04
FROM ubuntu:20.04
RUN apt-get -y update
RUN apt-get -y install nginx

FROM node:18-alpine
WORKDIR /app
COPY . .
RUN yarn install --production
CMD ["node", "src/index.js"]
EXPOSE 3000