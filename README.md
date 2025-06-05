# 🔐 Secure DES Utility Web Application

This project is a web-based utility for encrypting and decrypting text using the **Data Encryption Standard (DES)** algorithm. It provides a simple, interactive user interface to perform secure message encoding and decoding operations.

---

## 📌 Features

- 🔒 Encrypt plain text messages using DES
- 🔓 Decrypt previously encrypted messages
- 📄 User-friendly web interface with dynamic output display
- 🖥️ Backend integration with Java-based DES logic
- 🚫 Error handling for failed encryption/decryption
- 🌐 Built with Django (Python) for backend and HTML/CSS for frontend

---

## 🏗️ Project Structure
DES_PBL/
├── app/
│ ├── templates/
│ │ └── des_form.html # Main UI template
│ ├── views.py # Logic to handle encryption/decryption
│ └── ...
├── des_encryption/
│ ├── DESUtility.java # Java class for DES encryption/decryption
│ ├── input.txt # Temporary file to read user input
│ └── output.txt # Temporary file for result output
├── manage.py
└── README.md

---


---

## ⚙️ How It Works

1. User inputs a message in the form.
2. On clicking **Encrypt** or **Decrypt**, the input is saved to a file.
3. Django triggers a Java subprocess to run the `DESUtility` class.
4. The Java class reads the input, processes it using DES, and writes the result to `output.txt`.
5. The output is read and displayed on the same page.

---

## 🚀 Getting Started

### 1. Prerequisites

- Python 3.12+
- Java (JDK 8+)
- Django 5.1+
- Working Java compiler (`javac`) and runner (`java`)

### 2. Clone the Repository

bash
git clone https://github.com/manishmora/secure-des-web.git
cd secure-des-web

### 3. Setup Python Environment

python -m venv env
source env/bin/activate  # or `env\Scripts\activate` on Windows
pip install -r requirements.txt

### 4. Compile the Java Class

cd des_encryption
javac DESUtility.java
cd ..

### 5. Run Django Server

python manage.py runserver

Open http://127.0.0.1:8000 in your browser.
