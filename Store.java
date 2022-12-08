import java.util.ArrayList;
/**
 * Connor Underwood, Zeyad Adham, Suhani Yadav, Neel Acharya
 *
 * A Store class
 */
public class Store {
    /**
     * ArrayList of Customer -> Sellers can view a dashboard that lists statistics for each of their stores
     * Data will include a list of customers with the number of items
     * that they have purchased and a list of products with the number of sales.
     *
     * Store 1: [Customer1,xItemsPurchased,product1,product2,product3]
     * xItems purchased IS THE SALES!
     * worry about this later when we get to Customer implementation
     *
     */
    private Seller seller; // each singular store has only one seller Each store has one seller associated with it
    // Each store has one seller associated with it. Another Seller may not have the same store

    public ArrayList<Shoe> shoes; // each store has a list of shoes

    private String name; // each store has a name

    private int sales; // each store has a number of sales that increase with each purchase from a customer

    private double revenue; // each store has a revenue that increases with each purchase from a customer

    private ArrayList<Customer> customers; // each store has a list of customers that have purchased something from them

    /**
     *
     * @param name read from market.csv
     * @param seller created from market.csv
     */
    public Store(String name, Seller seller) { // to make a store, you only need a name
        this.sales = 0; // initialize revenue to 0, may have to change this when we
        // implement customers because when we read from file we need to get the sales of store
        this.revenue = 0; // increase this as we read through file

        this.name = name; // passed through constructor from file we read from

        this.seller = seller; // passed through constructor from file we read from

        this.shoes = new ArrayList<>(); // new arraylist of shoes for every store
        // that is created, so we can add to it and not get null pointer exception

        this.customers = new ArrayList<>(); // new arraylist of customers for every store that is created,
        // so we can add customers to it if they purchase from this store specifically
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    /**
     * In case we ever need to find the seller of this Store object
     * @return
     */
    public Seller getSeller() {
        return this.seller;
    }
    /**
     * Sellers should be able to call this method in their ArrayList
     * of Stores to add a Shoe object to that specific Store
     * Adds a Shoe object to this Store's Shoe ArrayList
     * @param shoe
     */
    public boolean addShoe(Shoe shoe) {
        int count = 0;
        for (int i = 0; i < shoes.size(); i++) {
            if (shoe.getName().equalsIgnoreCase(shoes.get(i).getName())) {
                count++;
            }
        }
        if (count == 0) {
            this.shoes.add(shoe);
            return true;
        } else {
            return false;
        }
    }


    /**
     * Sellers should be able to call this method in their ArrayList
     * of Stores to remove a Shoe object from that specific Store
     *
     * Removes a shoe object from this Store's arrayList
     * @param shoe
     */
    public void removeShoe(Shoe shoe) {
        for (int i = 0; i < shoes.size(); i++) {
            if (shoes.get(i).equalsShoe(shoe)) {
                shoes.remove(shoes.get(i));
            }
        }
    }

    /**
     * Discuss with team how we want to do this method
     * @param shoe
     */
    public void editShoe(Shoe shoe) {

    }


    /**
     * @return the name of this Store object
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return The shoes ArrayList of this Store
     */
    public ArrayList<Shoe> getShoes() {
        return this.shoes;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public ArrayList<Customer> getCustomers() {
        return this.customers;
    }

    public void addCustomer(Customer customer) {
        this.customers.add(customer);
    }

    /**
     * Discuss with team how we want to do this
     * @param obj
     * @return True if obj is equal to another Store object
     * E.g. all params are equal
     */
    public boolean equalsStore(Object obj) {
        if (obj instanceof Store) {
            Store store = (Store) obj;
            return (store.getSeller().equalsSeller(this.seller) && store.getName().equalsIgnoreCase(this.name) &&
                    store.getSales() == this.sales && store.getShoes().equals(this.shoes)
                    && store.getCustomers().equals(this.customers));
        }
        return false;
    }

    /**
     * @param shoe A shoe object we use to check if this Store object's contains it inside of it's ArrayList
     * of Shoe objects
     * @return Returns a boolean value determining if this Store object's ArrayList of Shoes contains
     * this shoe object
     */
    public boolean checkShoe(Shoe shoe) {
        for (Shoe shoo : shoes) {
            if (shoo.equalsShoe(shoe)) {
                return true;
            }
        }
        return false;
    }
}