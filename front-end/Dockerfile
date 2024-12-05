# Stage 1: Build the Next.js application
FROM node:18-buster AS builder
WORKDIR /app

# Install dependencies
COPY package.json package-lock.json ./
RUN npm install

# Copy application files
COPY . .

# Build the Next.js application
RUN npm run build

# Stage 2: Run the Next.js application
FROM node:18-buster
WORKDIR /app

# Copy built files from the builder stage
COPY --from=builder /app /app

# Expose the port Next.js will run on
EXPOSE 3000

# Start the Next.js server
CMD ["npm", "start"]
