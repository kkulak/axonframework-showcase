package knbit.events.bc.announcement.publishers.iietboard;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import knbit.events.bc.announcement.Announcement;
import knbit.events.bc.announcement.AnnouncementException;
import knbit.events.bc.announcement.publishers.Publisher;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Optional;

/**
 * Created by novy on 03.04.15.
 */

@Slf4j
public class IIETBoardPublisher implements Publisher {

    private final BoardPublisherConfiguration configuration;
    private final WebClient webClient;

    private final LoginScrapper loginScrapper;
    private final PostScrapper postScrapper;


    public IIETBoardPublisher(BoardPublisherConfiguration configuration, WebClient webClient) {

        this.configuration = configuration;
        this.webClient = webClient;

        loginScrapper = new LoginScrapper(
                configuration.loginUrl(), webClient
        );

        postScrapper = new PostScrapper(
                configuration.postOnBoardUrl(), webClient
        );
    }

    @Override
    public void publish(Announcement announcement) throws AnnouncementException {

        try {

            loginScrapper.logIn(
                    configuration.boardUsername(),
                    configuration.boardPassword()
            );
            postScrapper.post(announcement);

        } catch (IOException cause) {
            log.error("CannotPostOnBoard", cause);
            throw new CannotPostOnBoardException(cause);
        }
    }


    private class LoginScrapper {

        private static final String LOGIN_FORM_ID = "new_student";
        private static final String USERNAME_INPUT_NAME = "student[username]";
        private static final String PASSWORD_INPUT_NAME = "student[password]";
        private static final String SUBMIT_BUTTON_NAME = "commit";

        private final String loginUrl;
        private final WebClient webClient;

        private LoginScrapper(String loginUrl, WebClient webClient) {
            this.loginUrl = loginUrl;
            this.webClient = webClient;
        }

        public void logIn(String username, String password) throws IOException {

            final HtmlPage loginPage = webClient.getPage(loginUrl);
            final HtmlForm loginForm = (HtmlForm) loginPage.getElementById(LOGIN_FORM_ID);
            loginForm.getInputByName(USERNAME_INPUT_NAME).setValueAttribute(username);
            loginForm.getInputByName(PASSWORD_INPUT_NAME).setValueAttribute(password);
            final HtmlSubmitInput button = loginForm.getInputByName(SUBMIT_BUTTON_NAME);
            button.click();
        }
    }

    private class PostScrapper {

        private static final String POST_FORM_ID = "postform";
        private static final String SUBJECT_INPUT_NAME = "subject";
        private static final String MESSAGE_INPUT_NAME = "message";
        private static final String SUBMIT_BUTTON_NAME = "post";
        private static final int DELAY_TIME = 3000;

        private final String postUrl;
        private final WebClient webClient;

        private PostScrapper(String postUrl, WebClient webClient) {
            this.postUrl = postUrl;
            this.webClient = webClient;
        }

        public void post(Announcement announcement) throws IOException {

            final HtmlPage postingPage = webClient.getPage(postUrl);
            final HtmlForm postForm = (HtmlForm)postingPage.getElementById(POST_FORM_ID);

            postForm.getInputByName(SUBJECT_INPUT_NAME).setValueAttribute(announcement.title());

            final String messageContent = determineMessageContent(announcement);
            postForm.getTextAreaByName(MESSAGE_INPUT_NAME).setText(messageContent);

            final HtmlSubmitInput sendPostButton = postForm.getInputByName(SUBMIT_BUTTON_NAME);
            sendPostButton.click();
        }

        private String determineMessageContent(Announcement announcement) {
            final Optional<String> possibleImageUrl = announcement.imageUrl();

            String messageContent;
            if (possibleImageUrl.isPresent()) {
                messageContent = withMessageUrlAppended(
                        announcement.content(), possibleImageUrl.get()
                );
            } else {
                messageContent = announcement.content();
            }

            return messageContent;
        }

        private String withMessageUrlAppended(String messageContent, String imageUrl) {
            return messageContent
                    + "\n\n"
                    + "[img]"
                    + imageUrl
                    + "[/img]";
        }

    }
}
