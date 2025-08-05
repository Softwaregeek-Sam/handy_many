# 🚀 HandyMany -  A Labor Hiring Platform

A real-time labor hiring platform designed to bridge the gap between clients and skilled workers — such as electricians, plumbers, carpenters, and more — in their immediate locality.

This platform works on a hybrid job dispatch model inspired by ride-hailing and food delivery apps like Ola, Uber, and Zomato, ensuring fast and reliable labor availability even in urgent scenarios.

Whether it's an emergency plumbing need, furniture installation, or electrical work, clients can quickly request help, and nearby available professionals get notified in real time. The entire flow — from OTP-based login to worker matching and notifications — is fully automated and production-grade.


## 🏗️ Project Modules

- **Authentication Module**
  - OTP-based login/signup using 2Factor
  - Stateless JWT Authentication
  - Default role assignment and DB persistence

- **User Module**
  - Dual-role users (Client & Worker)
  - One-to-one mapping with Worker Profile
  - Role-based Access Control (RBAC)

- **Worker Module**
  - Profession, Experience, Hourly Rate
  - Skills, Location (lat/lng), Availability status
  - Workers can update availability and profile details

- **Job Management Module**
  - Real-time Job Creation after successful matching
  - Location + profession-based matching logic
  - Incremental search radius for better results

- **Notification System**
  - Notification using websocket Integration
  - Send notifications to nearby available workers
  - Token management via user profile

- **Access Control**
  - RBAC with multiple roles per user
  - Fine-grained authorization checks

---

OTP authentication with fallback to missed call (2Factor voice API)
- Stateless login with secure JWTs
- Real-time location-based worker matching
- PostgreSQL-based data persistence
- Modular Feature-based architecture



---

## 📦 Tech Stack

| Layer       | Technology Used         |
|------------|--------------------------|
| **Backend**| Java, Spring Boot        |
| **Database**| PostgreSQL               |
| **Auth**   | JWT, OTP (2Factor API)   |
| **Notifications** | WebSocket |
| **Dev Tools**| Postman, GitHub |

---


---

## 🔄 Job Flow (How It Works)

1. ✅ **User/Client logs in using OTP**
2. ✅ **Creates a Job Request** (category, location, urgency)
3. ✅ **Job Matching Loop** runs (expands search radius if needed)
4. ✅ **Notifies eligible Workers** 
5. ✅ **Worker accepts the job**
6. ✅ **Job marked as active**, both parties are notified

---
## 📌 Future Enhancements

- Admin module (for platform control)
- Premium workers and verification flow
- Job bidding and multi-worker offers
- Calendar scheduling
- Payment Integration

---

## 🔧 Setup Instructions

```bash
# Clone the repository
git clone https://github.com/Softwaregeek-Sam/handymany.git
cd handymany

# Configure application.properties
# Add DB URL, FCM key, 2Factor API key, etc.

# Run the app
./mvnw spring-boot:run



