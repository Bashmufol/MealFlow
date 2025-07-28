# MealFlow 🍽️

A full-stack, role-based web application designed to modernize and streamline the corporate canteen ordering process. It allows company employees to pre-order their meals from the office canteen and allows canteen staff to manage the menu and orders. Employees can pre-order meals to save time, and canteen staff can efficiently manage menus and incoming orders through a dedicated admin dashboard.

**Live Demo:** **mealflow.herokuapp.com**

---

## ✨ Key Features

### User (Employee) Role
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

| User Dashboard | Admin Dashboard |
| :----------------: | :---------------------: |
| ![[User Dashboard](https://github.com/Bashmufol/MealFlow/blob/main/src/main/resources/templates/images/user-dashboard.png)] | ![[Admin Dashboard](https://github.com/Bashmufol/MealFlow/blob/main/src/main/resources/templates/images/admin-dashboard.png)] |

| Mobile View |
| :----------------: | 
| ![[Mobile View](https://github.com/Bashmufol/MealFlow/blob/main/src/main/resources/templates/images/mobile-view.png)]| 


---

## 🛠️ Technologies & Tools

* **Backend:** Java, Spring Boot, Spring Security
* **Frontend:** Thymeleaf, Bootstrap 5, HTML, CSS
* **Database:** MySQL
* **Authentication:** Spring Security for session-based authentication and role management.
* **Build Tool:** Maven

---

## 🧑‍💻 Usage

You can test the application using the following pre-populated credentials:

* **Employee Account:**
    * **Username:** `user`
    * **Password:** `password`
* **Admin Account:**
    * **Username:** `admin`
    * **Password:** `adminPass`

---
