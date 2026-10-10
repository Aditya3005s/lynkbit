# LynkBit — URL Shortener

LynkBit is a URL-shortening web application built with a Java Spring Boot backend, PostgreSQL persistence, and a responsive HTML/CSS/JavaScript frontend.

## Live Demo

- **Frontend:** https://lynkbit.onrender.com/
- **Backend API:** https://lynkbit-api.onrender.com

> The free backend host may spin down after inactivity, so the first request can take a little longer while the service wakes up.

## Features

- Create a short link from a valid HTTP or HTTPS URL.
- Generate a six-character short code.
- Store original URLs and short codes in PostgreSQL.
- Redirect short links to their original destinations with HTTP `302 Found`.
- Validate URL input and return useful errors for invalid URLs or unknown short codes.
- Enforce short-code uniqueness in the database.
- Copy the generated link from the frontend.
- Use environment variables for hosted database configuration.

## Tech Stack

- **Frontend:** HTML, CSS, JavaScript
- **Backend:** Java 17, Spring Boot, Spring Data JPA
- **Database:** PostgreSQL
- **Deployment:** Render (frontend and backend), Neon (hosted PostgreSQL)

## API

### Create a short URL

`POST /api/urls`

Request body:

```json
{
  "originalUrl": "https://www.google.com"
}
```

Example using `curl`:

```bash
curl -X POST "https://lynkbit-api.onrender.com/api/urls" \\
  -H "Content-Type: application/json" \\
  -d '{"originalUrl":"https://www.google.com"}'
```

A successful request returns `201 Created`. The response includes the original URL, generated short code, and complete short URL, for example:

```json
{
  "originalUrl": "https://www.google.com",
  "shortCode": "3nqdW2",
  "shortUrl": "https://lynkbit-api.onrender.com/3nqdW2"
}
```

The generated short code varies for each new URL.

### Redirect to the original URL

`GET /{shortCode}`

Open the generated short URL in a browser. If the code exists, the API responds with `302 Found` and redirects to the original URL. An unknown code returns `404 Not Found`.

## Run Locally

### Prerequisites

- Java 17
- Maven (or the included Maven Wrapper)
- PostgreSQL

### 1. Clone the repository

```bash
git clone https://github.com/Aditya3005s/lynkbit.git
cd lynkbit
```

### 2. Create a PostgreSQL database

Create a database named `urlshortener`, or update the database URL environment variable to match your database.

### 3. Configure environment variables

The application supports these variables:

| Variable | Default / Example |
|---|---|
| `DB_URL` | `jdbc:postgresql://localhost:5432/urlshortener` |
| `DB_USERNAME` | `postgres` |
| `DB_PASSWORD` | Your local PostgreSQL password |
| `APP_BASE_URL` | `http://localhost:8080` |

Do not commit real database passwords or other secrets to source control.

### 4. Start the backend

On Windows PowerShell:

```powershell
.\mvnw.cmd spring-boot:run
```

On macOS/Linux:

```bash
./mvnw spring-boot:run
```

The backend runs at `http://localhost:8080` by default.

### 5. Open the frontend

Open `frontend/index.html` in a browser or serve the `frontend` directory using a local development server. If you change the backend address, update the API URL in `frontend/script.js` and ensure the backend CORS configuration allows the frontend origin.

## Validation and Error Handling

The backend validates the submitted URL, checks for an HTTP/HTTPS scheme and host, enforces short-code uniqueness, and handles unknown codes. The frontend displays the generated link and supports copying it to the clipboard.

## Current Scope

This is the V1 core URL shortener: create, persist, and redirect. Click analytics, custom aliases, expiry settings, user accounts, and a dashboard are planned as possible later versions, not part of the current V1 feature set.

## Repository

https://github.com/Aditya3005s/lynkbit
