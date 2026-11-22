# ✅ TaskFlow – Modern To-Do List Application

A clean, simple, and modern to-do list mobile application that helps you organize your day, check off what's done, and stay on track. Built with a warm orange design language and a distraction-free experience.

This project is developed as **Task 3** for the Android Application Development Internship at **Oasis Infobyte**.

---

## 👨‍💻 Developer
- **Name:** Karam Mahmoud Mostafa Salama
- **Program:** Android Application Development Internship (Oasis Infobyte)

---

## 🌟 Features
- **Splash Screen:** Branded welcome screen with the TaskFlow identity and smooth navigation.
- **Onboarding Screen:** Introductory screen with a task preview and a **Get Started** action.
- **Authentication:** **Login**, **Register**, and **Forgot Password** flows powered by Firebase Authentication with input validation and password visibility toggle.
- **Personalized Home:** Displays a greeting with the user's name/email and a **Logout** action with confirmation.
- **User-Scoped Task Management:** Add tasks with title, optional note, priority (`Low`, `Medium`, `High`), and due date stored locally in SQLite per user account.
- **Completion & Deletion:** Mark tasks as complete with strikethrough styling and automatic ordering, delete tasks permanently, and view a friendly empty state when there are no tasks.

---

## 📸 App Screenshots

| Splash Screen | Onboarding | Login | Register |
| :---: | :---: | :---: | :---: |
| <img width="180" alt="splash" src="https://github.com/user-attachments/assets/3015e568-eb90-488e-bf65-09841a69fa9b" /> | <img width="180" alt="onboarding" src="https://github.com/user-attachments/assets/b8817135-b97e-4a47-849c-d71c0a0ffa20" /> | <img width="180" alt="login" src="https://github.com/user-attachments/assets/92d0b9e4-a8f4-4dfa-8fd2-498b0e48a427" /> | <img width="180" alt="register" src="https://github.com/user-attachments/assets/37bdccc4-7163-4f15-adfc-5cc8762bbd38" /> |

| Forget Password | My Tasks | Add Task |
| :---: | :---: | :---: |
| <img width="180" alt="forget-password" src="https://github.com/user-attachments/assets/d94c1fb1-9bc3-4b21-99f2-a5eb5eb7e18f" /> | <img width="180" alt="home" src="https://github.com/user-attachments/assets/de21d8ba-c68a-4e8d-a409-051860ee116d" /> | <img width="180" alt="add-task" src="https://github.com/user-attachments/assets/eeb2942b-ed3a-4cbf-83de-b5d69783a0b1" /> |

---

## 🎨 Design
- **Primary Color:** Warm orange (`#E8663F`)
- **Background:** Clean light surfaces with soft accent containers
- **Style:** Rounded inputs and cards, clear typography (`Kanit`), and accessible touch targets

---

## 🛠️ Tech Stack & Architecture
- **Language:** Java
- **Architecture:** MVVM (`ViewModel`, `LiveData`, and `Repository`)
- **Authentication:** Firebase Authentication
- **Local Storage:** SQLite (`TaskDbHelper` for user-linked tasks and `CacheHelper` for app state/cache)
- **UI & Components:** XML Layouts, `ScrollView`, `RecyclerView`, Material Components (`Chip`, `MaterialButton`, `Snackbar`, `AlertDialog`)
- **Tools:** Android Studio, Gradle

---

## 📂 Project Structure
```text
com.example.todoapplication
├── core/
│   ├── data/
│   ├── errors/
│   ├── functions /
│   └── helpers/
│
├── modules/
│   ├─  splash
│   │
│   ├── onboarding
│   │     ├── model/
│   │     ├── view/
│   │     └── viewmodel/
│   │
│   ├── auth/
│   │   ├── model/
│   │   ├── view/
│   │   │   ├── LoginActivity.java
│   │   │   ├── RegisterActivity.java
│   │   │   └── ForgetPasswordActivity.java
│   │   └── viewmodel/
│   └── home/
│       ├── model/
│       ├── view/
│       │   ├── MainActivity.java
│       │   ├── AddTaskActivity.java
│       │   └── TaskAdapter.java
│       └── viewmodel/
└── res/
    ├── color/
    ├── drawable/
    ├── font/
    ├── layout/
    └── values/
