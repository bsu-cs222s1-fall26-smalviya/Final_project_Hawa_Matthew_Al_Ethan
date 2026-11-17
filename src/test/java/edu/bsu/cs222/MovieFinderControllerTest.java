package edu.bsu.cs222;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.Objects;

@ExtendWith(ApplicationExtension.class)
public class MovieFinderControllerTest {

    @Start
    @SuppressWarnings("unused")
    public void start(Stage stage) throws Exception {

        URL fxmlLocation = Objects.requireNonNull(
                getClass().getResource("/movieFinderUI.fxml"),
                "Cannot find /movieFinderUI.fxml resource!"
        );

        Parent root = FXMLLoader.load(fxmlLocation);
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    public void testEmptySearchDisplaysError(FxRobot robot) {

        robot.clickOn("#searchButton");

        FxAssert.verifyThat("#statusLabel", LabeledMatchers.hasText("Please enter a movie title."));
    }


    @Test
    public void testUserTypesMovieAndSearches(FxRobot robot) {
        robot.clickOn("#searchButton");
        robot.sleep(1000);

        robot.interact(() -> {
            TextField searchField = robot.lookup("#searchField")
                    .queryAs(TextField.class);
            searchField.setText("Inception");
        });

        robot.interact(() -> {
            Button searchButton = robot.lookup("#searchButton")
                    .queryAs(Button.class);
            searchButton.fire();
        });

        FxAssert.verifyThat(
                "#statusLabel",
                LabeledMatchers.hasText("Fetching ratings for 'Inception'...")
        );
    }

}
