package pickle.plaza.util;

import pickle.plaza.orders.Order;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class CSVWriter
{
    //Delimiter used in CSV file
    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";

    //CSV file header
    private static final String FILE_HEADER = "style_id,name,2T,3T,4T,4,5,6,6x,7,8,10,12,14,16";

    private FileWriter fileWriter;
    private boolean exists;

    public CSVWriter(String fileName)
    {
        try
        {
            File file = new File(fileName);
            exists = file.exists();
            this.fileWriter = new FileWriter(file, true);
        } catch (IOException e)
        {
            System.out.println("Error in while saving to CSV!");
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void write(Map<Integer, ArrayList<Order>> styleMap)
    {
        try
        {
            //Write the CSV file header
            if (!exists)
                fileWriter.append(FILE_HEADER);

            //Add a new line separator after the header
            fileWriter.append(NEW_LINE_SEPARATOR);

            //Write a new student object list to the CSV file
            for (Map.Entry<Integer, ArrayList<Order>> entry : styleMap.entrySet())
            {
                for (Order order : entry.getValue())
                {
                    fileWriter.append(entry.getKey().toString());
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(order.getStoreName());
                    fileWriter.append(COMMA_DELIMITER);

                    for (int size : order.getSizes())
                    {
                        fileWriter.append(String.valueOf(size));
                        fileWriter.append(COMMA_DELIMITER);
                    }

                    fileWriter.append(NEW_LINE_SEPARATOR);
                }
            }
        } catch (Exception e)
        {
            System.out.println("Error in while saving to CSV!");
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void close()
    {
        try
        {
            fileWriter.flush();
            fileWriter.close();

            System.out.println("CSV Saved!");
        } catch (IOException e)
        {
            System.out.println("Error while flushing/closing fileWriter !!!");
            e.printStackTrace();
            System.exit(0);
        }
    }
}
