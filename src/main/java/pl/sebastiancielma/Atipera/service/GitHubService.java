package pl.sebastiancielma.Atipera.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.sebastiancielma.Atipera.model.RepositoryInfo;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GitHubService {

    private final RestTemplate restTemplate;

    public GitHubService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }



    public List<RepositoryInfo> getNonForkRepositories(String username) {
        String repositoriesUrl = "https://api.github.com/users/" + username + "/repos";
        RepositoryInfo[] response = restTemplate.getForObject(repositoriesUrl, RepositoryInfo[].class);

        return Arrays.stream(response)
                .filter(repositoryInfo -> !repositoryInfo.isFork())
                .collect(Collectors.toList());
    }
}
