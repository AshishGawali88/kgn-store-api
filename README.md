# KGN Store API

A single, small Spring Boot 3 (Java 17) REST API for **KGN Power Tools & Materials**.
It serves the product catalogue and receives customer enquiries and orders.

This is intentionally **one service**, not a microservices cluster — it's the right
size for one shop, it's easy to run, and it hosts for **free**.

## Endpoints

| Method | Path | Access | Purpose |
|---|---|---|---|
| GET  | `/api/products`            | public | List products (`?category=cleaners` to filter) |
| GET  | `/api/products/{id}`       | public | One product |
| POST | ``           | public | Submit a contact enquiry |
| POST | `/api/orders`              | public | Place an order (price computed server-side) |
| GET  | `/api/admin/enquiries`     | admin  | List enquiries (needs `X-Admin-Key`) |
| GET  | `/api/admin/orders`        | admin  | List orders (needs `X-Admin-Key`) |
| GET  | `/actuator/health`         | public | Health check |

## Run locally

Requires JDK 17+ (and Maven, or use the bundled `mvnw` if you add it).

```bash
mvn spring-boot:run
# API on http://localhost:8080
# Try:  curl http://localhost:8080/api/products
```

With no configuration it uses an in-memory H2 database seeded with the 14 products,
so it just works. Data resets on restart — that's fine for local testing.

## Environment variables (set these on your host, never in code)

| Variable | Required in prod? | Meaning |
|---|---|---|
| `ALLOWED_ORIGINS` | **Yes** | Comma-separated sites allowed to call the API, e.g. `https://kgnpowertools.netlify.app`. Never `*`. |
| `ADMIN_API_KEY` | For admin pages | A long random string. If unset, all `/api/admin/**` calls are denied. |
| `SPRING_DATASOURCE_URL` | For persistence | JDBC URL of your Postgres, e.g. `jdbc:postgresql://HOST:5432/DB` |
| `SPRING_DATASOURCE_USERNAME` | with Postgres | DB user |
| `SPRING_DATASOURCE_PASSWORD` | with Postgres | DB password |
| `RATE_LIMIT_PER_MIN` | optional | Max POSTs per IP per minute (default 20) |
| `PORT` | set by host | Port to listen on (Render/Railway set this automatically) |

## Security choices (built in)

- **No secrets in code.** The admin key, DB password, and allowed origins all
  come from environment variables.
- **Fail-secure admin.** If `ADMIN_API_KEY` is not set, admin endpoints are
  blocked entirely rather than left open.
- **CORS is scoped**, never `*`, and credentials are off.
- **All input is validated and size-capped**; external text is stored as data
  only and never executed.
- **Prices are computed on the server** from the catalogue, so a tampered order
  request cannot change the price.
- **No stack traces or internal messages** are returned to clients.
- **Runs as a non-root user** in Docker; security headers added to every response.
- A simple per-IP **rate limit** protects the public forms from spam.

## Generate an admin key

```bash
# any long random string works, e.g.
openssl rand -hex 24
```
Put the result in `ADMIN_API_KEY`, then call admin endpoints with that value:
```bash
curl -H "X-Admin-Key: <your-key>" https://your-api-url/api/admin/orders
```
