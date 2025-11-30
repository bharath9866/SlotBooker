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

## 🗄 API Behavior

All API calls are simulated using a fake in-memory mock server.

| Endpoint | Method | Description                 |
|----------|--------|-----------------------------|
| `/available-slots` | GET | Fetches available time slots|
| `my-bookings` | GET | Fetches user time slots     |
| `/slot-booking` | POST | Books a slot with user details |

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