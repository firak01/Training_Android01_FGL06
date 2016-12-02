package basic.zBasic.util.abstractList;
/**
 * @author Fritz Lindhauer
 *
 */
import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.os.Parcelable;


//Das hab ich Web Gefunden.
//ggfs. sinnvoll, wenn man eigene Parcable Objekte erstellt
//http://stackoverflow.com/questions/3323074/android-difference-between-parcelable-and-serializable
public class CollectionAndroidUtils {
    public static Bundle toBundle(Map<String, ? extends Parcelable> input) {
        Bundle output = new Bundle();
        for(String key : input.keySet()) {
            output.putParcelable(key, input.get(key));
        }
        return output;
    }
    
    public static <T extends Parcelable> Map<String, T> fromBundle(Bundle input, Class<T> c) {
        Map<String, T> output = new HashMap<String, T>();
        for(String key : input.keySet()) {
            output.put(key, c.cast(input.getParcelable(key)));
        }
        return output;
    }
}
