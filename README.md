#  Digital Exam Moderator

A smart online exam monitoring system using **AI, Spring Boot, and React**.

##  Overview
The **Digital Exam Moderator** ensures exam integrity by detecting suspicious behavior using AI models, providing a smooth front-end for users, and a robust backend for managing data.

---

##  Project Modules

###  Frontend
- Built with **React.js**
- Provides a user-friendly dashboard for students and proctors
- Handles login, live monitoring, and report visualization

###  Backend
- Developed using **Spring Boot**
- Handles authentication, API endpoints, and communication between modules
- Connects to the database for exam and user management

###  AI / ML Module
- Python-based detection system using **YOLOv8**
- Detects face position, objects, and user activity
- Sends warnings or alerts to the backend

---

##  Tech Stack
- **Frontend:** React.js  
- **Backend:** Spring Boot (Java)  
- **AI Module:** Python (YOLO, OpenCV, NumPy)  
- **Database:** (Mention here if you used MySQL/PostgreSQL, etc.)

---

## Folder Structure
DigitalExamModerator/
│
├── frontend/ → React app (UI)
├── backend/ → Spring Boot backend
├── ml/ → AI/YOLO Python module
└── demo_video.mp4 → Demo video (optional)

## How to Run the Project

###  Frontend
```bash
cd frontend
npm install
npm start
## Backend
bash
Copy code
cd backend
mvn spring-boot:run
##AI / ML Module
bash
Copy code
cd ml
python ai_server.py
###S Requirements
Each module has its own dependency files:

frontend/package.json

backend/pom.xml

ml/requirements.txt
