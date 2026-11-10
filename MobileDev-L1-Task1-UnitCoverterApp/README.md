# 📱 Modern Android Unit Converter App

A clean, simple, and fast unit converter app. Pick a category, type a value, and get the converted result instantly, with a one-tap swap and a copy button.

This project is developed as **Task 1** for the Android App Development Internship at **Oasis Infobyte**.

---

## 👨‍💻 Developer
* **Name:** Karam Mahmoud Mostafa Salama  
* **Program:** Android App Development Internship (Oasis Infobyte)

---

## 🌟 Features
* **Splash Screen:** A beautifully styled welcoming screen with clean typography, version tracking, and smooth transitions.
* **Modern UI/UX:** Designed following Material Design guidelines with custom color palettes and responsive button margins.
* **Real-time Expression Evaluation:** Powered by the **Mozilla Rhino** JavaScript engine to compute mathematical expressions seamlessly as you type.
* **Essential Operations:** Supports addition, subtraction, multiplication, division, percentage/modulo operations, and decimal calculations.
* **Advanced Controls:** Includes a Clear All (`AC`) option and a functional Backspace (`⌫`) button for correcting inputs.
* **Edge-to-Edge Display:** Implements modern Android window insets for immersive full-screen layouts.

---

## 📸 App Screenshots

| Splash Screen | Converter Home |
| :---: | :---: |
| <img src="https://github.com/user-attachments/assets/e6b19091-7d21-4106-a820-0a544efd6e70" width="250"/> | <img src="https://github.com/user-attachments/assets/362436b2-9212-448f-b794-b362a2cdff89" width="250"/> |


---

## 🔄 Supported Units

| Category | Units |
| :--- | :--- |
| Length | m, km, cm, mm, mi, yd, ft, in |
| Weight | kg, g, mg, t, lb, oz |
| Temperature | °C, °F, K |
| Volume | L, mL, m³, gal, cup, fl oz |
| Speed | m/s, km/h, mph, kn |

Conversions for Length, Weight, Volume, and Speed go through a base unit (meter, kilogram, liter, and meter per second). Temperature uses dedicated formulas, converting through Celsius.

---

## 🎨 Design Specs
* **Home screen frame:** 360 × 760, corner radius 32
* **Primary color:** Blue `#2563EB`
* **Text color:** `#111827`
* **Muted text:** `#6B7280`
* **Borders:** `#E5E7EB`
* **Background:** `#F9FAFB`
* **Font:** Inter (fallback: Segoe UI, Roboto, system sans-serif)

---

## 🛠️ Tech Stack & Libraries
<!-- Edit this section to match what you actually use -->
* **Language:** Java
* **UI Frameworks:** Android Views, ConstraintLayout, Material Components
* **Design:** Figma
* **Tools:** Android Studio, Gradle

---

## 📂 Project Structure
```text
com.example.unitconverter
│
├── modules/
│   ├── splash/
│   │   └── views/
│   │       └── SplashActivity.java    # Splash delay and navigation to home
│   │
│   └── home/
│       └── views/
│           └── HomeActivity.java      # Category selection, conversion logic, swap, and copy
│
└── res/
    ├── layout/                        # XML layouts for activity_splash & activity_home
    ├── values/                        # Colors, strings, themes, and styles
    └── drawable/                      # Icons and vector assets
```

---

## 🚀 Getting Started
1. Clone the repository.
2. Open the project in Android Studio.
3. Let Gradle sync, then run the app on an emulator or a device.
