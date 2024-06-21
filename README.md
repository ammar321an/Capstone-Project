# Capstone Project: Animal Deterrence System

This is my Capstone project, where I developed and utilized a software application using Android Studio, ESP32, and Arduino to control the robot's algorithm and also to send automatic notifications via WhatsApp.

---

## Project Description

This Capstone project, created by Ammar, functions to deter animals in the garden using gun sound effects. The system utilizes an ultrasonic sensor for detection. A key feature of this project is the integration of a software application, developed with Android Studio, which includes login and registration pages for security purposes. Only the admin (owner of the robot) has access to the system.

The main microcontroller used is the ESP32, with an Arduino Uno R4 for activating the ultrasonic sensor.

---

## How It Works

1. **Login**: The owner must first log in using the software application to initiate the algorithm.
2. **IP Calibration**: After logging in, the owner needs to enter the IP address provided by the ESP32.
3. **Main Page**: The main page features an ON/OFF button to control the algorithm.
4. **Ultrasonic Sensor**: When the sensor detects an object within the assigned range, it sends a serial command from the Arduino Uno to the ESP32, triggering the speaker to emit the deterrent sound.
5. **WhatsApp Notification**: Simultaneously, an alert notification is sent to the owner's WhatsApp.

---

## Key Components

| Component               | Description                                                    |
|-------------------------|----------------------------------------------------------------|
| **ESP32**               | Main microcontroller for controlling the system.               |
| **Arduino Uno R4**      | Activates the ultrasonic sensor.                               |
| **DFPlayer Mini**       | Plays sound files for the deterrent sound effect.              |
| **4-inch Speaker**      | Outputs the deterrent sound.                                   |
| **Ultrasonic Sensor**   | Detects the presence of objects within a specific range.       |
| **LED**                 | Provides visual indicators.                                    |
| **Motor Driver**        | Controls the high torque motor.                                |
| **High Torque Motor**   | Drives the physical deterrent mechanism.                       |
| **12V Battery**         | Powers the entire system.                                      |
| **Voltage Regulator**   | Regulates voltage to safe levels for components.               |
| **Wiring and Resistors**| Connects and ensures proper operation of electrical components.|
| **Caster Wheel**        | Supports and stabilizes the robot.                             |

---

## Features

- **Login & Registration Pages**: Ensures only authorized users can access the system.
- **IP Calibration**: Connects the app to the ESP32 via IP address.
- **ON/OFF Control**: Allows the owner to start and stop the algorithm.
- **Real-Time Notifications**: Sends alerts via WhatsApp when an object is detected.

---

## Contact

[![LinkedIn](https://img.shields.io/badge/LinkedIn-Connect-blue)](https://www.linkedin.com/in/muhammad-ammar-yaseer-azizan-48b28a235/)

---

Thank you for visiting my project! Feel free to reach out if you have any questions or would like to collaborate.
