# 📦 Tracking Number Generator API

A scalable and efficient REST API that generates unique tracking numbers for parcels. Built with Spring Boot 3 and Java 17. Designed for performance, high concurrency, and horizontal scalability.

---

## 🧠 Business Rules

- Tracking number is generated using:
    - origin_country_id
    - destination_country_id
    - created_at
    - customer_id
    - customer_slug
- `weight` is not part of the uniqueness logic
- Requests with same identifying input return 409 Conflict

---

## ⚙️ Prerequisites

- Java 17
- Maven 3.8+
- Internet connection (to download dependencies)

---

## 🚀 How to Run Locally

```bash
./mvnw spring-boot:run
```

### Access H2 Console (optional)

- URL: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:trackingdb`
- Username: `sa`
- Password: *(leave blank)*

---

## ☁️ Deployment

Supports deployment to platforms like:

- ✅ [Render](https://render.com)
- ✅ [Railway](https://railway.app)
- ✅ [Fly.io](https://fly.io)

Make sure your `application.yaml` includes:

```yaml
server:
  port: ${PORT:8080}
```

---

## 🧪 API Endpoints

### `GET /next-tracking-number`

Generates a unique tracking number.

#### Query Parameters

| Name                   | Type     | Example                                       |
|------------------------|----------|-----------------------------------------------|
| origin_country_id      | String   | `MY`                                          |
| destination_country_id | String   | `ID`                                          |
| weight                 | Double   | `1.234`                                       |
| created_at             | RFC 3339 | `2024-05-22T10:00:00+08:00`                   |
| customer_id            | UUID     | `de619854-b59b-425e-9db4-943979e1bd49`        |
| customer_name          | String   | `RedBox Logistics`                            |
| customer_slug          | String   | `redbox-logistics`                            |

#### Success Response

```json
{
  "success": true,
  "data": {
    "tracking_number": "MYIDREDBOXABC1234",
    "created_at": "2025-05-22T10:15:30+08:00"
  },
  "error": null
}
```

#### Error Response (duplicate)

```json
{
  "success": false,
  "data": null,
  "error": {
    "status": 409,
    "error": "Conflict",
    "message": "Tracking number already exists: MYIDREDBOXABC1234",
    "path": "/next-tracking-number",
    "timestamp": "2025-05-22T10:15:45+08:00"
  }
}
```

---

### `GET /tracking-numbers`

Returns a list of all generated tracking numbers.

#### Success Response

```json
{
  "success": true,
  "data": [
    {
      "tracking_number": "MYIDREDBOXABC1234",
      "created_at": "2025-05-22T10:15:30+08:00"
    },
    {
      "tracking_number": "MYIDLOGIXXYZ9876",
      "created_at": "2025-05-22T10:20:15+08:00"
    }
  ],
  "error": null
}
```

---

## 📥 Curl Examples

### Generate a Tracking Number

```bash
curl -G http://localhost:8080/next-tracking-number \
  --data-urlencode "origin_country_id=MY" \
  --data-urlencode "destination_country_id=ID" \
  --data-urlencode "weight=1.234" \
  --data-urlencode "created_at=2024-05-22T10:00:00+08:00" \
  --data-urlencode "customer_id=de619854-b59b-425e-9db4-943979e1bd49" \
  --data-urlencode "customer_name=RedBox Logistics" \
  --data-urlencode "customer_slug=redbox-logistics"
```

### Get All Tracking Numbers

```bash
curl http://localhost:8080/tracking-numbers
```

---

## 👨‍💻 Author

📧 Email: hendralw98@email.com  
🔗 GitHub: https://github.com/hendralw

---