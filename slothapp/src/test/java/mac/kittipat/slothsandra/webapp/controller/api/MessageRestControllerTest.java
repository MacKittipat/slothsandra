package mac.kittipat.slothsandra.webapp.controller.api;

import mac.kittipat.slothsandra.core.dao.MessageByChannelDao;
import mac.kittipat.slothsandra.core.dao.MessageByUserChannelDao;
import mac.kittipat.slothsandra.core.dao.MessageDao;
import mac.kittipat.slothsandra.webapp.service.SlackMessageFormatter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class MessageRestControllerTest {

    @InjectMocks
    private MessageRestController messageRestController;

    @Mock
    private MessageDao messageDao;

    @Mock
    private MessageByChannelDao messageByChannelDao;

    @Mock
    private MessageByUserChannelDao messageByUserChannelDao;

    @Mock
    private SlackMessageFormatter slackMessageFormatter;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(messageRestController).build();
    }

    @Test
    public void testCreate() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/api/messages")
                .param("channelName", "general")
                .param("username", "mac")
                .param("message", "Hello!")
                .param("createdTime", "1451274445000"))
                .andExpect(status().isCreated());

        verify(messageDao).insert(anyString(), anyString(), anyString(), anyLong());
    }

    @Test
    public void testFindByChannel() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/channels/general/messages"))
                .andExpect(status().isOk());

        verify(messageByChannelDao).findMessageByChannel(anyString());
    }

    @Test
    public void testFindByUserChannel() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/channels/general/users/mac/messages"))
                .andExpect(status().isOk());

        verify(messageByUserChannelDao).findMessageByUserChannel(anyString(), anyString());
    }
}