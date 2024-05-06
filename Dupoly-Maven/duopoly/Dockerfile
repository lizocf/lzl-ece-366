FROM node:20

# set working directory
WORKDIR /app

ENV PATH /app/node_modules/.bin:$PATH

# install app dependencies
COPY package.json ./
COPY package-lock.json ./
RUN npm install -g serve
RUN npm install -g npm@10.5.0 --silent
RUN npm install react-scripts@3.4.1 -g --silent
RUN npm install react-router-dom --silent
RUN npm install --save react-jsx
RUN npm install react-icons --save



# add app
COPY . ./

# start app
CMD sh -c 'npm start & node server.js'
# CMD npm start