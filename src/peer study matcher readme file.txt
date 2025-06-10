# Peer Study Matcher

**Desktop app to match students needing help with those who can assist, based on shared subjects.**

---

## 🧾 Table of Contents
1. [Overview](#overview)
2. [Core Features](#core-features)
3. [How It Works](#how-it-works)
4. [Validation & Error Handling](#validation--error-handling)
5. [Project Structure](#project-structure)
6. [Setup & Run](#setup--run)
7. [Next Steps](#next-steps)

---

## Overview
Peer Study Matcher is a JavaFX-based Java application that helps students connect with suitable study partners. Profiles include “subjects I can help with” and “subjects I need help in.” The app matches students based on common subjects.

---

## Core Features
- **CRUD operations**: Users can add, edit, or delete student profiles.
- **Matching logic**: Finds helpers based on shared subjects.
- **File handling**: Save and load user data via text files.
- **GUI interface**: Built with JavaFX controls (forms, lists, dialogs).

---

## How It Works
1. **User Input**: Enter name, role (helper/needy), and subjects.
2. **Data Processing**: Stored in memory and lists.
3. **Matching**: Click “Match” to see results for each needy student.
4. **Data Persistence**: Save data to `students.txt` and reload it later.

---

## Validation & Error Handling
- Ensures names and subjects are not empty.
- Validates that a role (helper/needy) is selected.
- Catches file exceptions (FileNotFound, I/O errors).
- Prevents app crashes and provides user alerts.

---

## Project Structure

PeerStudyMatcher/
├─ src/
│ └─ *.java
├─ data/
│ └─ students.txt
├─ presentation_review2.pptx
└─ README.md


---

## Setup & Run
1. **Install**: Java 17+ and extract JavaFX SDK.
2. **Configure VM args**:

--module-path C:/path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml

3. **Run**: Execute `Main.java` (launches GUI).
4. **Use App**:
- Add students → Save
- Load existing profiles → Edit/Delete
- Click “Match” to find study buddies


