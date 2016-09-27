import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

  @Override
  protected void before() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/outdoor_stores_test", null, null);
  }

  @Override
  protected void after() {
    try(Connection con = DB.sql2o.open()) {
      String deleteStoresQuery = "DELETE FROM customers *;";
      String deleteItemsQuery = "DELETE FROM inventories *;";
      con.createQuery(deleteStoresQuery).executeUpdate();
      con.createQuery(deleteItemsQuery).executeUpdate();
    }
  }
}
