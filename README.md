# Mobile App: Customer Management and Merchandise Inventory
This mobile app, developed using Java and Firebase, allows users to perform CRUD (Create, Read, Update, Delete) operations with customer information and manage merchandise inventory. The app enables users to add, modify, and delete merchandise entries, including the option to upload pictures of the merchandise. All the data is stored and synchronized with Firebase, providing real-time updates across multiple devices. Additionally, the app allows users to generate bills for customers, which can be downloaded as PDF files and uploaded to Firebase for record-keeping purposes.

## Features
The app provides the following features:

### •	Customer Management:
•	Add new customers with their details, such as name, contact information, and address.
•	View and edit customer information.
•	Delete customer entries when necessary.

### •	Merchandise Inventory:
•	Add new merchandise entries, including details like name, description, quantity, and price.
•	Upload pictures of merchandise to visually represent the products.
•	Update merchandise information as required.
•	Delete merchandise entries when necessary.

### •	Firebase Integration:
•	Utilize Firebase's real-time database functionality to store and synchronize customer and merchandise data across devices.
•	Store merchandise pictures in Firebase Storage for easy access and retrieval.

### •	Bill Generation:
•	Create bills for customers that include details of multiple merchandise items.
•	Generate bills in PDF format for easy sharing and printing.
•	Download bills as PDF files to the device's local storage.

### •	PDF Upload to Firebase:
•	Upload generated bills in PDF format to Firebase Storage.
•	Store bills securely in Firebase for record-keeping and future reference.

### Technologies Used
The app is developed using the following technologies:
•	Java: The primary programming language for the Android app development.
•	Firebase: A cloud-based platform providing backend services, including real-time database, cloud storage, and authentication.

### Setup and Configuration
To set up the app locally, follow these steps:
1.	Clone the repository from [GitHub Repository Link].
2.	Open the project in your preferred Integrated Development Environment (IDE).
3.	Configure Firebase:
•	Create a new Firebase project in the Firebase Console.
•	Enable the required Firebase services, including the Realtime Database and Firebase Storage.
•	Download the google-services.json configuration file for Android and add it to the app's project directory.

### 4.	Build and run the app on an Android device or emulator.
#### Dependencies
The app relies on the following dependencies:
•	Firebase SDK: [Firebase SDK Version]
•	PDF library: [PDF Library Version]
•	Android Support Libraries: [Support Library Versions]
Make sure to include these dependencies in your project's build.gradle file.

#### Contributions
Contributions to this project are welcome. If you encounter any issues or would like to suggest improvements, please open an issue in the GitHub repository. You can also submit pull requests for bug fixes or new features.

#### License
This project is licensed under the MIT License. Feel free to modify and distribute the app according to the terms of the license.
#### Acknowledgments
We would like to thank the developers and contributors of the libraries and frameworks used in this project for their excellent work and support.
[Optional: Add credits or acknowledgments to any external resources used in the project.]
For any further assistance or inquiries, please contact [Your Contact Information].

