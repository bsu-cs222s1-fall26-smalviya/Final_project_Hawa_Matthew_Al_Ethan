package edu.bsu.cs222;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.concurrent.Task;

public class MovieDetailsView {

    public void show(String movieTitle) {

        Stage detailsStage = new Stage();

        Label titleLabel = new Label(movieTitle);
        titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");

        Label ratingsLabel = new Label("Loading ratings...");

        Button backButton = new Button("Back");

        backButton.setOnAction(event -> detailsStage.close());

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(
                titleLabel,
                ratingsLabel,
                backButton
        );

        layout.setStyle("-fx-background-color: #9370DB;");

        Scene scene = new Scene(layout, 600, 400);

        detailsStage.setScene(scene);
        detailsStage.setTitle("Movie Details");
        detailsStage.show();
        MovieRatingAPI api = new MovieRatingAPI();

        Task<String> ratingTask = new Task<>() {
            @Override
            protected String call() throws Exception {

                String imdb = api.getIMDbRating(movieTitle);
                String rottenTomatoes = api.getRottenTomatoesRating(movieTitle);
                String metacritic = api.getMetacriticRating(movieTitle);

                return "IMDb: " + imdb
                        + "\nRotten Tomatoes: " + rottenTomatoes
                        + "\nMetacritic: " + metacritic;
            }
        };

        ratingTask.setOnSucceeded(event -> ratingsLabel.setText(ratingTask.getValue()));

        ratingTask.setOnFailed(event -> ratingsLabel.setText("Could not load movie ratings."));

        Thread thread = new Thread(ratingTask);
        thread.setDaemon(true);
        thread.start();
    }
}