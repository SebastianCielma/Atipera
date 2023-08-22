package pl.sebastiancielma.Atipera.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.web.client.RestTemplate;
import pl.sebastiancielma.Atipera.model.OwnerInfo;
import pl.sebastiancielma.Atipera.model.RepositoryInfo;
import pl.sebastiancielma.Atipera.service.GitHubService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


class GitHubServiceTest {


    @Mock
    private RestTemplate restTemplate;

    private GitHubService gitHubService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        gitHubService = new GitHubService(restTemplate);
    }

    @Test
    void testGetNonForkRepositories() {
        String username = "testuser";
        String repositoriesUrl = "https://api.github.com/users/" + username + "/repos";

        RepositoryInfo[] response = {
                new RepositoryInfo("repo1", false, new OwnerInfo("owner1")),
                new RepositoryInfo("repo2", true, new OwnerInfo("owner2"))
        };

        when(restTemplate.getForObject(repositoriesUrl, RepositoryInfo[].class)).thenReturn(response);

        List<RepositoryInfo> repositories = gitHubService.getNonForkRepositories(username);

        assertEquals(1, repositories.size());
        assertEquals("repo1", repositories.get(0).getName());
        assertFalse(repositories.get(0).isFork());
        assertEquals("owner1", repositories.get(0).getOwnerInfo().getLogin());

        verify(restTemplate, times(1)).getForObject(repositoriesUrl, RepositoryInfo[].class);
        verifyNoMoreInteractions(restTemplate);
    }
}
