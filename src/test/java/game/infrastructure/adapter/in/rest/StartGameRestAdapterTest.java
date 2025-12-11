package game.infrastructure.adapter.in.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import game.application.in.StartGameUseCase;
import game.infrastructure.adapter.in.rest.mapper.StartGameRestMapper;
import game.infrastructure.adapter.in.rest.request.StartGameRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = StartGameRestAdapter.class)
public class StartGameRestAdapterTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StartGameUseCase startGameUseCase;

    @MockBean
    private StartGameRestMapper mapper;


    @Test
    void shouldStartGameAndReturn201() throws Exception {


        StartGameRequest request = new StartGameRequest("Pepito");

        mockMvc.perform(post("/games/start")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        then(startGameUseCase).should().startGame(
                argThat(cmd -> cmd.playerName().toString().contains("Pepito"))
        );
    }

}
