# Customer Reminder Android App

This project is a native Android app written in Kotlin. It lets users:

- register customers with name, address, phone number, reminder date/time, and construction phase
- view all customers with search
- view all reminders with search
- schedule local reminder notifications for saved customers

## Build Instructions

1. Install Android Studio and the Android SDK.
2. Open the project folder in Android Studio.
3. Sync Gradle and build the project.
4. Run the app on an emulator or device, or generate an APK from `Build > Build Bundle(s) / APK(s) > Build APK(s)`.

## Notes

- Data is stored locally in Room database.
- Notification scheduling uses `AlarmManager` and a broadcast receiver.
