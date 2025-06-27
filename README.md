
# Ntravliw ✈️

A simple travel agency web application developed for a hackathon, using **Spring Boot (Java)** for the backend and **vanilla HTML/CSS/JS** for the frontend. Data is stored in a **MySQL** database.

---

## 🚀 Features

- Admin authentication (front and back)
- Trip reservation system
- Online payment integration via **Guiddini**
- User-friendly dashboard
- RESTful APIs (documented)
- Responsive frontend

---

## 🛠️ Technologies Used

- Java 17 + Spring Boot
- MySQL
- HTML, CSS, JavaScript
- JPA / Hibernate

---

## ⚙️ Prerequisites

Before running the project, make sure you have:

- [Java JDK 17+](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Maven](https://maven.apache.org/download.cgi)
- [MySQL](https://dev.mysql.com/downloads/)
- [Git](https://git-scm.com/)

---

## 🧑‍💻 How to Install and Run the Project

### 1. Clone the repository

```bash
git clone https://github.com/Chaima-ing/Ntravliw.git
cd Ntravliw
```

### 2. Configure the database

- Create a MySQL database named `ntravliw` (or your preferred name).
- Update your credentials in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ntravliw
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 3. Build and run the Spring Boot app

```bash
./mvnw spring-boot:run
```

Or if you're using Maven directly:

```bash
mvn spring-boot:run
```

### 4. Access the application

- Open your browser and go to: [http://localhost:8080](http://localhost:8080)

---

## 📂 Project Structure

```
src/
├── main/
│   ├── java/          # Spring Boot Java code (controllers, services, entities)
│   ├── resources/
│   │   ├── static/     # HTML/CSS/JS frontend files
│   │   ├── templates/  # Thymeleaf templates (if used)
│   │   └── application.properties
```

---

## 💳 Payment Integration (Guiddini)

This project integrates [**Guiddini**](https://www.guiddini.com/) for secure online payment.

- When a user confirms a reservation, the backend redirects to the Guiddini payment gateway.
- The payment flow uses **API keys (secret + app key)** securely stored in environment variables or `application.properties`.
- After successful payment, the user is redirected back with a confirmation.

> 🛠️ Make sure to configure your `application.properties` with your own Guiddini credentials:

```properties
guiddini.app-key=your_app_key
guiddini.secret-key=your_secret_key
```

---

## 🤝 Contributing

Pull requests are welcome! For major changes, please open an issue first.

---

## 📄 License

This project is open-source and free to use. You can add your own license section here if needed.

---
