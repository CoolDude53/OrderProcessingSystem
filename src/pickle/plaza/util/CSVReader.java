package pickle.plaza.util;

import pickle.plaza.BeginProcessing;
import pickle.plaza.orders.Order;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CSVReader
{
    private static final char DEFAULT_SEPARATOR = ',';
    private static final char DEFAULT_QUOTE = '"';

    public CSVReader(BeginProcessing processor, String fileName)
    {
        try
        {
            File file = new File(fileName);
            if (file.exists())
            {
                Scanner scanner = new Scanner(file);
                if (scanner.hasNextLine())
                    scanner.nextLine();

                while (scanner.hasNext())
                {
                    List<String> line = parseLine(scanner.nextLine());
                    if (!line.get(0).equals(""))
                    {
                        int styleNumber = Integer.parseInt(line.get(0));

                        Order order = new Order(line.get(1));

                        // fill order sizes
                        for (int count = 0; count < order.getSizes().length; count++)
                            order.getSizes()[count] = Integer.parseInt(line.get(2 + count));

                        processor.getStyleMap().putIfAbsent(styleNumber, new ArrayList<>());
                        processor.getStyleMap().get(styleNumber).add(order);
                    }

                }

                scanner.close();
                file.delete();
            }
        } catch (IOException e)
        {
            System.out.println("Error while reading CSV!");
            e.printStackTrace();
            System.exit(0);
        }
    }

    public List<String> parseLine(String cvsLine)
    {
        return parseLine(cvsLine, DEFAULT_SEPARATOR, DEFAULT_QUOTE);
    }

    public List<String> parseLine(String cvsLine, char separators)
    {
        return parseLine(cvsLine, separators, DEFAULT_QUOTE);
    }

    public List<String> parseLine(String cvsLine, char separators, char customQuote)
    {

        List<String> result = new ArrayList<>();

        //if empty, return!
        if (cvsLine == null && cvsLine.isEmpty())
            return result;

        if (customQuote == ' ')
            customQuote = DEFAULT_QUOTE;

        if (separators == ' ')
            separators = DEFAULT_SEPARATOR;

        StringBuffer curVal = new StringBuffer();
        boolean inQuotes = false;
        boolean startCollectChar = false;
        boolean doubleQuotesInColumn = false;

        char[] chars = cvsLine.toCharArray();

        for (char ch : chars)
        {

            if (inQuotes)
            {
                startCollectChar = true;
                if (ch == customQuote)
                {
                    inQuotes = false;
                    doubleQuotesInColumn = false;
                }
                else
                {
                    //Fixed : allow "" in custom quote enclosed
                    if (ch == '\"')
                    {
                        if (!doubleQuotesInColumn)
                        {
                            curVal.append(ch);
                            doubleQuotesInColumn = true;
                        }
                    }
                    else
                        curVal.append(ch);
                }
            }
            else
            {
                if (ch == customQuote)
                {
                    inQuotes = true;

                    //Fixed : allow "" in empty quote enclosed
                    if (chars[0] != '"' && customQuote == '\"')
                        curVal.append('"');

                    //double quotes in column will hit this!
                    if (startCollectChar)
                        curVal.append('"');
                }
                else if (ch == separators)
                {

                    result.add(curVal.toString());

                    curVal = new StringBuffer();
                    startCollectChar = false;

                }
                else if (ch == '\n')
                    break;
                else if (ch != '\r')
                    curVal.append(ch);
            }
        }

        result.add(curVal.toString());
        return result;
    }
}
