import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.apache.commons.io.IOUtils;


class ConsoleInput {
    Scanner sc = new Scanner(System.in);

    String getSearchRequest() {
        System.out.print("Введите поисковой запрос: ");
        return this.sc.nextLine();
    }

    int getPageId(Query pages) {
        for (int i = 0; i < pages.title.size(); i++) {
            System.out.printf("%d: %s\n", i + 1, pages.title.get(i));
        }
        int indexOfTitle;
        do {
            System.out.println("Выбирите номер страницы");
            indexOfTitle = this.sc.nextInt() - 1;
        } while (indexOfTitle >= pages.pageid.size());
        return Integer.parseInt(pages.pageid.get(indexOfTitle));
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

class OpenInBrowser {
    void wikipedia(int pageId) {
        String url = String.format("https://ru.wikipedia.org/w/index.php?curid=%d", pageId);
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
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


        Query pages = Unpars.unpars(jsonContent);

        assert pages != null;
        int pageId = cip.getPageId(pages);
        cip.close();

        OpenInBrowser oib = new OpenInBrowser();
        oib.wikipedia(pageId);

    }
}