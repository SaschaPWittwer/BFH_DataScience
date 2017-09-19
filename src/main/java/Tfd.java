import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;

public class Tfd {
    public static void main(String[] args){

        Map<String, Integer> dict = new HashMap<>();

        try
        {
            FileReader reader = new FileReader("C:\\temp\\Hamlet.txt");
            BufferedReader br = new BufferedReader(reader);

            String line = "";
            while((line = br.readLine()) != null){
                // Remove punctuation
                line = line.replaceAll("[.!?,;:]", "");
                // Split words
                String[] words = line.split(" ");

                for (String word : words){
                    if (word.isEmpty())
                        continue;

                    if (!dict.containsKey(word))
                        dict.put(word, 0);

                    Integer counter = dict.get(word);
                    dict.remove(word);
                    dict.put(word, counter + 1);
                }
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
        catch(Exception exc)
        {

        }
    }
}
