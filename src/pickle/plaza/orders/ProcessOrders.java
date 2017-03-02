package pickle.plaza.orders;

import pickle.plaza.BeginProcessing;

import java.util.ArrayList;
import java.util.Scanner;

public class ProcessOrders
{
    public static String[] sizeNames = {"2T", "3T", "4T", "4", "5", "6", "6x", "7", "8", "10", "12", "14", "16"};
    private BeginProcessing processor;
    private Scanner scanner;

    public ProcessOrders(BeginProcessing processor)
    {
        this.processor = processor;
        this.scanner = new Scanner(System.in);

        processOrder();
        processor.saveMap();

        processor.close();
    }

    private void processOrder()
    {
        // get style number
        ArrayList<Integer> styleNumbers = new ArrayList<>();
        System.out.print("Enter the style number(s) (-1 to exit): ");

        String line = scanner.nextLine();
        Scanner innerScanner = new Scanner(line);

        while (innerScanner.hasNext())
        {
            int styleNumber;

            try
            {
                styleNumber = Integer.parseInt(innerScanner.next());

                if (styleNumber == -1)
                {
                    processor.close();
                    System.exit(0);
                }

            } catch (NumberFormatException e)
            {
                System.out.println("Improper input! Please start over.");
                processOrder();
                return;
            }

            System.out.println(styleNumber);
            styleNumbers.add(styleNumber);
        }

        // get store name and create order
        System.out.print("Enter the store name: ");
        Order order = new Order(scanner.nextLine().trim());

        // get sizes
        System.out.print("Are there toddler size orders? (Y/N): ");
        if (processSizes(scanner, order, scanner.nextLine().equalsIgnoreCase("Y")))
        {
            //add order to map
            for (int number : styleNumbers)
            {
                processor.getStyleMap().putIfAbsent(number, new ArrayList<>());
                processor.getStyleMap().get(number).add(order);
            }

            System.out.println(processor.getStyleMap().size());

            System.out.print("Is there another product to be inputted? (Y/N): ");
            if (scanner.next().equalsIgnoreCase("Y"))
            {
                scanner.nextLine();
                processOrder();
            }
        }
    }

    private boolean processSizes(Scanner scanner, Order order, boolean toddler)
    {
        int count = 0;

        if (!toddler)
        {
            order.getSizes()[count++] = 0;
            order.getSizes()[count++] = 0;
            order.getSizes()[count++] = 0;
        }

        // fill order sizes
        for (; count < order.getSizes().length; count++)
        {
            System.out.print(sizeNames[count] + ": ");

            try
            {
                order.getSizes()[count] = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e)
            {
                System.out.println("Improper input! Please start over.");
                processOrder();
                return false;
            }
        }

        System.out.print("Is this information correct? (Y/N): ");

        if (!scanner.next().equalsIgnoreCase("Y"))
        {
            scanner.nextLine();
            processOrder();
            return false;
        }

        return true;
    }
}
