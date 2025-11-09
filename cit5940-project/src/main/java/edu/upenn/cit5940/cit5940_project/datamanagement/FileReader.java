package edu.upenn.cit5940.cit5940_project.datamanagement;

import java.util.*;
import edu.upenn.cit5940.cit5940_project.common.dto.*;

public interface FileReader {
	List<Article> read(String filepath);
}
