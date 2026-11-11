package edu.bsu.cs222;

public class MovieDescription {
    private final String title;
    private final String description;

    public MovieDescription(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean hasDescription() {
        return description != null
                && !description.isBlank()
                && !description.equalsIgnoreCase("N/A");
    }
}

