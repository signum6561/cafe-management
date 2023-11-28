package db;

import java.sql.Connection;
import java.sql.DriverManager;

public class database 
{
    public static Connection connectDB()
    {
        try 
        {
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/cafemanagement", "root", "dat789qu");
            return connect;
        }
        catch(Exception e)
        {
            System.out.println("ERROR");
            e.printStackTrace();
        }
        return null;
    }
        
}
