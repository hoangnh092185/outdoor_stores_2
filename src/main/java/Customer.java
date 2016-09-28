import org.sql2o.*;
import java.util.ArrayList;
import java.util.List;

public class Customer {
  private String name;
  private String email;
  private int id;

  public Customer(String name, String email) {
    this.name = name;
    this.email = email;
  }

  public String getName() {
    return name;
  }
  public String getEmail() {
    return email;
  }
  public int getId(){
    return id;
  }
  public static List<Customer> all() {
      String sql = "SELECT * FROM customers";
      try(Connection con = DB.sql2o.open()) {
       return con.createQuery(sql).executeAndFetch(Customer.class);
      }
    }
    public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO customers (name, email) VALUES (:name, :email)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("email", this.email)
        .executeUpdate()
        .getKey();
      }
    }
    public static Customer find(int id) {
      try(Connection con = DB.sql2o.open()) {
        String sql = "SELECT * FROM customers where id=:id";
        Customer customer = con.createQuery(sql)
          .addParameter("id", id)
          .executeAndFetchFirst(Customer.class);
        return customer;
      }
    }
    public List<Object> getInventories() {
    List<Object> allInventories = new ArrayList<Object>();

    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM inventories WHERE customerid=:id";
      List<Inventory> inventories = con.createQuery(sql)
        .addParameter("id", this.id)
        .throwOnMappingFailure(false)
        .executeAndFetch(Inventory.class);
      allInventories.addAll(inventories);

      }
      return allInventories;
    }

    @Override
   public boolean equals(Object otherCustomer){
     if (!(otherCustomer instanceof Customer)) {
       return false;
     } else {
       Customer newCustomer = (Customer) otherCustomer;
       return this.getName().equals(newCustomer.getName()) &&
              this.getEmail().equals(newCustomer.getEmail());
     }
   }

}
