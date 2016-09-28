import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Arrays;
import java.sql.Timestamp;
import java.util.Date;
import java.text.DateFormat;

public class InventoryTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void inventorie_instantiatesCorrectly_true() {
    Inventory testInventory = new Inventory("Carabiners", 1);
    assertEquals(true, testInventory instanceof Inventory);
  }
  @Test
   public void Inventory_instantiatesWithCustomerId_int() {
     Inventory testInventory = new Inventory("Carabiners", 1);
     assertEquals(1, testInventory.getCustomerId());
   }
 @Test
  public void equals_returnsTrueIfNameAndCustomerIdAreSame_true() {
    Inventory testInventory = new Inventory("Carabiners", 1);
    Inventory anotherInventory = new Inventory("Carabiners", 1);
    assertTrue(testInventory.equals(anotherInventory));
  }
  @Test
  public void save_returnsTrueIfDescriptionsAretheSame() {
    Inventory testInventory = new Inventory("Carabiners", 1);
    testInventory.save();
    assertTrue(Inventory.all().get(0).equals(testInventory));
  }
  @Test
  public void save_assignsIdToInventory() {
    Inventory testInventory = new Inventory("Carabiners", 1);
    testInventory.save();
    Inventory savedInventory = Inventory.all().get(0);
    assertEquals(savedInventory.getId(), testInventory.getId());
  }
  @Test
  public void all_returnsAllInstancesOfInventory_true() {
    Inventory firstInventory = new Inventory("Carabiners", 1);
    firstInventory.save();
    Inventory secondInventory = new Inventory("Climbing Protection", 1);
    secondInventory.save();
    assertEquals(true, Inventory.all().get(0).equals(firstInventory));
    assertEquals(true, Inventory.all().get(1).equals(secondInventory));
  }
  @Test
  public void find_returnsInventoryWithSameId_secondInventory() {
    Inventory firstInventory = new Inventory("Carabiners", 1);
    firstInventory.save();
    Inventory secondInventory = new Inventory("Climbing Protection", 3);
    secondInventory.save();
    assertEquals(Inventory.find(secondInventory.getId()), secondInventory);
  }
  @Test
  public void save_savesCustomerIdIntoDB_true() {
    Customer testCustomer = new Customer("Henry", "henry@henry.com");
    testCustomer.save();
    Inventory testInventory = new Inventory("Carabiners", testCustomer.getId());
    testInventory.save();
    Inventory savedInventory = Inventory.find(testInventory.getId());
    assertEquals(savedInventory.getCustomerId(), testCustomer.getId());
  }
  @Test
  public void getInventories_retrievesAllInventoriesFromDatabase_inventoriesList() {
    Customer testCustomer = new Customer("Henry", "henry@henry.com");
    testCustomer.save();
    Inventory firstInventory = new Inventory("Carabiners", testCustomer.getId());
    firstInventory.save();
    Inventory secondInventory = new Inventory("Climbing Protection", testCustomer.getId());
    secondInventory.save();
    Inventory[] inventories = new Inventory[] { firstInventory, secondInventory };
    assertTrue(testCustomer.getInventories().containsAll(Arrays.asList(inventories)));
  }
  @Test
  public void Updates_returnsAInstancesOfInventory_true() {
    Inventory firstInventory = new Inventory("Carabiners", 1);
    firstInventory.save();
    firstInventory.update(4);
    assertEquals(4, Inventory.find(firstInventory.getId()).getInventoryLevel());
  }

}
