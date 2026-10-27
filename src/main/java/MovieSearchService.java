
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class MovieSearchService {

    public String prepareSearch(String movieTitle) {

        if (movieTitle == null || movieTitle.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Please enter a movie title."
            );
        }

        String title = movieTitle.trim();

        return URLEncoder.encode(
                title, StandardCharsets.UTF_8
        );
    }
}
