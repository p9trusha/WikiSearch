import java.util.List;
//Создаем наш класс
public class Query {
    public void putTitle(String string) {
        titles.add(string);
    }

    public void putPageId(String string) {
        pageIds.add(string);
    }

    final List<String> titles  = new java.util.ArrayList<>(List.of());
    final List<String> pageIds = new java.util.ArrayList<>(List.of());
}
