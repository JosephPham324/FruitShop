package lab211_long3;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 *
 * @author Pham Nhat Quang CE170036
 */
public class ShopManagement {

    private final ArrayList<Fruit> fruits; //Store fruits available
    private final Hashtable<String, ArrayList<Fruit>> orders;//Store orders
    private final Utility util; //Utility for inputs

    /**
     * Creates new instance of ShopManagement
     *
     * @param util To initialize util field
     */
    public ShopManagement(Utility util) {
        this.fruits = new ArrayList<>(); //Initialize fruits storage
        this.orders = new Hashtable<>();//Initialize orders storage
        this.util = util; //Initialize Utility object
    }

    /**
     * Add a fruit to the fruits storage and ask user if they want to make an
     * order. If user answer yes, call shopping method.
     */
    public void addFruit() {
        System.out.println("------ADD FRUIT------");
        Fruit fruitToAdd = getNewFruit(); //Get a new Fruit to add
        Fruit check = getExistingFruit(fruitToAdd.getName(), fruitToAdd.getOrigin(), fruitToAdd.getPrice()); //Check if there's already this fruit in the list
        if (check == null) { //If there is no existing fruit
            this.fruits.add(fruitToAdd); //Add to list
        } else { //If there is, update quantity
            check.setQuantity(check.getQuantity() + fruitToAdd.getQuantity());
        }
        if (askOrder()) { //If user wants to order
            shopping(); //Do shopping function
        } else { //Else show all fruits and return to menu
            viewFruits();
        }
    }

    /**
     * View an order as a table, with or without customer name.<br>
     * Table columns: Product (Product name), Quantity (Number of product
     * ordered), Price (Per one product), Amount (Total price)
     *
     * @param order Order to view (with key as customer name and value as order
     * items)
     */
    private void viewOrder(Map.Entry<String, ArrayList<Fruit>> order) {
        if (order.getKey() != null) { //If customer name isn't null
            System.out.println("Customer: " + order.getKey()); //Show customer name
        }
        int totalAmount = 0; //Store total amount of cost of the order
        int amountPerItem = 0;//Store total amount per item

        //Show table headings
        System.out.println("+--------------------+----------+-------+-------------+");
        System.out.println("| Product            | Quantity | Price | Amount      |");
        System.out.println("+--------------------+----------+-------+-------------+");
        for (Fruit show : order.getValue()) { //Show each fruit and its quantity in the order
            amountPerItem = show.getPrice() * show.getQuantity(); //Calculate item cost
            totalAmount += amountPerItem; //Add to total amount
            //Show information indicated in table headings
            System.out.printf("| %-18s | %8d | %4d$ | %10d$ |\n", show.getName(), show.getQuantity(), show.getPrice(), amountPerItem);
            System.out.println("+--------------------+----------+-------+-------------+");
        }
        System.out.printf("|                                  TOTAL| %10d$ |\n", totalAmount); //Show total cost of order
        System.out.println("+---------------------------------------+-------------+");
    }

    /**
     * View all orders in the storage, if there are no orders yet, issue an
     * alert
     */
    public void viewOrders() {
        if (!this.orders.isEmpty()) { //If there are orders to show
            Set<Map.Entry<String, ArrayList<Fruit>>> entries = this.orders.entrySet();//Get an iterable set of orders to do loopings
            for (Map.Entry<String, ArrayList<Fruit>> show : entries) {
                viewOrder(show); //Show individual order
            }
        } else {//If there are no orders, inform the user
            System.out.println("Sorry, there have not been any orderes yet!");
        }
    }

    /**
     * Show all fruits as a table. Table columns: No. (ID of fruit), Fruit Name,
     * Origin, Price (Per fruit)
     */
    public void viewFruits() {
        System.out.println("List of Fruits:");
        //Headings of table
        System.out.println("+-----+--------------------+-----------------+-------+");
        System.out.println("| No. | Fruit Name         | Origin          | Price |");
        System.out.println("+-----+--------------------+-----------------+-------+");
        for (Fruit show : fruits) { //Show every fruit in fruits list
            //Show information as indicated in the headings
            System.out.printf("| %3d | %-18s | %15s | %4d$ |\n", show.getID(), show.getName(), show.getOrigin(), show.getPrice());
            System.out.println("+-----+--------------------+-----------------+-------+");
        }
    }

