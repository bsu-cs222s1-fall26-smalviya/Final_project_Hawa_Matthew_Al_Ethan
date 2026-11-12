package edu.bsu.cs222;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.jetbrains.annotations.NotNull;

public class MovieFinderController  {

    @FXML private TextField searchField;
    @FXML private TableView<MovieResult> movieTable;
    @FXML private Button resetButton;
    @FXML private TableColumn<MovieResult, String> movieNameColumn;
    @FXML private TableColumn<MovieResult, String> averageRatingColumn;
    @FXML private TableColumn<MovieResult, Void> moreColumn;
    @FXML private Label statusLabel;

    private final ObservableList<MovieResult> searchHistory = FXCollections.observableArrayList();

    private final MovieSearchService searchService = new MovieSearchService();
    private final AverageRatingCalculator ratingCalculator = new AverageRatingCalculator();

    @FXML
    private void handleSearch() {
        String rawInput = searchField.getText();

        try {

            searchService.prepareSearch(rawInput);
            String cleanMovieName = rawInput.trim();

            statusLabel.setText("Fetching ratings for '" + cleanMovieName + "'...");

            //Run rating calculation in background to prevent UI freezing
            Task<Double> ratingTask = createRatingTask(cleanMovieName);

            new Thread(ratingTask).start();

        } catch (IllegalArgumentException e) {
            // Catches empty input errors from MovieSearchService
            statusLabel.setText(e.getMessage());
        }
    }

    @NotNull
    private Task<Double> createRatingTask(String cleanMovieName) {
        Task<Double> ratingTask = new Task<>() {
            @Override
            protected Double call() throws Exception {

                return ratingCalculator.getMovieAverage(cleanMovieName);
            }
        };

        ratingTask.setOnSucceeded(event -> {
            double averageScore = ratingTask.getValue();

            if (Double.isNaN(averageScore) || averageScore < 0) {

                statusLabel.setText("Movie not found: '" + cleanMovieName + ";");
                searchField.clear();
                return;
            }

            String formattedRating = String.format("%.1f / 100", averageScore);

            searchHistory.add(new MovieResult(cleanMovieName, formattedRating));

            if (!movieTable.isVisible()) {
                movieTable.setVisible(true);
                movieTable.setManaged(true);

                resetButton.setVisible(true);
                resetButton.setManaged(true);
            }

            searchField.clear();
            statusLabel.setText("Successfully loaded: " + cleanMovieName);
        });

        ratingTask.setOnFailed(event ->{

            statusLabel.setText("Movie not found or error fetching data.");
            searchField.clear();
        });

        return ratingTask;
    }

    @FXML
    private void handleReset() {
        searchHistory.clear();
        movieTable.setVisible(false);
        movieTable.setManaged(false);

        resetButton.setVisible(false);
        resetButton.setManaged(false);

        statusLabel.setText("Table reset.");
    }

    @FXML
    public void initialize() {

        movieNameColumn.setCellValueFactory(cellData ->
                cellData.getValue().movieNameProperty());

        averageRatingColumn.setCellValueFactory(cellData ->
                cellData.getValue().averageRatingProperty());

        moreColumn.setCellFactory(column -> new TableCell<>() {

            private final Button moreButton = new Button("More");

            {
                moreButton.setOnAction(event -> {
                    MovieResult movie = getTableView().getItems().get(getIndex());
                    showMovieRatings(movie);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(moreButton);
                }
            }
        });

        movieTable.setItems(searchHistory);

        movieTable.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
    }
}
