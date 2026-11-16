package edu.bsu.cs222;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class MovieSearchService {


    public String prepareSearch(String movieTitle) {
        String title = validateMovieTitle(movieTitle);

        return URLEncoder.encode(
                title, StandardCharsets.UTF_8


        );
    }
public String validateMovieTitle(String movieTitle) {
            if (movieTitle == null || movieTitle.trim().isEmpty()) {
                throw new IllegalArgumentException(
                        "Please enter a movie title."
                );
            }

    return movieTitle.trim().replaceAll("\\s+", " ");
}
}
