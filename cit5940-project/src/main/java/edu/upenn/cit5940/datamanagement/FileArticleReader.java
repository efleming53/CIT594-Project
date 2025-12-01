package edu.upenn.cit5940.datamanagement;

import java.io.IOException;

// interface for readers that will read articles from file
public interface FileArticleReader {
	public void read() throws IOException;
}