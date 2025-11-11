package edu.upenn.cit5940.cit5940_project.datamanagement;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.nio.file.Files;
import java.util.List;

import org.junit.jupiter.api.Test;

import edu.upenn.cit5940.cit5940_project.common.dto.Article;

class CsvFileArticleReaderTest {

    @Test
    void testReadValidAndInvalidRecords() throws IOException {
        // Create a temporary CSV file for testing
        File tempFile = Files.createTempFile("test_articles", ".csv").toFile();
        tempFile.deleteOnExit();

        // CSV content: first line valid, second missing title, third valid
        String csvContent = 
                "id1,2025-01-01,author1,category1,title1,body1,,,,,,,,,,\n" +
                "id2,2025-01-02,author2,category2,,body2,,,,,,,,,,\n" +
                "id3,2025-01-03,author3,category3,title3,body3,,,,,,,,,,";
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write(csvContent);
        }

        CsvFileArticleReader reader = new CsvFileArticleReader();
        List<Article> articles = reader.read(tempFile.getAbsolutePath());

        // Only 2 valid articles should be read
        assertEquals(2, articles.size());

        // Check first article
        Article a1 = articles.get(0);
        assertEquals("id1", a1.getUri());
        assertEquals("title1", a1.getTitle());

        // Check second article (third row in CSV)
        Article a2 = articles.get(1);
        assertEquals("id3", a2.getUri());
        assertEquals("title3", a2.getTitle());
    }

    @Test
    void testEmptyFile() throws IOException {
        File tempFile = Files.createTempFile("empty_articles", ".csv").toFile();
        tempFile.deleteOnExit();

        CsvFileArticleReader reader = new CsvFileArticleReader();
        List<Article> articles = reader.read(tempFile.getAbsolutePath());

        assertTrue(articles.isEmpty(), "Articles list should be empty for empty CSV file");
    }
}
