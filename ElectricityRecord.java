import java.util.ArrayList;

public class ElectricityRecord {

    private String country;
    private String monthAndYear;
    private String balance;
    private String product;
    private double gwh;

    public ElectricityRecord(String country, String monthAndYear, String balance, String product, double gwh) {
        this.country = country;
        this.monthAndYear = monthAndYear;
        this.balance = balance;
        this.product = product;
        this.gwh = gwh;
    }

    public String getCountry() {
        return country;
    }

    public String getMonthAndYear() {
        return monthAndYear;
    }

    public String getBalance() {
        return balance;
    }

    public String getProduct() {
        return product;
    }

    public double getGwh() {
        return gwh;
    }

    public void print() {
        System.out.println("Record: "+ this.country + ", " + this.monthAndYear + ", " + this.balance + ", " + this.product + ", " + this.gwh);
    }
}
