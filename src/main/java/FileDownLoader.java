import entities.MyParser;
import entities.Task;
import lombok.extern.log4j.Log4j;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Log4j
public class FileDownLoader {
    public static void main(String[] args)  {
        int count = 1;
        List<String> links = new ArrayList<>();

        final Properties propURL = new Properties();
        try (InputStream resourceAsStream = FileDownLoader.class.getResourceAsStream(
                "/Properties.properties")) {
            propURL.load(resourceAsStream);
            for (int i =0; i< propURL.size(); i++){
                links.add(propURL.getProperty("link"+(i+1)));

            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        MyParser parser = new MyParser();
        ConcurrentHashMap<String, List<String>> results = parser.doInBackground(links);

        ExecutorService executor = Executors.newCachedThreadPool();

        for (Map.Entry<String, List<String>> entry: results.entrySet()){
        Task myTask = new Task(entry,count);
        executor.execute(myTask);
        count++;
        }

        executor.shutdown();

    }
}



