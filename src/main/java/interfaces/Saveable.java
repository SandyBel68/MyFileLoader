package interfaces;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface Saveable {
    void save (Map.Entry <String,List<String>> descriptionForOne, int count) throws IOException;
}
