package lab211_long3;

/**
 *
 * @author Pham Nhat Quang CE170036
 */
public class Fruit {

    private int ID; //ID of fruit
    private final String name; //Name of fruit
    private final int price; //Price of fruit
    private int quantity; //Quantity of fruit
    private final String origin; //Origin of fruit

    /**
     * Create new Fruit object
     *
     * @param ID ID of new fruit
     * @param name Name of new fruit
     * @param price Price of new fruit
     * @param quantity Quantity of new fruit (how many fruits are available)
     * @param origin Origin of new fruit (where the fruit came from)
     */
    public Fruit(int ID, String name, int price, int quantity, String origin) {
        //Initialize fields
        this.ID = ID;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.origin = origin;
    }

    /**
     * Set ID of fruit
     *
     * @param ID ID to set
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * Get ID of fruit
     *
     * @return ID of fruit
     */
    public int getID() {
        return ID;
    }

    /**
     * Get name of fruit
     *
     * @return Name of fruit
     */
    public String getName() {
        return name;
    }

    /**
     * Get price of fruit
     *
     * @return Price of fruit
     */
    public int getPrice() {
        return price;
    }

    /**
     * Get quantity of fruit
     *
     * @return Quantity of fruit
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Get origin of fruit
     *
     * @return Origin of fruit
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * Set quantity of fruit
     *
     * @param quantity Quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Make a copy of this fruit<br>
     * A copy is an instance with the same attributes values
     *
     * @return A copy of this fruit instance
     */
    public Fruit makeCopy() {
        //Return a copy with fields value same as this instance
        return new Fruit(this.getID(), this.getName(), this.getPrice(), this.getQuantity(), this.getOrigin());
    }
}
