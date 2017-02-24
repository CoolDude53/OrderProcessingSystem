package pickle.plaza.orders;

public class Order
{
    private String storeName;
    private int[] sizes = new int[13];

    public Order(String storeName)
    {
        this.storeName = storeName;
    }

    public String getStoreName()
    {
        return storeName;
    }

    public void setStoreName(String storeName)
    {
        this.storeName = storeName;
    }

    public int[] getSizes()
    {
        return sizes;
    }

    public void setSizes(int[] sizes)
    {
        this.sizes = sizes;
    }

    public void printReview()
    {
        System.out.println(storeName);
        printSizes();
    }

    public void printSizes()
    {
        for (int count = 0; count < sizes.length; count++)
            System.out.print(ProcessOrders.sizeNames[count] + " = " + sizes[count] + " ");
    }
}
