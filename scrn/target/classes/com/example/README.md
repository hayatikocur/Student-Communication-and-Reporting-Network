# Student-Communication-and-Reporting-Network (SCRN)
CS 102 – Spring 2025 – Term Project  
Bilkent University – Computer Engineering

## Project Overview

The **Student Communication and Reporting Network** (SCRN) is a desktop-based JavaFX application that aims to enhance campus life by offering a real-time communication and feedback system between students and university authorities.

Students can report issues they observe in classrooms or campus areas and interact with other users via comments and upvotes. Issues with high engagement appear at the top of the list and have a higher chance of being addressed by authorities.

## Key Features

- Report classroom and campus problems  
- Comment and upvote on existing reports  
- View a live map with report densities via LeafletJS integration
- Send email notifications via Jakarta Mail API
- Upload and view images related to reports  
- User account types: student & authority  
- Clean and modern JavaFX UI with scene switching

## Technologies Used

| Tech         | Version        | Description                          |
|--------------|----------------|--------------------------------------|
| Java         | 21             | Main programming language            |
| JavaFX       | 21.0.7         | GUI framework                        |
| Jakarta Mail | 2.0.1          | Email integration                    |
| MySQL        |                | Backend database                     |
| Leaflet.js   | CDN-based      | Map visualization in WebView         |

## Dependencies

All required `.jar` dependencies are stored in the `/lib/` directory:

| Library                     | Purpose                     |
|-----------------------------|-----------------------------|
| `javafx.base.jar`           | JavaFX core runtime         |
| `javafx.controls.jar`       | UI controls                 |
| `javafx.fxml.jar`           | FXML support                |
| `javafx.web.jar`            | WebView for map             |
| `javafx.graphics.jar`       | Graphics rendering          |
| `javafx.media.jar`          | Media                       |
| `jakarta.mail-2.0.1.jar`    | JavaMail support            |
| `jakarta.activation*.jar`   | MIME type handling          |
| `mysql.jar`                 | MySQL database connector    | 


### Prerequisites

- JDK 21 or newer
- JavaFX SDK 21.0.7 downloaded and extracted
- JavaFX modules included in the `lib/javafx-sdk-21.0.7/lib` directory

## Authors & Contributors
- Hayati Kocur
- Mustafa Mert Mumcu
- Emir Akar
- Yiğit Kaan Önder
- Burhan Bulut
