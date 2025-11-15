package edu.upenn.cit5940.cit5940_project.common.dto;

import java.util.*;
import java.time.LocalDate;

public class Article {

    private String uri;
    private LocalDate date;
    private String title;
    private String body;

    // The number of fields required to construct an Article.
    public static final int EXPECTED_FIELD_COUNT = 16;

    public Article(String[] csvRow) {
        if (csvRow == null || csvRow.length < EXPECTED_FIELD_COUNT) {
            throw new IllegalArgumentException("CSV row must contain " + EXPECTED_FIELD_COUNT + " fields for an Article.");
        }

        // Mapping: 0=uri, 1=date, 4=title, 5=body
        this.uri = csvRow[0];
        this.date = DateFormatter.formatDate(csvRow[1]);
        this.title = csvRow[4];
        this.body = csvRow[5];

        if (uri == null || uri.isBlank()) {
            throw new IllegalArgumentException("URI cannot be null or empty.");
        }
    }

    /*
    Getters and setters
     */
    public String getUri() {
        return uri;
    }
    public LocalDate getDate() {
        return date;
    }
    public String getTitle() {
        return title;
    }
    public String getBody() {
        return body;
    }

    public void setUri(String uri) {
        if (uri == null || uri.isBlank()) {
            throw new IllegalArgumentException("URI cannot be null or empty.");
        }
        this.uri = uri;
    }

    public void setDate(String date) {
        this.date = DateFormatter.formatDate(date);
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setBody(String body) {
        this.body = body;
    }
}
