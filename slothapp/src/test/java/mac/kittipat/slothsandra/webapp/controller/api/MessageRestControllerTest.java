package mac.kittipat.slothsandra.webapp.controller.api;

import mac.kittipat.slothsandra.core.bean.MessageBean;
import mac.kittipat.slothsandra.core.dao.MessageByChannelDao;
import mac.kittipat.slothsandra.core.dao.MessageByUserChannelDao;
import mac.kittipat.slothsandra.core.dao.MessageDao;
import mac.kittipat.slothsandra.core.dao.YearByChannelDao;
import mac.kittipat.slothsandra.core.model.YearByChannel;
import mac.kittipat.slothsandra.core.model.YearByChannelKey;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
    private YearByChannelDao yearByChannelDao;

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

        List<YearByChannel> yearByChannelList = new ArrayList<>();

        YearByChannelKey yearByChannelKey1 = new YearByChannelKey();
        yearByChannelKey1.setChannelName("general");
        yearByChannelKey1.setYear(2015);
        YearByChannel yearByChannel1 = new YearByChannel();
        yearByChannel1.setYearByChannelKey(yearByChannelKey1);

        YearByChannelKey yearByChannelKey2 = new YearByChannelKey();
        yearByChannelKey2.setChannelName("general");
        yearByChannelKey2.setYear(2014);
        YearByChannel yearByChannel2 = new YearByChannel();
        yearByChannel2.setYearByChannelKey(yearByChannelKey2);

        yearByChannelList.add(yearByChannel1);
        yearByChannelList.add(yearByChannel2);

        when(yearByChannelDao.findByChannel(anyString())).thenReturn(yearByChannelList);

        MessageBean messageBean1 = new MessageBean();
        messageBean1.setMessage("333");
        MessageBean messageBean2 = new MessageBean();
        messageBean2.setMessage("222");
        MessageBean messageBean3 = new MessageBean();
        messageBean3.setMessage("111");

        List<MessageBean> messageBeenList1 = new ArrayList<>();
        messageBeenList1.add(messageBean1);
        messageBeenList1.add(messageBean2);

        List<MessageBean> messageBeenList2 = new ArrayList<>();
        messageBeenList2.add(messageBean3);

        when(messageByChannelDao.findMessageByChannel(anyString(), anyInt(), anyLong(), anyInt()))
                .thenReturn(messageBeenList1, messageBeenList2);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/channels/general/messages")
                .param("limit", "3"))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindByUserChannel() throws Exception {

        List<YearByChannel> yearByChannelList = new ArrayList<>();

        YearByChannelKey yearByChannelKey1 = new YearByChannelKey();
        yearByChannelKey1.setChannelName("general");
        yearByChannelKey1.setYear(2015);
        YearByChannel yearByChannel1 = new YearByChannel();
        yearByChannel1.setYearByChannelKey(yearByChannelKey1);

        YearByChannelKey yearByChannelKey2 = new YearByChannelKey();
        yearByChannelKey2.setChannelName("general");
        yearByChannelKey2.setYear(2014);
        YearByChannel yearByChannel2 = new YearByChannel();
        yearByChannel2.setYearByChannelKey(yearByChannelKey2);

        yearByChannelList.add(yearByChannel1);
        yearByChannelList.add(yearByChannel2);

        when(yearByChannelDao.findByChannel(anyString())).thenReturn(yearByChannelList);

        MessageBean messageBean1 = new MessageBean();
        messageBean1.setMessage("333");
        MessageBean messageBean2 = new MessageBean();
        messageBean2.setMessage("222");
        MessageBean messageBean3 = new MessageBean();
        messageBean3.setMessage("111");

        List<MessageBean> messageBeenList1 = new ArrayList<>();
        messageBeenList1.add(messageBean1);
        messageBeenList1.add(messageBean2);

        List<MessageBean> messageBeenList2 = new ArrayList<>();
        messageBeenList2.add(messageBean3);

        when(messageByUserChannelDao.findMessageByUserChannel(anyString(), anyString(), anyInt(), anyLong(), anyInt()))
                .thenReturn(messageBeenList1, messageBeenList2);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/channels/general/users/mac/messages"))
                .andExpect(status().isOk());
    }
}