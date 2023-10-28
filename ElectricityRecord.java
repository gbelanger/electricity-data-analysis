import java.util.ArrayList;

public class ElectricityRecord implements Comparable<ElectricityRecord> {

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

    @Override
    public int compareTo(ElectricityRecord record) {
        return (int) Math.signum(this.gwh - record.getGwh());
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
        System.out.println(this.getClass().getSimpleName() + " - " + this.country + ", " + this.monthAndYear + ", " + this.balance + ", " + this.product + ", " + this.gwh);
    }
}
