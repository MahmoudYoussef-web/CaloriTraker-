# 🍎 Calorie Calculation & Nutrition Management System

A backend system for tracking meals, managing nutrition, and analyzing food images using AI.

Developed as a **Graduation Project** for the Computer Science Program.

---

## 📌 Project Overview

The system helps users:

* Track meals and daily calories
* Analyze food images using AI
* Monitor BMI and body records
* View nutrition progress
* Manage personal health goals

Built using **Spring Boot** and RESTFull architecture.

---

## ✨ Key Features

* Secure Authentication (JWT) 
* User Profile Management
* Meal & Food Tracking
* Calorie & Macro Calculation
* BMI Calculator
* AI Food Recognition
* Nutrition Dashboard
* Progress Reports
* Swagger API Documentation

---

## 🛠️ Tech Stack

### Backend

* Java 21
* Spring Boot
* Spring Data JPA
* Hibernate
* Spring Security (JWT)

### Database

* MySQL

### Tools

* Swagger (OpenAPI)
* Maven
* Git
* IntelliJ IDEA

---

## 🏗️ System Architecture

The system follows **Layered Architecture**:

```
Controller → Service → Repository → Database
                     ↓
                   AI Module
```

### Layers

* Controller: REST APIs
* Service: Business Logic
* Repository: Data Access
* Security: Authentication
* AI Module: Image Analysis

---

## 🤖 AI Processing Flow

1. User uploads food image
2. Backend saves image
3. Image sent to AI model
4. AI returns nutrition data
5. Data stored in database
6. Meal item created automatically

---

## 🗄️ Database Design

### Main Tables

| Table        | Description         |
| ------------ | ------------------- |
| Users        | Authentication data |
| UserProfiles | Health data         |
| Meals        | Daily meals         |
| MealItems    | Meal foods          |
| Foods        | Nutrition info      |
| Images       | AI images           |
| BodyRecords  | Body history        |

### Relations

* User → Profile (1:1)
* User → Meals (1:N)
* Meal → Items (1:N)
* Item → Food (N:1)
* Item → Image (1:1)
* User → BodyRecords (1:N)

---

## 👨‍💻 My Contribution

**Role: Backend Developer**

Responsibilities:

* Designed REST APIs
* Implemented AI integration
* Built image gallery system
* Developed meal tracking logic
* Implemented DTO mapping
* Optimized database relations
* Integrated Swagger documentation

---

## ⚙️ Installation

### Requirements

* Java 17+
* MySQL 8+
* Maven

### Setup

```bash
git clone https://github.com/MahmoudYoussef-web/calorie-tracker-backend

cd calorie-tracker-backend

# Configure database in application.properties

mvn clean install
mvn spring-boot:run
```

---

## 📖 API Documentation

After running:

```
http://localhost:8080/swagger-ui.html
```

---

## 📷 Demo

Screenshots and demo video available in `/docs`.

---

## 📜 License

Academic Project – Educational Use Only.
