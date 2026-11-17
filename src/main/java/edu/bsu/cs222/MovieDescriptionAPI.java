package edu.bsu.cs222;

public class MovieDescriptionAPI {

    public String gatherMovieDescription(String movieTitle) throws Exception {

        MovieRatingAPI getMovieInformation = new MovieRatingAPI();
        String movieInformation = getMovieInformation.getMovie(movieTitle);

        String key = "\"Plot\":\"";

        int descriptionStart = movieInformation.indexOf((key));

        if (descriptionStart == -1) {
            return "N/A";
        }

        descriptionStart += key.length();

        int descriptionEnd = movieInformation.indexOf("\"", descriptionStart);

        return movieInformation.substring(descriptionStart, descriptionEnd);
    }
}
