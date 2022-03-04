/**
 * @author Steve Shi
 * @version 11/26/2021
 * restaurant object for individual restaurants with information such as
 * name, address, hours, simple few word description, distance from 
 * woodruff north, and the average cost, with a few speciality
 */

public class restaurant {
    private int rank;        // Rank on the 75 best restaurants in Atlanta list
    private String name;        // Name of restaurant
    private String address;     // The address of the restaurant
    private String shortDescription;    // A short description of the restaurant
    private double distance;    // Distance in miles from woodruff
    private String avgCost;     // Average cost
    private String popularD;    // A popular dish to order;

    public restaurant(int rank, String name, String address, String shortDescription, double distance, String avgCost, String popularD) {
        this.rank = rank;
        this.name = name;
        this.address = address;
        this.shortDescription = shortDescription;
        this.distance = distance;
        this.avgCost = avgCost;
        this.popularD = popularD;
    }

    public int getRank() { return rank; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getShortDescription() { return shortDescription; }
    public double getDistance() { return distance; }
    public String getAvgCost() { return avgCost; }
    public String getPopularD() { return popularD; }

    public void setRank(int rank) { this.rank = rank; }
    public void setName(String name) { this.name = name; }
    public void setAddress(String address) { this.address = address; }
    public void setShortDescription(String shortDescription) { this.shortDescription = shortDescription; }
    public void setDistance(double distance) { this.distance = distance; }
    public void setAvgCost(String avgCost) { this.avgCost = avgCost; }
    public void setPopularD(String popularD) { this.popularD = popularD; }

    public String toString() {
        return String.format("%-15s%d\n%-14s%s\n%-14s%s\n%-14s%s\n%-17s%smi\n%-16s%s\n%-14s%s",
                            "NUM:",rank, "NAME:", name, "ADDR:", address, "DESCR:", shortDescription,
                            "DIST:", distance, "AVRG:", avgCost, "POPULR:", popularD);
    }
    public static void main(String[] args) {
        restaurant steve = new restaurant(0, "Steve", "124 Conch Street ", "Home of the Krabby Patties", 1000000, "$$$", "Nasty Patty");
        System.out.println(steve);
    }
}
