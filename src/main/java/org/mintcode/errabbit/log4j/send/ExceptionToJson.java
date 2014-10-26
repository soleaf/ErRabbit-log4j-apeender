package org.mintcode.errabbit.log4j.send;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * StackTrace to Json
 * Created by soleaf on 2014. 10. 26..
 */
public class ExceptionToJson {

    private static final String DATA_KEY_class = "e_class";
    private static final String DATA_KEY_message = "basic_msg";
    private static final String DATA_KEY_cause = "e_cause";
    private static final String DATA_KEY_trace = "e_trace";

    /**
     * Exception to Json String Parameter Map
     *
     * @param exception
     * @return
     */
    public static Map<String, String> make(Exception exception) {
        try {

            Map<String, String> data = new HashMap<String, String>();
            Gson gson = new Gson();

            if (exception.getClass() != null)
                data.put(DATA_KEY_class, exception.getClass().getName());
            if (exception.getMessage() != null)
                data.put(DATA_KEY_message, exception.getMessage());
            if (exception.getCause() != null)
                data.put(DATA_KEY_cause, exception.getCause().toString());
            if (exception.getStackTrace() != null)
                data.put(DATA_KEY_trace, gson.toJson(exception.getStackTrace()));

            System.out.println(data);
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
