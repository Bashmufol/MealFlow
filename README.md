# MealFlow 🍽️

A full-stack, role-based web application designed to modernize and streamline the corporate canteen ordering process. It allows company employees to pre-order their meals from the office canteen and allows canteen staff to manage the menu and orders. Employees can pre-order meals to save time, and canteen staff can efficiently manage menus and incoming orders through a dedicated admin dashboard.

**Live Demo:** [**canteen-connect.herokuapp.com**](https://[your-live-app-url]) 

---

## ✨ Key Features

### Employee Role
* **Secure Authentication:** Unique login for each employee.
* **Daily Menu:** View a responsive, up-to-date daily menu with prices and descriptions.
* **Simple Ordering:** Add items to a cart and place an order in seconds.
* **Order History:** Track the status of current and past orders (`Ordered`, `In Progress`, `Ready for Pickup`).

### Admin (Canteen Staff) Role
* **Secure Admin Access:** Separate, secure login for staff.
* **Full Menu Management (CRUD):** Easily **C**reate, **R**ead, **U**pdate, and **D**elete menu items.
* **Live Order Queue:** View all incoming employee orders in a real-time queue.
* **Order Status Control:** Update the status of any order to notify the employee.

---

## 📸 Screenshots

*(This is one of the most important sections. Add your own screenshots here!)*

| Employee Dashboard | Admin Menu Management |
| :----------------: | :---------------------: |
| ![Employee Dashboard](https://placehold.co/600x400/333/FFF?text=Employee+Dashboard+Screenshot) | ![Admin Menu Management](https://placehold.co/600x400/333/FFF?text=Admin+Dashboard+Screenshot) |

| Mobile View | Order History |
| :----------------: | :---------------------: |
| ![Mobile View](https://placehold.co/300x500/333/FFF?text=Mobile+View) | ![Order History](https://placehold.co/600x400/333/FFF?text=Order+History+Screenshot) |


---

## 🛠️ Technologies & Tools

* **Backend:** Java, Spring Boot, Spring Security
* **Frontend:** Thymeleaf, Bootstrap 5, HTML, CSS
* **Database:** MySQL
* **Authentication:** Spring Security for session-based authentication and role management.
* **Build Tool:** Maven

---

## 🚀 Setup and Installation

To get a local copy up and running, follow these simple steps.

### Prerequisites
* JDK 11 or higher
* Maven
* A running SQL database instance

### Installation
1.  **Clone the repo**
    ```sh
    git clone [https://github.com/](https://github.com/)[your-username]/[your-repo-name].git
    ```
2.  **Navigate to the project directory**
    ```sh
    cd [your-repo-name]
    ```
3.  **Configure the database**
    * Open `src/main/resources/application.properties`.
    * Update the `spring.datasource.url`, `spring.datasource.username`, and `spring.datasource.password` properties to match your local database configuration.
4.  **Run the application**
    ```sh
    mvn spring-boot:run
    ```
The application will be available at `http://localhost:8080`.

---

## 🧑‍💻 Usage

You can test the application using the following pre-populated credentials:

* **Employee Account:**
    * **Username:** `employee@test.com`
    * **Password:** `password`
* **Admin Account:**
    * **Username:** `admin@test.com`
    * **Password:** `adminpass`

---

## 📄 License

Distributed under the MIT License. See `LICENSE` for more information.
