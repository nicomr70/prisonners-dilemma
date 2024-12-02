# Stage 1: Build the application
FROM node:20-alpine AS build
WORKDIR /app

# Copy package files and install dependencies
COPY package.json package-lock.json ./
RUN npm install

# Copy the entire project and build the Next.js app
COPY . .
RUN npm run build

# Stage 2: Serve the application with a lightweight Node.js image
FROM node:20-alpine
WORKDIR /app

# Copy the built application from the previous stage
COPY --from=build /app/.next ./.next
COPY --from=build /app/public ./public
COPY --from=build /app/package.json ./package.json
COPY --from=build /app/package-lock.json ./package-lock.json

# Install production dependencies only
RUN npm install --production

# Expose the port for the application
EXPOSE 3000

# Run the application on 0.0.0.0 to make it accessible externally
ENV HOST 0.0.0.0
CMD ["npm", "start"]