    /**
     * Delete a fruit from the fruits list using its ID
     *
     * @param ID ID of fruit to delete
     */
    private void deleteFruit(int ID) {
        fruits.remove(ID - 1); //Remove from the list
        for (int i = ID - 1; i < fruits.size(); i++) {//Update ID of elements from that index to end of list
            fruits.get(i).setID(fruits.get(i).getID() - 1);
        }
    }

    /**
     * Perform shopping function.<br>
     * Get inputs from user to create an order and add to orders storage. Inputs
     * required:<br>
     * May repeat multiple times:<br>
     * Fruit ID or Fruit name <br>
     * Number of the fruit<br>
     * Once:<br>
     * Customer name<br>
     * When getting ID or Fruit name, if it's the first try, function exits, if
     * it's from second try on, keep asking for name or ID.
     */
    public void shopping() {
        ArrayList<Fruit> items = new ArrayList<>();//Store order items
        Fruit item; //Store item
        String fruitName, //Store fruit name to buy
                custName; //Store customer name
        int quantity = 0; //Store quantity of fruit to buy
        boolean validQuant, //Checks if quantity is valid (enough fruits are available in storage)
                orderIncomplete = false; //Checks if user want to choose more fruits

        System.out.println("------SHOPPING------");
        viewFruits(); //View fruits before ordering
        System.out.println("(!)Notice: You can select with name or ID\n"
                + "If there are fruits with the same name, selecting with name will\n"
                + "give you the fruit with lowest ID");
        do { //Perform shopping function

            fruitName = util.getString("You selected: ", 18, true); //Get fruit name

            item = getFruitCopy(fruitName);

            if (item != null) { //If the fruit exists
                boolean fruitDeleted = false;
                do {
                    quantity = util.getInt("Please input quantity: ", "Error: This must be an integer!", 1, 10000);

                    validQuant = (item.getQuantity() - quantity) >= 0; //Check if quantity is valid
                    if (!validQuant) { //If not valid, inform user
                        System.out.println("Sorry, this fruit only has " + item.getQuantity() + " available. Please choose another quantity.");
                    }
                } while (!validQuant); //Get quantity until valid

                if (item.getQuantity() - quantity > 0) {//If after buying there are still some of that fruit left
                    fruits.get(item.getID() - 1).setQuantity(item.getQuantity() - quantity); //Update quantity in storage
                } else { //Else delete that fruit from storage
                    deleteFruit(item.getID());
                    fruitDeleted = true;
                }

                item.setQuantity(quantity);//Set quantity of item
                items.add(item); //Add item to list of items

                if (areFruitsUnavailable() || askOrder()) {//If user want to order or no more fruit to buy
                    if (areFruitsUnavailable()) { //Inform user
                        System.out.println("There are no more fruits to buy.");
                    }
                    viewOrder(new AbstractMap.SimpleEntry<>(null, items)); //View the complete order

                    custName = util.getName("Input your name: ", 255, true);//Get customer name
                    addOrder(custName, items); //Add order to storage

                    System.out.println("Order complete!"); //Inform user order successful
                    orderIncomplete = false; //Exit function

                } else {
                    orderIncomplete = true; //Continue choosing items
                    if (fruitDeleted) {//If a fruit is out of stock, show the table again
                        viewFruits();
                        System.out.println("(!)Notice: You can select with name or ID\n"
                                + "If there are fruits with the same name, selecting with name will\n"
                                + "give you the fruit with lowest ID");
                    }
                }
            } else { //If the fruit doesn't exist, inform user
                System.out.println("Sorry, there is no fruit with that name or ID!");
            }
        } while (orderIncomplete); //While user still want to choose items

    }

    /**
     * Add an order to the orders storage
     *
     * @param customerName Name of customer
     * @param items List of items bought by customer
     */
    private void addOrder(String customerName, ArrayList<Fruit> items) {
        if (!this.orders.containsKey(customerName)) { //If there is no entry with that customer name in orders storage
            this.orders.put(customerName, items); //Put new order in orders storage
        } else {//If there exists an order with that customer name

            //Perform moddification of customer name
            String[] name = customerName.split(" ");//Split name to check if there's already a tag ([number])
            if (Pattern.compile("^\\([0-9]+\\)$").matcher(name[name.length - 1]).matches()) {//If there's a tag
                //Increment tag value to avoid duplicate
                int newOrderName = Integer.parseInt(name[name.length - 1].substring(1, name[name.length - 1].length() - 1)) + 1;

                name[name.length - 1] = "(" + newOrderName + ")";//Make the new tag
                String finalName = "";
                for (int i = 0; i < name.length; i++) { //Attach all parts of customerName together, including the new tag
                    finalName = finalName.concat(name[i]);
                    finalName += (i == name.length - 1) ? "" : " ";
                }
                addOrder(finalName, items); //Check if customer name + the new tag already exists and perform adding order
            } else { //If there's no tag, add new tag (1) to name
                addOrder(customerName + " (1)", items);//Check if "[customerName] (1)" already exists and perform adding order
            }
        }
    }

