package pl.sebastiancielma.Atipera.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import pl.sebastiancielma.Atipera.exception.ErrorResponse;
import pl.sebastiancielma.Atipera.exception.UserNotFound;
import pl.sebastiancielma.Atipera.model.RepositoryInfo;
import pl.sebastiancielma.Atipera.service.GitHubService;

import java.util.List;

@RestController
public class GitHubController {

    @Autowired
    private GitHubService gitHubService;

    @GetMapping(value = "/repositories/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRepositories(
            @PathVariable String username,
            @RequestHeader(value = "Accept", defaultValue = "application/json") String acceptHeader
    ) {
        List<RepositoryInfo> repositories = gitHubService.getNonForkRepositories(username);
        if (repositories.isEmpty()) {
            throw new UserNotFound("User not found");
        }
        return ResponseEntity.ok(repositories);
    }

    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFound ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMediaTypeNotAcceptableException() {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(), "Requested media type not acceptable");
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(errorResponse);
    }
}
