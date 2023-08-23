package pl.sebastiancielma.Atipera;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import pl.sebastiancielma.Atipera.exception.ErrorResponse;
import pl.sebastiancielma.Atipera.model.RepositoryInfo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class GitHubControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void getRepositories_ValidUser_ReturnsRepositories() {
        webTestClient
                .get()
                .uri("/repositories/{username}", "octocat") // możesz zmienić na innego użytkownika GitHuba
                .header("Accept", "application/json")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(RepositoryInfo.class);
    }

    @Test
    public void getRepositories_InvalidUser_Returns404() {
        webTestClient
                .get()
                .uri("/repositories/{username}", "invalidusername1234567890")
                .header("Accept", "application/json")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorResponse.class);
    }

    @Test
    public void getRepositories_WrongAcceptHeader_Returns406() {
        webTestClient
                .get()
                .uri("/repositories/{username}", "octocat")
                .header("Accept", "application/xml")
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody(ErrorResponse.class);
    }
}
