# 🌤️ Simple Weather Program
## Spring 2025 Java Final Project
This weather application connects to **two APIs**:

- [Geocodio](https://www.geocod.io/) – to convert a ZIP code into latitude and longitude coordinates
- [Open-Meteo](https://open-meteo.com/) – to retrieve weather data using those coordinates

👉 **Note:** This program requires an **internet connection** to access the APIs.

---

## 🚀 How to Run

1. Run the `WeatherFxMain` class to launch the application.
2. Be sure to add `json.lib` to your project’s library dependencies.
3. Enter a valid **U.S. ZIP code** (this program only supports U.S. postal codes since it uses Geocodio).

---

## ⚠️ Important Notes

- This application only supports **American ZIP codes** (due to Geocodio API).
- An **internet connection is required** to fetch location and weather data.
- Make sure `json.lib` is included in your project’s library path to avoid runtime errors.

---

## 🎥 Demo

![Screen recording](src/images/screen-recording.gif)

---

## 🚀 How to Run the Application

1. **Clone the repository:**
   ```bash
   git clone https://github.com/Kroessle2/WeatherFinal.git