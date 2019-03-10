package entities;

import interfaces.Saveable;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;



public class Saver implements Saveable {

    @Override
    public void save(Map.Entry <String, List<String>> descriptionForOne, int count) throws IOException {
            StringBuilder fileName = new StringBuilder();
            fileName.append("G:/JavaRush/").append("file").append(count).append(".txt");

            File file = new File(fileName.toString());
            if (!file.exists()) {
                file.createNewFile();
            }
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file.getAbsoluteFile())))) {
                for (String item : descriptionForOne.getValue()) {
                    writer.write(item);
                }
            }
        }

    }


