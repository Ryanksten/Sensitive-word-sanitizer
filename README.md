# Sensitive Word Sanitizer

## Overview
This is a microservice application for sanitizing text by replacing sensitive words with asterisks. The application provides a backend API and a frontend interface for managing and testing sensitive word sanitization.

## Technologies Used
- Backend: Java Spring Boot
- Frontend: React
- Database: H2 (In-memory for simplicity)

## Prerequisites
- Java 17+
- Node.js 14+
- npm or yarn

## Backend Setup
1. Clone the repository
2. Navigate to the backend directory
3. Build the project:
   ```
   ./gradlew build
   ```
4. Run the application:
   ```
   ./gradlew bootRun
   ```
   The backend will start on `http://localhost:8080`

## Frontend Setup
1. Navigate to the frontend directory
2. Install dependencies:
   ```
   npm install
   ```
3. Start the development server:
   ```
   npm start
   ```
   The frontend will start on `http://localhost:3000`

## API Endpoints
- `POST /api/sensitive-words/sanitize`: Sanitize input text
- `GET /api/sensitive-words`: Retrieve all sensitive words
- `POST /api/sensitive-words`: Add a new sensitive word
- `PUT /api/sensitive-words/{id}`: Update an existing sensitive word
- `DELETE /api/sensitive-words/{id}`: Delete a sensitive wor
