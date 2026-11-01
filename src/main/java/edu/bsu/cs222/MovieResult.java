package edu.bsu.cs222;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MovieResult {

    private final StringProperty movieName;
    private final StringProperty averageRating;

    public MovieResult(String movieName, String averageRating) {

        this.movieName = new SimpleStringProperty(movieName);
        this.averageRating = new SimpleStringProperty(averageRating);
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

}
