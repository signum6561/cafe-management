package utils;

import java.sql.*;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;

public class DBUtil
{
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static Connection connect = null;
    private static final String URL = "jdbc:mysql://localhost:3306/cafemanagement";

    public static void connectDB()
    {
        try
        {
            Class.forName(JDBC_DRIVER);
            connect = DriverManager.getConnection(URL, "root", "dat789qu");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void disconnectDB()
    {
        try
        {
            if(connect != null && !connect.isClosed())
            {
                connect.close();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static ResultSet ExecuteQuery(String query) throws SQLException
    {
        Statement statement = null;
        ResultSet resultSet = null;
        CachedRowSet crs = null;
        try
        {
            connectDB();
            statement = connect.createStatement();
            resultSet = statement.executeQuery(query);
            crs = RowSetProvider.newFactory().createCachedRowSet();
            crs.populate(resultSet);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(resultSet != null)
            {
                resultSet.close();
            }
            if(statement != null)
            {
                statement.close();
            }
            disconnectDB();
        }
        return crs;
    }

    public static void ExecuteUpdate(String query) throws SQLException
    {
        Statement statement = null;
        try
        {
            connectDB();
            statement = connect.createStatement();
            statement.executeUpdate(query);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(statement != null)
            {
                statement.close();
            }
            disconnectDB();
        }
    }
}
