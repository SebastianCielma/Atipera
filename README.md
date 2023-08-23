# GitHub Repo Viewer

GitHub Repo Viewer is an application that allows users to retrieve a list of their non-fork repositories from GitHub. The application provides an API interface for fetching repository information, including branches and their last commit SHAs.


## Introduction
GitHub Repo Viewer is designed to help users retrieve and view their GitHub repositories. The application provides a simple API to fetch repository information, branches, and last commit SHAs.

## Installation
1. Clone the repository from GitHub.
2. In the `application.properties` file, configure the application parameters.

## Usage
### Getting User Repositories
**Endpoint:** `/api/repositories/{username}`  
**Method:** GET  
**Parameters:**
- `username` - GitHub username

**Example Request:**
GET /api/repositories/sebastiancielma

## API Endpoints
GET /api/repositories/{username} - Retrieve a list of user repositories.

## Error Handling
In case of errors, the server returns an appropriate HTTP status and a JSON response with an error message. Possible error codes and descriptions:

- 404 Not Found - When the GitHub user does not exist.
- 406 Not Acceptable - When the client requests a response format other than JSON.

## Testing 
The application is covered by unit tests that verify various use cases and error handling. Tests can be executed using a build tool (e.g., Gradle or Maven) or from within your development environment.

## Technologies 
- Spring Boot
- JUnit5
- Mockito
- Lombok
- Gradle
- WebClient
