# ✅ TaskFlow – Modern To-Do List Application

A clean, simple, and modern to-do list mobile application that helps you organize your day, check off what's done, and stay on track. Built with a warm orange design language and a focus on a distraction-free experience.

This project is developed as **Task 3** for the Android App Development Internship at **Oasis Infobyte**.

---

## 👨‍💻 Developer
* **Name:** Karam Mahmoud Mostafa Salama  
* **Program:** Android App Development Internship (Oasis Infobyte)

---

## 🌟 Features
* **Splash Screen:** A bold, branded welcome screen showing the TaskFlow logo and tagline *"Organize your day, simply."*
* **Onboarding Screen:** A short introduction to the app with a task preview card and a **Get Started** button.
* **Authentication:** Secure **Login** (email or username + password) and **Create Account** (name, email, password with a minimum of 6 characters) screens, with easy switching between the two.
* **Personalized Home:** A friendly greeting with the user's name (e.g. *"Hello, Nadia 👋"*) and a **Logout** option.
* **Task Management:** Add new tasks with a title and optional description, mark tasks as done, and delete tasks you no longer need.
* **Organized Lists:** Tasks are automatically grouped into **To Do** and **Completed** sections, each with a live counter.
* **Visual Feedback:** Completed tasks are checked off and struck through so progress is clear at a glance.
* **Floating Action Button:** A quick **+ Add Task** button always within reach.

---

## 📸 App Screenshots

| Splash Screen | Onboarding | Login | Register | My Tasks |
| :---: | :---: | :---: | :---: | :---: |
|   <img width="180" src="https://github.com/user-attachments/assets/3015e568-eb90-488e-bf65-09841a69fa9b" /> | <img width="180" src="https://github.com/user-attachments/assets/b8817135-b97e-4a47-849c-d71c0a0ffa20" /> | <img width="180" src="https://github.com/user-attachments/assets/92d0b9e4-a8f4-4dfa-8fd2-498b0e48a427" /> | <img width="180" src="https://github.com/user-attachments/assets/37bdccc4-7163-4f15-adfc-5cc8762bbd38" /> |<img width="180" src="https://github.com/user-attachments/assets/006a3c08-43c2-46a3-8a08-e6b065356716" /> |


---

## 🎨 Design
* **Primary Color:** Warm orange (`#E8663F`)
* **Background:** Soft cream / off-white for a calm, readable look
* **Style:** Rounded cards, large touch-friendly buttons, and clear typography

---

## 🛠️ Tech Stack & Libraries
* **Language:** Java *(update if you used Kotlin / Flutter / etc.)*
* **UI Frameworks:** Android Views, ConstraintLayout, RecyclerView, Material Components
* **Data Storage:** *(e.g. SharedPreferences / SQLite / Room / Firebase – update to match your project)*
* **Architecture & Tools:** Android Studio, Gradle

---

## 📂 Project Structure
```text
com.example.taskflow
│
├── modules/
│   ├── splash/
│   │   └── views/
│   │       └── SplashActivity.java        # Splash delay and navigation
│   │
│   ├── onboarding/
│   │   └── views/
│   │       └── OnboardingActivity.java    # Intro screen with Get Started button
│   │
│   ├── auth/
│   │   └── views/
│   │       ├── LoginActivity.java         # Login form and validation
│   │       └── RegisterActivity.java      # Account creation and validation
│   │
│   └── home/
│       ├── views/
│       │   └── HomeActivity.java          # Task lists, add/complete/delete logic
│       └── adapters/
│           └── TaskAdapter.java           # RecyclerView adapter for tasks
│
└── res/
    ├── layout/                            # XML layouts for all screens
    ├── values/                            # Colors, strings, themes, and styles
    └── drawable/                          # Icons and vector assets
```

---

## 🚀 Getting Started
1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/taskflow.git
   ```
2. Open the project in **Android Studio**.
3. Let Gradle sync finish.
4. Run the app on an emulator or a physical device.

---

## 📄 License
This project is created for educational purposes as part of the Android App Development Internship at Oasis Infobyte.
