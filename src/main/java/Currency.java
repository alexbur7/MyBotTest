import netscape.javascript.JSObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;


public class Currency {
    public static String getCurrency(String message, Model model) throws IOException {
       // URL url = new URL("https://currate.ru/api/?get=rates&pairs="+message+"&key=b2bfb04866a1e181e583b96a896257ac");
      //  URL url = new URL("http://api.currencylayer.com/live?access_key=2ecf63e95f2024b4adb72864353f1cf3&format=1");
        URL url = new URL("https://www.cbr-xml-daily.ru/daily_json.js");
        Scanner scanner = new Scanner((InputStream) url.getContent());
        String result="";
        while (scanner.hasNext()){
            result+=scanner.nextLine();
        }
       /* JSONObject object = new JSONObject(result);
        model.setSource(object.getString("source"));
        JSONObject object1 = object.getJSONObject("quotes");
        model.setCourse(object1.getDouble(message));
        String enter=null;
        if (message.length()==6) {
            enter = "Source: "+model.getSource()+"\nCourse " + message + ": " + model.getCourse().toString();
        }*/
        String enter="";
        JSONObject object = new JSONObject(result);
        JSONObject valute = object.getJSONObject("Valute");
        JSONObject value = valute.getJSONObject(message);
        model.setCourse(value.getDouble("Value"));
        enter = "Course " + message + "RUB: " + model.getCourse().toString();
        return enter;
    }
}
