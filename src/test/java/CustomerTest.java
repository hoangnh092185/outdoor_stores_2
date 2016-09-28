import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class CustomerTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void person_instantiatesCorrectly_true() {
    Customer testCustomer = new Customer("Henry", "henry@henry.com");
    assertEquals(true, testCustomer instanceof Customer);
  }
  @Test
  public void getName_personInstantiatesWithName_Henry() {
  Customer testCustomer = new Customer("Henry", "henry@henry.com");
  assertEquals("Henry", testCustomer.getName());
  }
  @Test
  public void getEmail_personInstantiatesWithEmail_String() {
  Customer testCustomer = new Customer("Henry", "henry@henry.com");
  assertEquals("henry@henry.com", testCustomer.getEmail());
  }
  @Test
  public void equals_returnsTrueIfNameAndEmailAreSame_true() {
    Customer firstCustomer = new Customer("Henry", "henry@henry.com");
    Customer anotherCustomer = new Customer("Henry", "henry@henry.com");
    assertTrue(firstCustomer.equals(anotherCustomer));
  }
  @Test
  public void save_insertsObjectIntoDatabase_Customer() {
    Customer testCustomer = new Customer("Henry", "henry@henry.com");
    testCustomer.save();
    assertTrue(Customer.all().get(0).equals(testCustomer));
  }
  @Test
  public void all_returnsAllInstancesOfCustomer_true() {
    Customer firstCustomer = new Customer("Henry", "henry@henry.com");
    firstCustomer.save();
    Customer secondCustomer = new Customer("Harriet", "harriet@harriet.com");
    secondCustomer.save();
    assertEquals(true, Customer.all().get(0).equals(firstCustomer));
    assertEquals(true, Customer.all().get(1).equals(secondCustomer));
  }
  @Test
   public void save_assignsIdToObject() {
     Customer testCustomer = new Customer("Henry", "henry@henry.com");
     testCustomer.save();
     Customer savedCustomer = Customer.all().get(0);
     assertEquals(testCustomer.getId(), savedCustomer.getId());
   }
   @Test
    public void find_returnsCustomerWithSameId_secondCustomer() {
      Customer firstCustomer = new Customer("Henry", "henry@henry.com");
      firstCustomer.save();
      Customer secondCustomer = new Customer("Harriet", "harriet@harriet.com");
      secondCustomer.save();
      assertEquals(Customer.find(secondCustomer.getId()), secondCustomer);
    }

}
