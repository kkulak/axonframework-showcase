package knbit.events.bc.announcement.iietboard;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import knbit.events.bc.announcement.Announcement;
import knbit.events.bc.announcement.AnnouncementException;
import knbit.events.bc.announcement.Publisher;

import java.io.IOException;

/**
 * Created by novy on 03.04.15.
 */
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

        private static final String POST_FORM_NAME = "postform";
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
            final HtmlForm postForm = postingPage.getFormByName(POST_FORM_NAME);
            postForm.getInputByName(SUBJECT_INPUT_NAME).setValueAttribute(announcement.title());
            postForm.getTextAreaByName(MESSAGE_INPUT_NAME).setText(announcement.content());
            final HtmlSubmitInput sendPostButton = postForm.getInputByName(SUBMIT_BUTTON_NAME);

            // http://stackoverflow.com/questions/8513134/i-cant-create-thread-on-phpbb3-forum/11713867#11713867
            try {
                Thread.sleep(DELAY_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sendPostButton.click();
        }

    }
}