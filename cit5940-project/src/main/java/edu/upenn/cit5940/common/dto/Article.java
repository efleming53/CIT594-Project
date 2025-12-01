package edu.upenn.cit5940.common.dto;

import java.util.*;
import java.time.LocalDate;

// Article object, created from parsing of data file and gets loaded into each structure in DataRepository
public class Article {

    private String uri;
    private LocalDate date;
    private String title;
    private String body;

    public static final int EXPECTED_FIELD_COUNT = 16;

    public Article(String[] csvRow) {
    	
    	// validate size of csvRow
        if (csvRow == null || csvRow.length < EXPECTED_FIELD_COUNT) {
            throw new IllegalArgumentException("CSV row must contain " + EXPECTED_FIELD_COUNT + " fields for an Article.");
        }

        // Mapping: 0=uri, 1=date, 4=title, 5=body
        this.uri = csvRow[0];
        this.date = DateFormatter.formatLocalDate(csvRow[1]); // forces consistency in date format
        this.title = csvRow[4];
        this.body = csvRow[5];

        // make uri required field
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
        this.date = DateFormatter.formatLocalDate(date); // force consistency in date format
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setBody(String body) {
        this.body = body;
    }
}