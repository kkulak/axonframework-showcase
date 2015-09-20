package knbit.annoucement.bc.publisher.iietboard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * Created by novy on 05.04.15.
 */

@Accessors(fluent = true)
@AllArgsConstructor
public class BoardPublisherConfiguration {

    private static final String POSTING_URL_SUBSTRING = "posting.php?mode=post&f=";

    @Getter
    private final String boardUsername;
    @Getter
    private final String boardPassword;
    @Getter
    private final String loginUrl;

    private final String boardUrl;
    private final String boardId;

    public String postOnBoardUrl() {
        return preparedBoardUrlPrefix() + POSTING_URL_SUBSTRING + boardId;
    }

    private String preparedBoardUrlPrefix() {
        return boardUrl.endsWith("/") ? boardUrl : boardUrl + "/";
    }
}
