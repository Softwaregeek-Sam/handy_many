# 🚀 HandyMany -  A Labor Hiring Platform

A real-time labor hiring platform designed to bridge the gap between clients and skilled workers — such as electricians, plumbers, carpenters, and more — in their immediate locality.

This platform works on a hybrid job dispatch model inspired by ride-hailing and food delivery apps like Ola, Uber, and Zomato, ensuring fast and reliable labor availability even in urgent scenarios.

Whether it's an emergency plumbing need, furniture installation, or electrical work, clients can quickly request help, and nearby available professionals get notified in real time. The entire flow — from OTP-based login to worker matching and notifications — is fully automated and production-grade.



---

## 📦 Tech Stack

| Layer       | Technology Used         |
|------------|--------------------------|
| **Backend**| Java, Spring Boot        |
| **Database**| PostgreSQL               |
| **Auth**   | JWT, OTP (2Factor API)   |
| **Notifications** | Firebase Cloud Messaging (FCM) |
| **Dev Tools**| Postman, Docker, GitHub |
| **Deployment**| AWS EC2, PostgreSQL (RDS/local) Upcoming |

---

## 🧩 Modules

- ✅ User Management
- ✅ Worker Profile Management
- ✅ Real-time Job Matching
- ✅ OTP Login via 2Factor (Missed Call)
- ✅ Notification System with FCM
- 🚧 Job History, Ratings & Reviews (Coming soon)
- 🚧 Admin Dashboard (Coming soon)

---

## 🔄 Job Flow (How It Works)

1. ✅ **User/Client logs in using OTP**
2. ✅ **Creates a Job Request** (category, location, urgency)
3. ✅ **Job Matching Loop** runs (expands search radius if needed)
4. ✅ **Notifies eligible Workers** via FCM
5. ✅ **Worker accepts the job**
6. ✅ **Job marked as active**, both parties are notified

---



