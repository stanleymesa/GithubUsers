# 📱 Github Users App

A modern Android application for browsing **GitHub user profiles and repositories**.  
Built with a focus on **maintainability, scalability, and great user experience**, the app leverages the latest Android development tools and best practices.

---

## ✨ Features

- **GitHub User Search**: Easily search for any GitHub user.  
- **User Profile Details**: View a user’s profile information, including bio, followers, and public repositories.  
- **Repository List**: Browse through a user’s public repositories with key details.  
- **Theme Customization**:  
  - 🌙 **Dark Mode**: Comfortable, eye-friendly theme for low-light environments.  
  - 🎨 **Dynamic Mode**: Adapts to the user’s device wallpaper (Material You on Android 12+).  

---

## 💻 Technical Stack

This project is built using a robust and modern stack to ensure high quality and scalability:

- **Jetpack Compose** → Modern declarative UI toolkit.  
- **Clean Architecture** → Separation of concerns into presentation, domain, and data layers.  
- **MVVM** → Keeps UI and business logic independent.  
- **Modularization** → Organized modules for faster builds and scalability.  

### Module Structure
- `:core` → Reusable/common code.  
- `:features` → Parent module containing feature-based modules:  
  - `:feature_name_data` → Data layer for a feature.  
  - `:feature_name_domain` → Domain layer for a feature.  
  - `:feature_name_presentation` → Presentation/UI layer for a feature.  
- `:shared-test` → Shared testing utilities.  

### Other Libraries
- **Dagger Hilt** → Dependency injection.  
- **Moshi** → JSON parsing.  
- **Retrofit** → HTTP client for GitHub REST API.  
- **Coroutines & Flow** → Asynchronous programming & reactive streams.  

---

## 🚀 How to Run the Project

### 1. Clone the Repository
```bash
git clone https://github.com/stanleymesa/GithubUsers.git
cd GithubUsers
```

### 2. Create a GitHub API Token

The app requires a GitHub Personal Access Token to make API calls. You can generate one by following these steps:

* Go to your GitHub account **Settings**.

* Navigate to **Developer settings** > **Personal access tokens** > **Tokens (classic)**.

* Click **Generate new token**.

* Give it a name (e.g., "Github Users App Token") and set the expiration.

* The token does not require any specific scopes.

* **Copy the generated token.** You will only be able to see it once.

### 3. Add the Token to `local.properties`

* In the root directory of the project, create a file named `local.properties`.

* Add the following line to the file, replacing `ghp_your_token` with the token you copied:
`github.api.token=ghp_your_token`

### 4. Sync and Run*

* Open the project in Android Studio.

* Let Gradle sync all dependencies.

* Run the app on an emulator or a physical device.

