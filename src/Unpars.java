
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class Unpars {
    public static Query unpars(String inputline)  {
        //теги которые нам нужны
        String TagQuery = "query";
        String TagSearch = "search";
        String TagTitle = "title";
        String TagPageID = "pageid";

        try {
            //Создаем jsonObject в который добавляем считаный json архив
            JsonObject jsonObject = (JsonObject) JsonParser.parseString(inputline);
            //Создаем queryObject в который добавляем обьект query из архива
            JsonObject queryObject = (JsonObject) jsonObject.get(TagQuery);
            //Создаем searchArray в который добавляем список из обьекта query
            JsonArray searchArray = (JsonArray) queryObject.get(TagSearch);
            //Создаем класс
            Query query = new Query();
            //перебираем обьекты внутри списка
            for (Object item: searchArray) {
                //создаем буфферный обьект
                JsonObject searchObject = (JsonObject) item;
                //из буфера достаем нужные нам данные
                Object pageTitle = (Object) searchObject.get(TagTitle);
                Object pageID = (Object) searchObject.get(TagPageID);
                //добавляем их в класс query

                query.getPageid(pageID.toString());
                query.getTitle(pageTitle.toString());

            }
            //возвращаем класс
            return (query);
        } catch (Exception _) {
            return null;
        }
    }
}

