package edu.bsu.cs222;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MovieResult {

    private final StringProperty movieName;
    private final StringProperty averageRating;
    private final StringProperty movieDescription;

    public MovieResult(String movieName, String averageRating, String movieDescription) {

        this.movieName = new SimpleStringProperty(movieName);
        this.averageRating = new SimpleStringProperty(averageRating);
        this.movieDescription = new SimpleStringProperty(movieDescription);
    }

    @SuppressWarnings("unused")
    public String getMovieName(){

        return movieName.get();
    }

    public StringProperty movieNameProperty() {

        return movieName;
    }

    @SuppressWarnings("unused")
    public String getAverageRating() {

        return averageRating.get();
    }

    public StringProperty averageRatingProperty() {

        return averageRating;
    }

    @SuppressWarnings("unused")
    public String getMovieDescription() {

        return movieDescription.get();
    }

    public StringProperty movieDescriptionProperty() {

        return movieDescription;
    }
}

