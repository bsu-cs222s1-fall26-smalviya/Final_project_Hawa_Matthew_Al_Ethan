package edu.bsu.cs222;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.jetbrains.annotations.NotNull;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

public class MovieFinderController  {

    @FXML private TextField searchField;
    @FXML private TableView<MovieResult> movieTable;
    @FXML private Button resetButton;
    @FXML private TableColumn<MovieResult, String> movieNameColumn;
    @FXML private TableColumn<MovieResult, String> averageRatingColumn;
    @FXML private TableColumn<MovieResult, Void> moreColumn;
    @FXML private Label statusLabel;
    @FXML private TableColumn<MovieResult, String> movieDescriptionColumn;

    private final ObservableList<MovieResult> searchHistory = FXCollections.observableArrayList();

    private final MovieSearchService searchService = new MovieSearchService();
    private final MovieDescriptionAPI descriptionExtractor = new MovieDescriptionAPI();
    private final AverageRatingCalculator ratingCalculator = new AverageRatingCalculator();

    @FXML
    private void handleSearch() {
        String rawInput = searchField.getText();

        try {

            String cleanMovieName = searchService.validateMovieTitle(rawInput);

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

            String formattedDescription;
            try {
                formattedDescription = descriptionExtractor.gatherMovieDescription(cleanMovieName);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            searchHistory.add(new MovieResult(cleanMovieName, formattedRating, formattedDescription));

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

        movieDescriptionColumn.setCellValueFactory(cellData ->
                cellData.getValue().movieDescriptionProperty());

        //Custom cell factory that handles text wrapping and formatting for the description
        movieDescriptionColumn.setCellFactory(col -> {
            TableCell<MovieResult, String> cell = new TableCell<>();
            Text text = new Text();

            text.setStyle("-fx-fill: white; -fx-font-size: 13px;");
            text.wrappingWidthProperty().bind(col.widthProperty().subtract(15));

            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            cell.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

            cell.itemProperty().addListener((observable, oldValue, newValue) -> text.setText(newValue));

            return cell;

        });


        moreColumn.setCellFactory(column -> new TableCell<>() {

            private final Button moreButton = new Button("More");

            {
                moreButton.setOnAction(event -> {
                    MovieResult movie = getTableView().getItems().get(getIndex());

                    MovieDetailsView detailsView = new MovieDetailsView();
                    detailsView.show(movie.movieNameProperty().get());
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

        //Tells the TableView to dynamically wrap each row to the necessary height
        movieTable.setFixedCellSize(Region.USE_COMPUTED_SIZE);

        movieTable.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
    }

    private void showMovieRatings(MovieResult movie) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Movie Information");
        alert.setHeaderText(movie.getMovieName());

        alert.setContentText(
                "Average Rating: " + movie.getAverageRating()
        );

        alert.showAndWait();
    }
}
