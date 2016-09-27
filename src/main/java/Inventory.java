import org.sql2o.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Timer;
import java.util.TimerTask;

public class Inventory {
  private String name;
  private int customerId;
  private int id;

  public Inventory(String name, int customerId){
    this.name = name;
    this.customerId = customerId;
  }

  public String getName(){
    return name;
  }
  public int getCustomerId(){
    return customerId;
  }
  public int getId(){
    return id;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO inventories (name, customerId) VALUES (:name, :customerId)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("customerId", this.customerId)
        .executeUpdate()
        .getKey();
    }
  }
  public List<Inventory> getInventory() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM inventories where customerId=:id";
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeAndFetch(Inventory.class);
    }
  }
  public static Inventory find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM inventories where id=:id";
      Inventory inventory = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Inventory.class);
      return inventory;
    }
  }


  @Override
  public boolean equals(Object otherInventory){
    if (!(otherInventory instanceof Inventory)) {
      return false;
    } else {
      Inventory newInventory = (Inventory) otherInventory;
      return this.getName().equals(newInventory.getName()) &&
             this.getCustomerId() == newInventory.getCustomerId();
    }
  }
}
