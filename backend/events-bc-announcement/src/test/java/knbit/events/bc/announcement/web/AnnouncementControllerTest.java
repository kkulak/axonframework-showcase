package knbit.events.bc.announcement.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import knbit.events.bc.announcement.Announcement;
import knbit.events.bc.announcement.Publisher;
import knbit.events.bc.announcement.testcontext.TestContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by novy on 03.04.15.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {
        TestContext.class
})
public class AnnouncementControllerTest {

    private static final String URL_PATH = "/announcements";

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private Publisher publisherMock;

    @Autowired
    private AnnouncementController objectUnderTest;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();

        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {
        Mockito.reset(publisherMock);
    }

    @Test
    public void shouldDispatchRequestToPublisher() throws Exception {

        final AnnouncementDTO dto = dtoOf("title", "content");

        objectUnderTest.postAnnouncement(dto);

        verify(publisherMock, times(1)).publish(
                new Announcement(dto.getTitle(), dto.getContent())
        );


    }

    @Test
    public void shouldReturnHttpNoContentAfterSuccessfulPublishing() throws Exception {

        final AnnouncementDTO dto = dtoOf("title", "content");
        final String jsonString = asJsonString(dto);

        mockMvc
                .perform(
                        post(URL_PATH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonString)
                )
                .andExpect(
                        status().isNoContent()
                );
    }

    @Test
    public void shouldReturnInternalErrorWithProperMessageWhenCoordinatorFails() throws Exception {

        doThrow(new SomethingWentWrongException())
                .when(publisherMock)
                .publish(
                        any(Announcement.class)
                );
        final AnnouncementDTO dto = dtoOf("title", "content");
        final String jsonString = asJsonString(dto);


        mockMvc
                .perform(
                        post(URL_PATH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonString)
                )
                .andExpect(
                        status().isInternalServerError()
                )
                .andExpect(
                        jsonPath("$.error", is(SomethingWentWrongException.ERROR_MESSAGE))
                );
    }

    private AnnouncementDTO dtoOf(String title, String content) {
        final AnnouncementDTO dto = new AnnouncementDTO();
        dto.setTitle(title);
        dto.setContent(content);
        return dto;
    }

    private String asJsonString(AnnouncementDTO announcementDTO) throws Exception {
        final ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(announcementDTO);
    }

}