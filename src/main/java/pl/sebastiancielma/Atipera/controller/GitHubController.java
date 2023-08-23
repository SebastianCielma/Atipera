package pl.sebastiancielma.Atipera.controller;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import pl.sebastiancielma.Atipera.exception.HttpMediaTypeNotAcceptableException;
import pl.sebastiancielma.Atipera.exception.UserNotFound;
import pl.sebastiancielma.Atipera.model.RepositoryInfo;
import pl.sebastiancielma.Atipera.service.GitHubService;

import java.util.List;

@RestController
public class GitHubController {

    private final GitHubService gitHubService;

    public GitHubController(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    @SneakyThrows
    @GetMapping(value = "/repositories/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RepositoryInfo>> getRepositories(
            @PathVariable String username,
            @RequestHeader(value = "Accept", defaultValue = "application/json") String acceptHeader
    ) {
        if (!"application/json".equals(acceptHeader)) {
            throw new HttpMediaTypeNotAcceptableException("Only application/json is supported");
        }

        List<RepositoryInfo> repositories = gitHubService.getNonForkRepositoriesWithBranchInfo(username);

        if (repositories.isEmpty()) {
            throw new UserNotFound("User not found");
        }

        return ResponseEntity.ok(repositories);
    }
}
