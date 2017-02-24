package pickle.plaza;

import pickle.plaza.orders.Order;
import pickle.plaza.orders.ProcessOrders;
import pickle.plaza.util.CSVReader;
import pickle.plaza.util.CSVWriter;

import java.util.ArrayList;
import java.util.SortedMap;
import java.util.TreeMap;

public class BeginProcessing
{
    private static final String DATA_FILE = "orderData.csv";

    private SortedMap<Integer, ArrayList<Order>> styleMap = new TreeMap<>();
    private CSVWriter writer;

    public static void main(String args[])
    {
        BeginProcessing processor = new BeginProcessing();
        new CSVReader(processor, DATA_FILE);
        processor.setWriter(new CSVWriter(DATA_FILE));
        new ProcessOrders(processor);
    }

    public void saveMap()
    {
        writer.write(styleMap);
    }

    public void close()
    {
        writer.close();
    }

    public SortedMap<Integer, ArrayList<Order>> getStyleMap()
    {
        return styleMap;
    }

    public void setWriter(CSVWriter writer)
    {
        this.writer = writer;
    }
}

