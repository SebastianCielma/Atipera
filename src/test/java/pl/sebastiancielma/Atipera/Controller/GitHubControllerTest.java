package pl.sebastiancielma.Atipera.Controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pl.sebastiancielma.Atipera.controller.GitHubController;
import pl.sebastiancielma.Atipera.model.OwnerInfo;
import pl.sebastiancielma.Atipera.model.RepositoryInfo;
import pl.sebastiancielma.Atipera.service.GitHubService;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class GitHubControllerTest {

    @Mock
    private GitHubService gitHubService;

    @InjectMocks
    private GitHubController gitHubController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetRepositories() {
        String username = "testuser";

        RepositoryInfo repositoryInfo = new RepositoryInfo("repo1", false, new OwnerInfo("owner1"));
        when(gitHubService.getNonForkRepositories(username)).thenReturn(Collections.singletonList(repositoryInfo));

        ResponseEntity<?> responseEntity = gitHubController.getRepositories(username, "application/json");

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(Collections.singletonList(repositoryInfo), responseEntity.getBody());

        verify(gitHubService, times(1)).getNonForkRepositories(username);
        verifyNoMoreInteractions(gitHubService);
    }
}
