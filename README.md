# Graduation Project – Backend

This repository contains the **backend (server-side)** implementation of a **TÜBİTAK-supported Graduation Project** aimed at increasing recycling awareness through **image-based waste classification**.

The backend is developed using **Spring Boot** and is responsible for **user authentication**, **data persistence**, **AI model integration**, and providing **RESTful APIs** for the frontend application.

---

## About the Project

**Graduation Project** is a web-based system where users upload images of waste materials.  
These images are analyzed by a **Flask-based AI model (YOLO)**, and the detected recyclable objects are stored and analyzed on a **user basis**.

### Backend Responsibilities

<ul>
  <li>User authentication and authorization (JWT)</li>
  <li>Communication with the AI model service (Flask API)</li>
  <li>Persisting recycling data in a PostgreSQL database</li>
  <li>Serving RESTful APIs to the frontend</li>
  <li>Providing a secure and scalable backend architecture</li>
</ul>

---

## Features

<ul>
  <li> JWT-based authentication (<code>/auth/**</code>)</li>
  <li> Role-based authorization with Spring Security</li>
  <li> Image upload and forwarding to Flask AI API</li>
  <li> Processing and storing AI prediction results</li>
  <li> User-based recycling history management</li>
  <li> PostgreSQL database integration</li>
  <li> RESTful API architecture</li>
  <li> Docker & Docker Compose support</li>
</ul>

---

## Technologies Used

<ul>
  <li> Java 17</li>
  <li> Spring Boot</li>
  <li> Spring Security + JWT</li>
  <li> Spring Web (REST API)</li>
  <li> Spring Data JPA (Hibernate)</li>
  <li> PostgreSQL</li>
  <li> Docker & Docker Compose</li>
  <li> Flask API (YOLO-based AI model)</li>
  <li> Gradle</li>
  <li> Nginx (Reverse Proxy – frontend side)</li>
</ul>

---

## Setup & Installation

### 1. Clone the Repository

```bash
git clone https://github.com/safaygt/graduation-project.git

cd graduation-project/backend/GraduationProject
```

### 2. Create `application.properties`

**For security reasons**, the `application.properties` file is **NOT committed** to this repository and is listed in `.gitignore`.

You must create this file manually at the following path:

```text
src/main/resources/application.properties
```

Example Configuration

Below is a sample application.properties file used for local development:
```properties
spring.application.name=GraduationProject


spring.datasource.url=jdbc:postgresql://localhost:5432/graduation
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.datasource.driver-class-name=org.postgresql.Driver



spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true



jwt.secret=CHANGE_THIS_SECRET_KEY
jwt.expiration=3600000


server.port=8080
server.address=0.0.0.0
```

### 3. Run Locally (Without Docker)

```bash
./gradlew bootRun
```

### 4. Run with Docker Compose (Recommended)

```bash
docker-compose up --build -d
```


This will start:
<ul>
<li>PostgreSQL</li>

<li>PgAdmin</li>

<li>Spring Boot Backend</li>

<li>React Frontend (Nginx)</li>

<li>Flask AI Model API</li>

</ul>


Related Repositories

<ul> 
  <li>Frontend (React)</li>
  <p><a href="https://github.com/safaygt/GraduationProjectFrontEnd.git">
      Frontend → GitHub
    </a></p>
  
  <li>AI Model API (FLASK + YOLO)</li>
  <p><a href="https://github.com/safaygt/GraduationProjectDeployModel.git">
      Graduation Project Model → GitHub
    </a></p> 
    
</ul>



Note: The backend service must be running before the frontend and model services can function properly.



If you would like to contribute:

Fork this repository

<ol>

<li>Create a new branch:</li>

```bash
git checkout -b feature/NewFeature
```

<li>Make your changes</li>

<li>Commit:</li>

```bash
git commit -m "Add new feature"
```

<li>Push:</li>

```bash
git push origin feature/NewFeature
```

<li>Open a Pull Request</li>


</ol>



<h1>Developer</h1>

Safa Yiğit
GitHub: [@safaygt](https://github.com/safaygt)  
