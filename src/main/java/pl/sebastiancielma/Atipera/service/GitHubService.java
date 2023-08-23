package pl.sebastiancielma.Atipera.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pl.sebastiancielma.Atipera.model.BranchInfo;
import pl.sebastiancielma.Atipera.model.RepositoryInfo;
import java.util.ArrayList;
import java.util.List;

@Service
public class GitHubService {

    private final WebClient webClient;

    public GitHubService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.github.com").build();
    }

    public List<RepositoryInfo> getNonForkRepositoriesWithBranchInfo(String username) {
        List<RepositoryInfo> repositories = getNonForkRepositories(username);
        List<RepositoryInfo> repositoriesWithBranches = new ArrayList<>();

        for (RepositoryInfo repo : repositories) {
            List<BranchInfo> branches = getBranchesForRepository(username, repo.name());
            repositoriesWithBranches.add(new RepositoryInfo(repo.name(), repo.fork(), repo.ownerInfo(), branches));
        }

        return repositoriesWithBranches;
    }

    private List<RepositoryInfo> getNonForkRepositories(String username) {
        return webClient.get()
                .uri("/users/{username}/repos", username)
                .retrieve()
                .bodyToFlux(RepositoryInfo.class)
                .filter(RepositoryInfo::fork)
                .collectList()
                .block();
    }

    private List<BranchInfo> getBranchesForRepository(String username, String repoName) {
        return webClient.get()
                .uri("/repos/{username}/{repoName}/branches", username, repoName)
                .retrieve()
                .bodyToFlux(BranchInfo.class)
                .collectList()
                .block();
    }
}
