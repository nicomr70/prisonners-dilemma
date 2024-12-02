# Use a Node.js image to build the app
FROM node:20-alpine AS build
WORKDIR /app
COPY package.json package-lock.json ./
RUN npm install
COPY . .
RUN npm run build

# Use a lightweight Node.js image to serve the app
FROM node:20-alpine
WORKDIR /app
COPY --from=build /app/.next ./.next
COPY --from=build /app/public ./public
COPY package.json package-lock.json ./
RUN npm install --production
EXPOSE 3000
CMD ["npm", "run", "start"]
