# SlotBooker App

SlotBooker is an Android application developed as part of a technical assessment. The app allows users to browse available time slots and book an event by entering customer details.

---

## 📌 Features

- **Home Screen**
    - Displays previously booked slots.
    - Floating Action Button to start a booking.

- **Slot Selection**
    - Fetches available slot list from a mocked backend.
    - Slots displayed in a grid layout.

- **Customer Details**
    - Validates user input.
    - Books a slot through a mocked API.
    - Displays loading, success, or error messages.

---

## 🏛 Architecture

The project follows a **Clean MVVM** structure:
com.slotbooker.app
┣ data
┃ ┣ mock
┃ ┣ remote
┃ ┗ repository
┣ domain
┃ ┣ model
┃ ┣ repository
┃ ┗ usecase
┣ ui
┃ ┣ components
┃ ┣ navigation
┃ ┗ screen
┣ util
┣ di
┗ SlotBookerApplication


---

## 🧰 Tech Stack

| Category | Tools |
|----------|-------|
| UI | Jetpack Compose |
| State Management | ViewModel + StateFlow |
| Networking | Retrofit + Coroutines |
| Dependency Injection | Hilt |
| Local Fake Backend | In-memory based mock service |
| Testing | JUnit, MockK, Turbine, MockWebServer, Coroutine Test |

---

## 🗄 API Behavior - Mock Response and Networking Behavior

The application uses Retrofit along with MockWebServer to simulate API responses during runtime. All network calls are served from a predefined mock response rather than a live backend. This allows testing booking workflow, UI states, and error handling without an actual server dependency.

| Endpoint | Method | Description                 |
|----------|--------|-----------------------------|
| `/available-slots` | GET | Fetches available time slots|
| `my-bookings` | GET | Fetches user time slots     |
| `/slot-booking` | POST | Books a slot with user details |

---
## Data Persistence Notice

* User bookings are stored only in memory for demonstration purposes. Data will not persist after the application is closed or killed. When the app restarts, previously created bookings will not be retained.
---

## 🧪 Unit Testing

Tests are written for:
- `DetailsViewModel`
- `SlotsViewModel`
- `BookingRepositoryImpl`

---

Covered Scenarios:
- Validation errors (missing name/phone)
- Loading state emission
- Successful booking update
- API failure response
- Repository response mock using MockWebServer

---
## ▶️ How to Run

1. Clone the project:

```git clone https://github.com/bharath9866/SlotBooker.git```