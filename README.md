# TickTask Android App

TickTask is an Android task management app that allows teams to collaborate and manage their tasks effectively.

## Features

- **Authentication:** User authentication and authorization
- **Task Creation:** Create and view tasks
- **Task Assignment:** Assign tasks to team members
- **Task Filtering:** Filter tasks by status and assignee
- **Task Details:** View task details and comments
- **Task Status:** Update task status
- **Search:** Search for tasks by title or content
- **Network Connection Handling:** Use Lottie animation to handle no network connection

## Technical Details

TickTask was developed using the following technical requirements:

- Networking was implemented using OkHttp library.
- ViewBinding was used to handle UI states.
- Shared Preferences were used to store the user's token.
- A Log Interceptor was added to help with debugging by logging all requests.
- An Authorization Interceptor was created to authorize all requests.
- The user's token is stored until it expires. Passwords and usernames are not stored in Shared Preferences.
- No Jetpack libraries were used in this project.
- All response models were created manually and no model generator was used.
- MVP (Model-View-Presenter) design pattern was implemented to allow for easier development and maintenance of the codebase.

## Getting Started

1. Clone the repository:

   ```
   git clone https://github.com/team-chocolate-cake/My-Team-TODO.git
   ```

2. Open the project in Android Studio.

3. Contact us for the API key to use the app.

4. Build and run the app.

## Usage

To use the application, you must first create an account or log in using your existing credentials. Once you have logged in, you can create tasks and assign them to team members. You can also update the status of tasks as they are completed. You can filter tasks by status and assignee to help you keep track of everything. Additionally, you can search for tasks by their title or content.

If there is no network connection, a Lottie animation is displayed to notify the user.

## Contributing

Contributions are welcome! If you would like to contribute to the project, please fork the repository and submit a pull request.