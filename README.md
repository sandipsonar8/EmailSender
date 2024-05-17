# EmailSender

EmailSender is a Java program that periodically sends emails with updates from RDX India. It retrieves email credentials and recipient addresses from a MySQL database, formats a message with the current system time, and sends it using the Gmail SMTP server.

## Features

- Connects to a MySQL database to fetch email credentials and recipient addresses.
- Sends HTML formatted emails with the current system time.
- Uses JavaMail API for sending emails.
- Schedules email sending at fixed intervals using `ScheduledExecutorService`.

## Prerequisites

Before you begin, ensure you have met the following requirements:

- Java Development Kit (JDK) installed
- MySQL database installed
- Internet connection
- Gmail account for sending emails

## Installation

1. Clone the repository
    ```sh
    git clone https://github.com/your-username/emailsender.git
    ```

2. Open the project in your preferred Java IDE (Eclipse, IntelliJ, etc.)

3. Add the following dependencies to your project:
   - JavaMail API (`javax.mail.jar`)
   - MySQL Connector/J (`mysql-connector-java.jar`)

## Database Setup

1. Create a MySQL database named `emails`.
2. Create the necessary tables (`fromtable`, `totable`, `cctable`) with the required fields:
    ```sql
    CREATE TABLE fromtable (
        id INT AUTO_INCREMENT PRIMARY KEY,
        email VARCHAR(255) NOT NULL,
        pass VARCHAR(255) NOT NULL
    );

    CREATE TABLE totable (
        id INT AUTO_INCREMENT PRIMARY KEY,
        email VARCHAR(255) NOT NULL
    );

    CREATE TABLE cctable (
        id INT AUTO_INCREMENT PRIMARY KEY,
        email VARCHAR(255) NOT NULL
    );
    ```
3. Insert your email credentials and recipient addresses into the tables:
    ```sql
    INSERT INTO fromtable (email, pass) VALUES ('your-email@gmail.com', 'your-email-password');
    INSERT INTO totable (email) VALUES ('recipient-email@example.com');
    INSERT INTO cctable (email) VALUES ('cc-email1@example.com'), ('cc-email2@example.com');
    ```

## Usage

1. Open the `EmailSender` class file.
2. Modify the `main` method to set the desired interval for email sending:
    ```java
    scheduler.scheduleAtFixedRate(task, 0, 1, TimeUnit.MINUTES);
    ```
   You can change the interval to seconds, hours, etc., by modifying the `TimeUnit` and interval value.
3. Run the `EmailSender` program.

## Code Explanation

### Main Method

The `main` method initializes a `ScheduledExecutorService` and schedules the `sendEmails` task to run at fixed intervals.

### sendEmails Method

- Connects to the MySQL database and retrieves email credentials and recipient addresses.
- Formats the current system time.
- Creates an HTML formatted email message.
- Calls `sendEmail` method to send the email.

### sendEmail Method

- Configures the Gmail SMTP server properties.
- Authenticates using the email credentials.
- Composes and sends the email using the JavaMail API.

## Troubleshooting

- Ensure you have enabled "Less secure app access" for your Gmail account or use an app-specific password if 2-Step Verification is enabled.
- Verify your database connection details (URL, username, password).
- Check if the required dependencies (JavaMail API, MySQL Connector/J) are correctly added to your project.

