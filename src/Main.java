import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.apache.commons.io.IOUtils;

class ConsoleInput {
    Scanner in = new Scanner(System.in);

    String getSearchRequest() {
        System.out.print("Введите поисковой запрос: ");
        String wikiRequest = this.in.nextLine();
        return wikiRequest;
    }

    void close() {
        this.in.close();
    }
}

class WikiApi {
    String getJson(String searchRequest) {
        try {
            searchRequest = URLEncoder.encode(searchRequest, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        String apiLink = String.format(
                "https://ru.wikipedia.org/w/api.php?action=query&list=search&utf8=&format=json&srsearch=\"%s\"",
                searchRequest
        );
        try {
            return IOUtils.toString(new URL(apiLink), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        ConsoleInput cip = new ConsoleInput();
        String searchRequest = cip.getSearchRequest();
        cip.close();

        WikiApi wAPI = new WikiApi();
        String jsonContent = wAPI.getJson(searchRequest);
        Unpars unpars = new Unpars();

        Query pages;
        try {
            pages = unpars.main(jsonContent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}