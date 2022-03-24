package experiment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class KRandomExperiment
{
    public static void generateData(int sizeBegin, int sizeEnd, int sizeJump, 
            int kBegin, int kEnd, int kJump, String filename)
    {
        try
        {
            String filePath = System.getProperty("user.dir") + "\\" + filename + ".txt";
            File outputFile = new File(filePath);
            FileWriter writer = new FileWriter(filePath, false);
            
            writer.write("dupa");
            writer.close();
        } catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
