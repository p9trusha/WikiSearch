import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;
import org.apache.commons.io.IOUtils;

// класс для работы с консолью
class ConsoleInput {
    Scanner sc = new Scanner(System.in);

    String getSearchRequest() {
        System.out.print("Введите поисковой запрос: ");
        return this.sc.nextLine();
    }

    String getPageId(Query pages) {
        for (int i = 0; i < pages.title.size(); i++) {
            System.out.printf("%d: %s\n", i + 1, pages.title.get(i));
        }
        int indexOfTitle;
        do {
            System.out.println("Выбирите номер страницы");
            indexOfTitle = this.sc.nextInt() - 1;
        } while (indexOfTitle >= pages.pageid.size());
        return (pages.pageid.get(indexOfTitle));
    }

    void close() {
        this.sc.close();
    }
}

// класс для работы с wiki api
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

// класс для открытия ссылки в браусере
class OpenInBrowser {
    void wikipedia(String pageId) {
        String url = String.format("https://ru.wikipedia.org/w/index.php?curid=%s", pageId);
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
        // получение запроса пользователя
        ConsoleInput cip = new ConsoleInput();
        String searchRequest = cip.getSearchRequest();

        // получение json
        WikiApi wAPI = new WikiApi();
        String jsonContent = wAPI.getJson(searchRequest);

        Query pages = Unpars.unpars(jsonContent);
        //Проверяем, есть ли данные в классе
        Objects.requireNonNull(pages);
        if (!pages.pageid.isEmpty()) {
            //Выбор страницы пользователем
            String pageId = cip.getPageId(pages);
            cip.close();
            //Открытые страницы
            OpenInBrowser oib = new OpenInBrowser();
            oib.wikipedia(pageId);
        } else {
           System.out.println("Информации не найдено");
        }

    }
}