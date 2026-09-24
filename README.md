# 🏋️ FitTogether – Personal Wellness Tracker

> A Java-based wellness and fitness management application designed to help users track their **workouts, sleep, and diet** in one centralized platform.

## 📌 Overview

**FitTogether** is a desktop-based wellness tracking application developed using **Java**. The application provides users with a simple platform to record and manage important aspects of their daily lifestyle, including **workout activities, sleep patterns, and dietary information**.

The project demonstrates the practical implementation of **Java GUI development, object-oriented programming, database management, and application-level data handling**.

---

## ✨ Features

### 🏃 Workout Tracker

* Record daily workout activities.
* Maintain workout-related information.
* Track exercise progress over time.
* Organize fitness activities in one place.

### 😴 Sleep Tracker

* Record daily sleep information.
* Maintain a history of sleep-related data.
* Help users monitor their sleeping patterns.

### 🥗 Diet Tracker

* Record daily food and dietary information.
* Maintain dietary history.
* Help users keep track of their overall nutrition habits.

### 🖥️ User-Friendly Interface

* Desktop-based graphical user interface.
* Simple navigation between different wellness modules.
* Designed for easy data entry and management.

---

## 🛠️ Technology Stack

| Technology           | Purpose                             |
| -------------------- | ----------------------------------- |
| **Java**             | Core application development        |
| **Java Swing / GUI** | Desktop user interface              |
| **MySQL**            | Database management                 |
| **JDBC**             | Java–MySQL database connectivity    |
| **Apache NetBeans**  | Development environment             |
| **Git & GitHub**     | Version control and project hosting |

---

## 🏗️ Application Architecture

```text
                    ┌───────────────────────┐
                    │      FitTogether      │
                    │   Wellness Tracker    │
                    └───────────┬───────────┘
                                │
              ┌─────────────────┼─────────────────┐
              │                 │                 │
              ▼                 ▼                 ▼
       ┌─────────────┐   ┌─────────────┐   ┌─────────────┐
       │   Workout   │   │    Sleep    │   │     Diet    │
       │   Tracker   │   │   Tracker   │   │   Tracker   │
       └──────┬──────┘   └──────┬──────┘   └──────┬──────┘
              │                 │                 │
              └─────────────────┼─────────────────┘
                                │
                                ▼
                     ┌────────────────────┐
                     │   Java Application │
                     │    / GUI Layer     │
                     └─────────┬──────────┘
                               │
                               ▼
                     ┌────────────────────┐
                     │       JDBC         │
                     │ Database Connector │
                     └─────────┬──────────┘
                               │
                               ▼
                     ┌────────────────────┐
                     │       MySQL        │
                     │      Database      │
                     └────────────────────┘
```

---

The repository follows the standard structure generated for a Java project developed with Apache NetBeans.

---

## ⚙️ Prerequisites

Before running the project, make sure you have:

* **Java JDK 8 or later**
* **Apache NetBeans**
* **MySQL Server**
* **MySQL Workbench**
* **MySQL Connector/J**

---

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/AadiAgarwal1/FitTogether.git
```

Navigate into the project:

```bash
cd FitTogether
```

### 2. Open in NetBeans

1. Open **Apache NetBeans**.
2. Select **File → Open Project**.
3. Select the `FitTogether` folder.
4. Allow NetBeans to load the project.

### 3. Configure MySQL

Make sure your MySQL server is running.

Create the required database:

```sql
CREATE DATABASE FitTogether;
```

Configure the database connection in the Java source code according to your local MySQL credentials.

Example:

```java
String url = "jdbc:mysql://localhost:3306/FitTogether";
String username = "root";
String password = "YOUR_PASSWORD";
```

> ⚠️ Do not upload your actual database password to GitHub.

### 4. Add MySQL Connector

Make sure **MySQL Connector/J** is available in the project's libraries/classpath.

### 5. Run the Application

From NetBeans:

```text
Right Click Project
        ↓
Run
```

or run the project's main Java class.

---

## 🧩 Core Concepts Demonstrated

This project demonstrates several important software development concepts:

* Object-Oriented Programming
* Java GUI Development
* Event-Driven Programming
* JDBC Database Connectivity
* MySQL Database Management
* CRUD Operations
* Exception Handling
* Modular Application Design
* User Input Validation
* Desktop Application Development

---

## 💾 Database Integration

FitTogether uses **MySQL** for persistent storage.

The Java application communicates with MySQL using **JDBC**, allowing the application to:

```text
Java Application
       │
       ▼
      JDBC
       │
       ▼
    MySQL DB
       │
       ├── Workout Data
       ├── Sleep Data
       └── Diet Data
```

This allows wellness records to be stored and retrieved instead of being limited to temporary application data.

---

## 🎯 Project Objectives

The main objectives of FitTogether are:

* Provide a centralized wellness tracking system.
* Make daily fitness and lifestyle information easier to manage.
* Demonstrate Java desktop application development.
* Implement database connectivity using JDBC.
* Apply Object-Oriented Programming concepts to a practical application.
* Build a foundation for future health and fitness management features.

---

## 🔮 Future Enhancements

Potential improvements for future versions include:

* 📊 Fitness analytics dashboard
* 📈 Progress charts and visualizations
* 🔐 User authentication and registration
* 🎯 Personalized fitness goals
* 🔔 Workout and sleep reminders
* 🧮 BMI and calorie calculations
* 📱 Mobile application version
* ☁️ Cloud-based data synchronization
* 🤖 AI-powered fitness recommendations
* 📅 Weekly and monthly wellness reports

---

## 🧠 Learning Outcomes

Through this project, the following skills were developed:

```text
Java
  │
  ├── Object-Oriented Programming
  ├── GUI Development
  ├── Event Handling
  └── Exception Handling
        │
        ▼
      JDBC
        │
        ▼
     MySQL
        │
        ▼
 Database-Driven Application
```

The project provided practical experience in connecting a Java desktop application with a relational database and building a functional wellness management system.

---

## 👨‍💻 Author

**Aadi Agarwal**

B.Tech Computer Science & Engineering
SRM Institute of Science and Technology

### 🔗 GitHub

[Aadi Agarwal – GitHub](https://github.com/AadiAgarwal1?utm_source=chatgpt.com)

### 📂 Project Repository

[FitTogether Repository](https://github.com/AadiAgarwal1/FitTogether?utm_source=chatgpt.com)

---

## 📄 License

This project is developed for **educational and academic purposes**.

---

⭐ If you find this project useful, consider giving the repository a star!
