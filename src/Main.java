import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.apache.commons.io.IOUtils;

class ConsoleInput {
    Scanner sc = new Scanner(System.in);

    String getSearchRequest() {
        System.out.print("Введите поисковой запрос: ");
        String wikiRequest = this.sc.nextLine();
        return wikiRequest;
    }

    int getPageId(Query pages) {
        for (int i = 0; i < pages.title.size(); i++) {
            System.out.println(String.format("%d: %s", i + 1, pages.title.get(i)));
        }
        int indexOfTitle;
        do {
            System.out.println("Выбирите номер страницы");
            indexOfTitle = this.sc.nextInt() - 1;
        } while (indexOfTitle >= pages.pageid.size());
        return Integer.valueOf(pages.pageid.get(indexOfTitle));
    }

    void close() {
        this.sc.close();
    }
}

class WikiApi {
    String getJson(String searchRequest) {
        searchRequest = URLEncoder.encode(searchRequest, StandardCharsets.UTF_8);
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

        WikiApi wAPI = new WikiApi();
        String jsonContent = wAPI.getJson(searchRequest);

        Unpars unpars = new Unpars();
        Query pages;
        try {
            pages = unpars.main(jsonContent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int pageId = cip.getPageId(pages);
        cip.close();
    }
}