    /**
     * Get a copy of a fruit using a String of fruit name or fruit ID<br>
     * A copy is a difference instance with the same attribute values.
     *
     * @param nameOrID Fruit name or ID
     * @return A copy of an existing fruit or null if not found
     */
    private Fruit getFruitCopy(String nameOrID) {
        Fruit copy = null;//To store result
        if (Pattern.compile("^([0-9]+)$").matcher(nameOrID).matches()) { //If user input number
            try {
                copy = fruits.get(Integer.parseInt(nameOrID) - 1); //Get fruit based on ID
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
            }
        } else { //Else get fruit based on name
            copy = getExistingFruit(nameOrID); //Get exisiting fruit from storage
        }
        if (copy != null) { //If there is a fruit found, make copy of that fruit
            copy = copy.makeCopy();
        }
        return copy;//Return the copy
    }

    /**
     * Find and get reference of an existing fruit in the fruits storage.
     *
     * @param fruitName Exact name of fruit (case insensitive)
     * @return A reference to the fruit in the storage
     */
    private Fruit getExistingFruit(String fruitName) {
        for (Fruit get : fruits) {//Iterate through fruits list
            if (get.getName().equalsIgnoreCase(fruitName)) { //If name is the same
                return get;
            }
        }
        return null; //Return null if not found
    }

    /**
     * Find and get reference of an existing fruit in the fruits storage.
     *
     * @param fruitName Exact name of fruit (case insensitive)
     * @param fruitOrigin Exact of fruit (case insensitive)
     * @param fruitPrice Exact price of fruit
     * @return A reference to the fruit in the storage
     */
    private Fruit getExistingFruit(String fruitName, String fruitOrigin, int fruitPrice) {
        for (Fruit get : fruits) {//Iterate through fruits list
            //If name, price and origin of fruit is the same
            if (get.getName().equalsIgnoreCase(fruitName) && get.getOrigin().equalsIgnoreCase(fruitOrigin) && get.getPrice() == fruitPrice) {
                return get; //Return the fruit
            }
        }
        return null; //Return null if not found
    }

    /**
     * Ask user if they want to make an order now
     *
     * @return true if yes, false if no
     */
    public boolean askOrder() {
        String ask; //To store answer
        do {
            ask = util.getString("Do you want to order now? (Y/N): ", 1, true); //Ask user and get answer input
            if (!(ask.equalsIgnoreCase("Y") || ask.equalsIgnoreCase("N"))) { //If wrongg answer 
                System.out.println("Error: You must enter Y or N!"); //Warn user
            }
        } while (!(ask.equalsIgnoreCase("Y") || ask.equalsIgnoreCase("N"))); //Until answer is valid
        return ask.equalsIgnoreCase("Y"); //Return answer
    }

    /**
     * Get a new fruit with information from user. Information that will be
     * required: name of fruit; price, amount available, origin of fruit.
     *
     * @return A fruit with the inputed information, and id calculated from
     * fruits storage
     */
    public Fruit getNewFruit() {
        String name, //Name of fruit
                origin;//Origin of fruit
        int price, //Price of fruit
                quantity;//Quantity of fruit available
        final String errorNumber = "Error: This must be an integer";//Error message to display

        name = util.getName("Please enter fruit name: ", 18, true);//Get fruit name
        price = util.getInt("Please enter fruit price: ", errorNumber + " from 1 to 9999!", 1, 9999); //Get fruit price
        quantity = util.getInt("Please enter number of fruits available: ", errorNumber + " from 1 to 10000!", 1, 10000);//Get fruit quantity
        origin = util.getName("Please enter fruit origin: ", 15, true);//Get fruit origin

        return new Fruit(this.fruits.size() + 1, name, price, quantity, origin); //Return new fruit
    }

    /**
     * Check if there are fruits available in the storage
     *
     * @return true if no fruits available, false if there are
     */
    public boolean areFruitsUnavailable() {
        return this.fruits.isEmpty();
    }
}
