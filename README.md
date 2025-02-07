# 🏋️ Yoga App

## 📝 Description
Yoga App is a full-stack application designed to help users manage their yoga sessions.
---

## 📂 Resources
- **Postman Collection**: [ressources/postman/yoga.postman_collection.json](#)  
- **MySQL Script for creating the shema**: [ressources/sql/script.sql](#)

---

## ⚙️ Setup

### 1️ Database Setup
To set up the **MySQL** database:
1. Install **MySQL** if not already installed.
2. In your Sql Command Line:
     mysql> CREATE DATABASE test;
    mysql> USE test;
    mysql> SOURCE ressources/sql/script.sql
5. Update the **backend configuration file** (`application.properties` or `application.yml`) with your MySQL credentials:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/test?allowPublicKeyRetrieval=true
   spring.datasource.username=root
   spring.datasource.password=root

### 2 BackEnd Setup
To run BackEnd:
1. Git clone https://github.com/yassine-berriri/yoga_App.git
2. mvn clean install
3. mvn spring-boot:run

### 3 FrontEnd Setup
To run FrontEnd:
1. Go inside front folder: cd front
2. Install dependencies: npm install
3. Launch Front-end: ng s
4. the admin user account that you can use to connect is: login: yoga@studio.com  password: test!1234

## ⚙️ Steps to run tests

### 1 FrontEnd unit and integration tests

Go to the root of the front app (openClassroom\p5\Testez-une-application-full-stack\front) and open a terminal: npm run test

### 2 FrontEnd E2E tests

Go to the root of the front app (openClassroom\p5\Testez-une-application-full-stack\front) and open a terminal : npm run e2e

Cypress should open and ask you which browser you want to use for your tests.

<img width="896" alt="image" src="https://github.com/user-attachments/assets/1af464dc-cbd0-4dd3-955c-2f9e865d46b6" />

Once chosen, go to the specs menu on the left and then you can select which e2e test you want to run.

<img width="959" alt="image" src="https://github.com/user-attachments/assets/d04af884-27ab-4eae-91d4-492a74679e34" />

### 3 BackEndEnd unit and integration tests

use maven with the following command to run all tests: mvn clean test

## ⚙️ Steps to generate coverage tests

### 1 Front unit and integration tests

Go to the root of the front app (openClassroom\p5\Testez-une-application-full-stack\front) and run the following command in terminal: npm run test:coverage

Results should show up in the terminal.

<img width="595" alt="image" src="https://github.com/user-attachments/assets/eb480fd9-563d-43c7-a1e6-09e4a846c201" />

### 2 FrontEnd E2E tests

Go to the root of the front app (openClassroom\p5\Testez-une-application-full-stack\front) and run the following command in terminal: npm run e2e:coverage

Results should show up in the terminal.

<img width="430" alt="image" src="https://github.com/user-attachments/assets/90aea1d3-8e24-4d1f-9760-e9e4db7061ce" />

### 3 BackEndEnd unit and integration tests

use maven with the following command: mvn clean test

You can find the report in the target folder: target/site/jacoco/index.html by opening the index.html file in a browser.

<img width="956" alt="image" src="https://github.com/user-attachments/assets/a6ca105b-046a-4bc8-9529-bef47cfe145b" />







