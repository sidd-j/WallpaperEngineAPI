# 🚀 Spring Boot API with CI/CD & Automated Testing

A production-style **Spring Boot REST API** with **JWT authentication**, **API automation (RestAssured)**, and **CI/CD pipeline using GitHub Actions**.

This project demonstrates real-world backend development, testing practices, and continuous integration — aligned with **SDET / QA Engineer roles**.

---

# 📌 Features

* 🔐 User Authentication (Register & Login with JWT)
* 🧪 API Automation Testing (RestAssured + JUnit 5)
* ⚙️ CI/CD Pipeline (GitHub Actions)
* 🗄️ In-memory DB for testing (H2)
* 🚫 Exception Handling with proper HTTP status codes
* 🌐 External API Integration (NASA APOD)

---

# 🛠️ Tech Stack

| Category   | Technology                         |
| ---------- | ---------------------------------- |
| Backend    | Spring Boot (Java 21)              |
| Database   | H2 (Test), PostgreSQL (Prod-ready) |
| Security   | Spring Security + JWT              |
| Testing    | RestAssured + JUnit 5              |
| CI/CD      | GitHub Actions                     |
| Build Tool | Maven                              |

---

# 📡 API Endpoints

## 🔹 Authentication

### ➤ Register User

```http
POST /registerUser
```

**Request Body**

```json
{
  "email": "user@test.com",
  "password": "Password123",
  "name": "Test User"
}
```

**Response**

* `201 Created` → User registered
* `409 Conflict` → Email already exists
* `400 Bad Request` → Invalid input

---

### ➤ Login User

```http
POST /loginUser
```

**Request Body**

```json
{
  "email": "user@test.com",
  "password": "Password123"
}
```

**Response**

* `200 OK` → Returns JWT token
* `401 Unauthorized` → Invalid password
* `404 Not Found` → User not found
* `400 Bad Request` → Invalid input

---

## 🔹 User APIs

### ➤ Get All Users

```http
GET /getUsers
```

### ➤ Get User by Email

```http
GET /getByEmail?email=user@test.com
```

---

## 🔹 External API

### ➤ NASA APOD

```http
GET /api/apod
```

---

# 🧪 Test Coverage

Automated API tests include:

### ✅ Positive Cases

* Successful signup
* Successful login

### ❌ Negative Cases

* Duplicate email signup (409)
* Invalid password (401)
* User not found (404)
* Empty input validation (400)

### ⚠️ Edge Cases

* Dynamic test data (prevents flaky tests)
* Proper exception handling validation

---

# 🔄 CI/CD Pipeline

GitHub Actions automatically:

* ✅ Builds project
* ✅ Runs all test cases
* ✅ Fails on test errors
* ✅ Uploads test reports

### Workflow Trigger:

* On push to `main`
* On pull request

---

# 📊 Sample CI Output

```bash
Tests run: 9, Failures: 0
BUILD SUCCESS
```

---

# 🧠 Key Learnings

* Debugging CI failures using logs
* Aligning API contract with test cases
* Handling dynamic test data for stable automation
* Implementing proper HTTP status codes
* Structuring testable backend services

---

# 🚀 How to Run Locally

### 1. Clone repo

```bash
git clone https://github.com/your-username/Spring-Boot-API-CICD-.git
cd Spring-Boot-API-CICD-
```

### 2. Run application

```bash
mvn spring-boot:run
```

### 3. Run tests

```bash
mvn test
```

---

# 📁 Project Structure

```
src/
 ├── main/
 │   ├── controller/
 │   ├── service/
 │   ├── repository/
 │   ├── dto/
 │   ├── entities/
 │   └── exception/
 └── test/
     ├── LoginTest.java
     ├── SignupTest.java
     └── ApplicationTests.java
```

---

# 🎯 Future Enhancements

* 🔒 JWT-based protected endpoints
* 🐳 Docker containerization
* 🧪 TestContainers (real DB in CI)
* 📊 Allure test reports
* ⚡ Parallel test execution

---

# 👨‍💻 Author

**Siddhesh Jadhav**
QA Engineer | Automation Testing | API Testing

* GitHub: https://github.com/sidd-j
* LinkedIn: https://www.linkedin.com/in/siddheshjadhavdeepak/

---

# ⭐ Final Note

This project showcases **end-to-end QA + backend integration skills**, combining:

* Development
* Testing
* CI/CD
* Debugging

👉 Built to demonstrate **industry-level QA/SDET capabilities**.
