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
  public void getInventorys_retrievesAllInventorysFromDatabase_inventoriesList() {
    Customer testCustomer = new Customer("Henry", "henry@henry.com");
    testCustomer.save();
    Inventory firstInventory = new Inventory("Carabiners", testCustomer.getId());
    firstInventory.save();
    Inventory secondInventory = new Inventory("Climbing Protection", testCustomer.getId());
    secondInventory.save();
    Inventory[] inventories = new Inventory[] { firstInventory, secondInventory };
    assertTrue(testCustomer.getInventorys().containsAll(Arrays.asList(inventories)));
  }
  @Test
  public void inventorie_instantiatesWithHalfFullPlayLevel(){
    Inventory testInventory = new Inventory("Carabiners", 1);
    assertEquals(testInventory.getPlayLevel(), (Inventory.MAX_ITEMS_LEVEL / 2));
  }
 //  @Test
 //  public void inventorie_instantiatesWithHalfFullSleepLevel(){
 //    Inventory testInventory = new Inventory("Carabiners", 1);
 //    assertEquals(testInventory.getSleepLevel(), (Inventory.MAX_SLEEP_LEVEL / 2));
 //  }
 //  @Test
 // public void isAlive_confirmsInventoryIsAliveIfAllLevelsAboveMinimum_true(){
 //   Inventory testInventory = new Inventory("Carabiners", 1);
 //   assertEquals(testInventory.isAlive(), true);
 // }
 // @Test
 //  public void depleteLevels_reducesAllLevels(){
 //    Inventory testInventory = new Inventory("Carabiners", 1);
 //    testInventory.depleteLevels();
 //    assertEquals(testInventory.getFoodLevel(), (Inventory.MAX_FOOD_LEVEL / 2) - 1);
 //    assertEquals(testInventory.getSleepLevel(), (Inventory.MAX_SLEEP_LEVEL / 2) - 1);
 //    assertEquals(testInventory.getPlayLevel(), (Inventory.MAX_ITEMS_LEVEL / 2) - 1);
 //  }
 //  @Test
 //  public void isAlive_recognizesInventoryIsDeadWhenLevelsReachMinimum_false(){
 //    Inventory testInventory = new Inventory("Carabiners", 1);
 //    for(int i = Inventory.MIN_ALL_LEVELS; i <= Inventory.MAX_FOOD_LEVEL; i++){
 //      testInventory.depleteLevels();
 //    }
 //    assertEquals(testInventory.isAlive(), false);
 //  }
 //  @Test
 //  public void play_increasesInventoryPlayLevel(){
 //    Inventory testInventory = new Inventory("Carabiners", 1);
 //    testInventory.play();
 //    assertTrue(testInventory.getPlayLevel() > (Inventory.MAX_ITEMS_LEVEL / 2));
 //  }
 //  @Test
 //  public void sleep_increasesInventorySleepLevel(){
 //    Inventory testInventory = new Inventory("Carabiners", 1);
 //    testInventory.sleep();
 //    assertTrue(testInventory.getSleepLevel() > (Inventory.MAX_SLEEP_LEVEL / 2));
 //  }
 //  @Test
 //  public void feed_increasesInventoryFoodLevel(){
 //    Inventory testInventory = new Inventory("Carabiners", 1);
 //    testInventory.feed();
 //    assertTrue(testInventory.getFoodLevel() > (Inventory.MAX_FOOD_LEVEL / 2));
 //  }
 //
 //  @Test(expected = UnsupportedOperationException.class)
 //  public void feed_throwsExceptionIfFoodLevelIsAtMaxValue(){
 //    Inventory testInventory = new Inventory("Carabiners", 1);
 //    for(int i = Inventory.MIN_ALL_LEVELS; i <= (Inventory.MAX_FOOD_LEVEL); i++){
 //      testInventory.feed();
 //    }
 //  }
 //  @Test
 //  public void inventorie_foodLevelCannotGoBeyondMaxValue(){
 //    Inventory testInventory = new Inventory("Carabiners", 1);
 //    for(int i = Inventory.MIN_ALL_LEVELS; i <= (Inventory.MAX_FOOD_LEVEL); i++){
 //      try {
 //        testInventory.feed();
 //      } catch (UnsupportedOperationException exception){ }
 //    }
 //    assertTrue(testInventory.getFoodLevel() <= Inventory.MAX_FOOD_LEVEL);
 //  }
 //  @Test(expected = UnsupportedOperationException.class)
 //  public void play_throwsExceptionIfPlayLevelIsAtMaxValue(){
 //    Inventory testInventory = new Inventory("Carabiners", 1);
 //    for(int i = Inventory.MIN_ALL_LEVELS; i <= (Inventory.MAX_ITEMS_LEVEL); i++){
 //      testInventory.play();
 //    }
 //  }
 //  @Test
 //  public void inventorie_playLevelCannotGoBeyondMaxValue(){
 //    Inventory testInventory = new Inventory("Carabiners", 1);
 //    for(int i = Inventory.MIN_ALL_LEVELS; i <= (Inventory.MAX_ITEMS_LEVEL); i++){
 //      try {
 //        testInventory.play();
 //      } catch (UnsupportedOperationException exception){ }
 //    }
 //    assertTrue(testInventory.getPlayLevel() <= Inventory.MAX_ITEMS_LEVEL);
 //  }
 //  @Test(expected = UnsupportedOperationException.class)
 //  public void sleep_throwsExceptionIfSleepLevelIsAtMaxValue(){
 //    Inventory testInventory = new Inventory("Carabiners", 1);
 //    for(int i = Inventory.MIN_ALL_LEVELS; i <= (Inventory.MAX_SLEEP_LEVEL); i++){
 //      testInventory.sleep();
 //    }
 //  }
 //  @Test
 //  public void inventorie_sleepLevelCannotGoBeyondMaxValue(){
 //    Inventory testInventory = new Inventory("Carabiners", 1);
 //    for(int i = Inventory.MIN_ALL_LEVELS; i <= (Inventory.MAX_SLEEP_LEVEL); i++){
 //      try {
 //        testInventory.sleep();
 //      } catch (UnsupportedOperationException exception){ }
 //    }
 //    assertTrue(testInventory.getSleepLevel() <= Inventory.MAX_SLEEP_LEVEL);
 //  }
 //  @Test
 // public void save_recordsTimeOfCreationInDatabase() {
 //   Inventory testInventory = new Inventory("Carabiners", 1);
 //   testInventory.save();
 //   Timestamp savedInventoryBirthday = Inventory.find(testInventory.getId()).getBirthday();
 //   Timestamp rightNow = new Timestamp(new Date().getTime());
 //   assertEquals(rightNow.getDay(), savedInventoryBirthday.getDay());
 // }
 // @Test
 //  public void sleep_recordsTimeLastSleptInDatabase() {
 //    Inventory testInventory = new Inventory("Carabiners", 1);
 //    testInventory.save();
 //    testInventory.sleep();
 //    Timestamp savedInventoryLastSlept = Inventory.find(testInventory.getId()).getLastSlept();
 //    Timestamp rightNow = new Timestamp(new Date().getTime());
 //    assertEquals(DateFormat.getDateTimeInstance().format(rightNow), DateFormat.getDateTimeInstance().format(savedInventoryLastSlept));
 //  }
 //  @Test
 // public void feed_recordsTimeLastAteInDatabase() {
 //   Inventory testInventory = new Inventory("Carabiners", 1);
 //   testInventory.save();
 //   testInventory.feed();
 //   Timestamp savedInventoryLastAte = Inventory.find(testInventory.getId()).getLastAte();
 //   Timestamp rightNow = new Timestamp(new Date().getTime());
 //   assertEquals(DateFormat.getDateTimeInstance().format(rightNow), DateFormat.getDateTimeInstance().format(savedInventoryLastAte));
 // }
 // @Test
 //  public void play_recordsTimeLastPlayedInDatabase() {
 //    Inventory testInventory = new Inventory("Carabiners", 1);
 //    testInventory.save();
 //    testInventory.play();
 //    Timestamp savedInventoryLastPlayed = Inventory.find(testInventory.getId()).getLastPlayed();
 //    Timestamp rightNow = new Timestamp(new Date().getTime());
 //    assertEquals(DateFormat.getDateTimeInstance().format(rightNow), DateFormat.getDateTimeInstance().format(savedInventoryLastPlayed));
 //  }
 //  @Test
 //  public void timer_executesDepleteLevelsMethod() {
 //    Inventory testInventory = new Inventory("Carabiners", 1);
 //    int firstPlayLevel = testInventory.getPlayLevel();
 //    testInventory.startTimer();
 //    try {
 //      Thread.sleep(7000);
 //    } catch (InterruptedException exception){}
 //    int secondPlayLevel = testInventory.getPlayLevel();
 //    assertTrue(firstPlayLevel > secondPlayLevel);
 //  }
 //  @Test
 // public void timer_haltsAfterInventoryDies() {
 //   Inventory testInventory = new Inventory("Carabiners", 1);
 //   testInventory.startTimer();
 //   try {
 //     Thread.sleep(10000);
 //   } catch (InterruptedException exception){}
 //   assertFalse(testInventory.isAlive());
 //   assertTrue(testInventory.getFoodLevel() >= 0);
 // }

}
