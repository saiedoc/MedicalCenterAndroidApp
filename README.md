# Clinic Management Android App

This is an Android application designed to assist doctors and patients in managing their medical centers and documents. The app utilizes Android development tools and is supported by a Spring Boot backend framework, a SQL Server database, and JPA ORM for data management. Various software development patterns and designs, such as REST APIs, Singletons, OOP, and database architecture patterns, were implemented throughout the project.

## Features

- **Responsive UI**: Designed to be user-friendly and efficient on different screen sizes.
- **Document Management**: Enables doctors and patients to manage medical documents securely.
- **Backend Integration**: Communicates with a Spring Boot backend to fetch and persist data.
- **Firebase Integration**: Uses Firebase for messaging, database, and other functionalities.
- **QR Code Scanning**: Supports scanning of QR codes for document verification.
- **PDF Viewing**: Provides built-in support for viewing PDFs within the app.
- **Charting**: Utilizes MPAndroidChart for visualizing patient data and medical records.

## Project Status

⚠️ **Note:** This project has not been maintained since 2021. Dependencies, configurations, and libraries may be outdated. If you are interested in using or contributing to the project, please contact me to update the necessary dependencies and configurations to get the app running again.

## Prerequisites

Before running the app, ensure that the following software is installed:

- **Android Studio**: You can download it from [here](https://developer.android.com/studio).
- **Java SDK (JDK 8 or later)**: Ensure you have the appropriate version installed for Android development.
- **Spring Boot Backend**: The app relies on a Spring Boot backend to function correctly. The backend repository can be found [here](https://github.com/saiedoc/MedicalCenterBackendApp).

## Project Setup

### Clone the repository

```bash
git clone https://github.com/saiedoc/MedicalCenterAndroidApp
cd MedicalCenterAndroidApp
```

### Android Studio Setup

1. Open the project in **Android Studio**.
2. Let **Gradle** sync to resolve dependencies automatically. (Note: Some dependencies may need to be updated due to their age and may cause errors.)
3. Build the project using the **Build** menu in Android Studio.
4. Connect a physical Android device or set up an Android Emulator.

### Running the Application

Once the project is set up in Android Studio, you can run it by pressing the **Run** button or using the following command:

```bash
./gradlew assembleDebug
```

This will install the app on your connected device or emulator.

### Backend Configuration

The Android app communicates with a Spring Boot backend via REST APIs. Ensure the backend server is running, and update the necessary endpoints in the app if required.

## Build and Dependency Information

The app is built using **Gradle**, and the key dependencies include:

- **Firebase**: For real-time database and messaging
- **Retrofit**: For making API calls to the backend
- **Room**: For local data storage and ORM
- **ZXing**: For QR code scanning
- **MPAndroidChart**: For charting and visualizing medical data
- **Android PDF Viewer**: For viewing PDF documents within the app


## Maintenance and Support

Given that this project has not been maintained for the past three years, the libraries and configurations may be outdated and could result in build issues. Please **contact me** if you encounter any problems, and I will assist you with updating the dependencies and configurations to get the project up and running.

## Authors

- **Saied Aussi**
- **Eyad Al Sayed**
- **Caesar Farah**
- **Abd Al Raheem Khoulani**
- **Salem Al Aushoor**

## License

This project is licensed under the ISC License.
