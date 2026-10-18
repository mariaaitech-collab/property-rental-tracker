# Property Rental Tracker
> An Android app that helps landlords keep track of their properties, tenants and rent payments.

![language](https://img.shields.io/badge/language-Kotlin-4338CA?style=flat-square&logo=kotlin&logoColor=white) ![framework](https://img.shields.io/badge/framework-Android-4338CA?style=flat-square&logo=android&logoColor=white) ![license](https://img.shields.io/badge/license-MIT-6366F1?style=flat-square) ![status](https://img.shields.io/badge/status-in_progress-818CF8?style=flat-square)

## Overview
Landlords with a handful of properties often track rent in notebooks or spreadsheets. This app puts properties, tenants and payments on the phone, sends rent reminders, and creates a PDF receipt that can be shared with a tenant on WhatsApp. Data is stored on the device.

## Features
- Dashboard with totals for properties, tenants, income and pending payments
- Add and list properties, tenants and payments through quick-entry dialogs
- PDF rent receipts (iText), shared straight to WhatsApp
- Rent-due reminder notifications scheduled with Android alarms
- Account setup with business name, phone and PIN
- Offline storage with a Room database (properties, tenants, payments, maintenance)

## Tech Stack
| Layer | Technology | Version |
|---|---|---|
| Language | Kotlin | 1.9.20 |
| Build | Android Gradle Plugin | 8.7.3 |
| Database | Room | 2.6.1 |
| Architecture | ViewModel + LiveData (Lifecycle) | 2.7.0 |
| Navigation | Jetpack Navigation | 2.7.6 |
| Async | Kotlin Coroutines | 1.7.3 |
| DI | Hilt (plugin) | 2.49 |
| UI | Material Components | 1.12.0 |
| PDF | iText 7 | 7.2.5 |
| Animation | Lottie | 6.3.0 |
| Charts | MPAndroidChart | 3.1.0 |
| Auth | Firebase Auth (BoM) | 32.7.0 |

## Screenshots
<!-- Maria: add screenshots here -->

## Getting Started
**Prerequisites:** Android Studio (Ladybug or newer), JDK 17, an emulator or device on Android 7.0 (API 24) or later.

```bash
git clone https://github.com/mariaaitech-collab/property-rental-tracker.git
```

1. Open the folder in Android Studio and let Gradle sync.
2. Add your own `google-services.json` to `app/` (Firebase Auth is a declared dependency).
3. Run the `app` configuration.

## Status
**In progress.**
- The Tenants and Payments screens still default to property/tenant ID 1. A selector is planned.
- The Profile screen isn't wired up yet.
- Hilt and Firebase are referenced in code but not fully configured in the build. The `hilt-android` dependency and the Google Services plugin still need adding.
- The dashboard's monthly income bar chart still shows sample figures. It isn't reading from the database yet.

---
Built by Maria Shafqat · [Linktree](https://linktr.ee/mariashafqat_)
