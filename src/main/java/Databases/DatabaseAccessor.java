package Databases;
import Entities.AccountCredidentials;

import java.sql.*;

public abstract class DatabaseAccessor {

    private Database database;
    private DatabaseAccessor () {}
    public DatabaseAccessor(Database database) {
        this.database = database;
    }




    public boolean printHotelChains () {
        return tryRunStatement((conn) -> {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("select chain_name from HotelChain");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("chain_name"));
            }
            return true;
        });
    }

    protected boolean tryRunStatement (IAccessRunFunction function) {
        Connection connection = database.getConnection();
        if(connection==null) {
            System.out.println("Failed to run SQL statement: Connection to database missing.");
            return false;
        }

        try {
            return function.tryRun(connection);
        } catch (Exception e) {
            System.out.println("Failed to run SQL statement: " + e.toString());
            return false;
        }
    }

    protected AccessResult tryReturnStatement (IAccessReturnFunction function) {
        Connection connection = database.getConnection();
        if(connection==null) {
            System.out.println("Failed to get result from SQL statement: Connection to database missing.");
            return new AccessResult(false, null);
        }
        try {
            return function.tryReturn(connection);
        } catch (Exception e) {
            System.out.println("Failed to get result from SQL statement: " + e.toString());
            return new AccessResult(false, null);
        }
    }
}






