package WordProcessing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;

public class WordProcessing {
    public static void main(String[] args) throws IOException {

        Map<String, Integer> dict = new HashMap<>();

        // Load stop words
        WordProcessing.GetStopWords();

        // Get book text
        String text = Files.readAllLines(Paths.get("..\\Hamlet.txt")).stream().collect(Collectors.joining());
        // Initialize tokenizer and format text to lower case
        StringTokenizer st = new StringTokenizer(text.toLowerCase());

        while (st.hasMoreTokens()){
            String token = st.nextToken();
            // Check for stop words
            if (Constants.stopWords.contains(token))
                continue;

            // Stem the word
            Stemmer stemmer = new Stemmer();
            stemmer.add(token.toCharArray(), token.length());
            stemmer.stem();

            String stemToken = new String(stemmer.getResultBuffer());

            if (!dict.containsKey(stemToken))
                dict.put(stemToken, 0);

            Integer counter = dict.get(stemToken);
            dict.remove(stemToken);
            dict.put(stemToken, counter + 1);
        }

        ArrayList<String> output = new ArrayList<>();
        output.add("Word;Count");
        dict.forEach((s, integer) -> {
            if (integer > 100)
                output.add(s + ";" + integer);
        });

        Path file = Paths.get("C:\\temp\\output.csv");
        Files.write(file, output, StandardOpenOption.CREATE_NEW);
    }

    private static void GetStopWords() throws IOException {
        for (String line : Files.readAllLines(Paths.get("..\\stopwords.txt"))){
            Constants.stopWords.add(line);
        }
    }
}
