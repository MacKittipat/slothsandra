package mac.kittipat.slothsandra.webapp.controller.api;

import mac.kittipat.slothsandra.core.dao.ChannelDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class ChannelRestControllerTest {

    @InjectMocks
    private ChannelRestController channelRestController;

    @Mock
    private ChannelDao channelDao;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(channelRestController).build();
    }

    @Test
    public void testFindAll() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/channels"))
                .andExpect(status().isOk());

        verify(channelDao).findAll();
    }
}