package entities;

import lombok.extern.log4j.Log4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Log4j
public class MyParser {

    private Connection.Response response;
    private Document document;

    public ConcurrentHashMap<String, List<String>> doInBackground(List<String> initialURLS) {

        Map<String, String> headers = new HashMap<>();
        headers.put("Host", "docs.oracle.com");
        headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        headers.put("Accept-language", "en-US,en;q=0.9");
        headers.put("Connection", "keep-alive");
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.119 Safari/537.36");

        ConcurrentHashMap<String, List<String>> descpForAll = new ConcurrentHashMap<>();

        for (String url : initialURLS) {
            try {
                response = Jsoup.connect(url)
                        .method(Connection.Method.GET)
                        .headers(headers)
                        .ignoreContentType(true)
                        .timeout(10 * 1000)
                        .execute();

            } catch (IOException e) {
                log.error(e.getMessage());
            }

            if (response != null) {
                List<String> description = new ArrayList<>();
                try {
                    document = response.parse();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
                for (Element elem : document.getElementsByTag("p")) {
                    String str = elem.text();
                    if (str != null) description.add(str);
                }

                descpForAll.put(url, description);

            }
        }
        return descpForAll;
    }
}