package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Catagories;
import utils.DBUtil;


public class CatagoriesDAO implements DAO<Catagories>
{
    private static CatagoriesDAO instance;
    private CatagoriesDAO()
    {
    }
    public static CatagoriesDAO Instance()
    {
        if(instance == null)
        {
            instance = new CatagoriesDAO();
        }
        return instance;
    }
    @Override public ObservableList<Catagories> getAll()
    {
        String query = "SELECT * FROM catagory";
        ObservableList<Catagories> List = FXCollections.observableArrayList();
        try
        {
            ResultSet resultSet = DBUtil.ExecuteQuery(query);
            while(resultSet.next())
            {
                List.add(getFromResultSet(resultSet));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return List;
    }

    @Override public Catagories get(String id)
    {
        String query = "SELECT * FROM catagory WHERE product_id='" + id + "'";
        Catagories cat = null;
        try
        {
            ResultSet resultSet = DBUtil.ExecuteQuery(query);
            if(resultSet != null)
            {
                cat = getFromResultSet(resultSet);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return cat;
    }

    private Catagories getFromResultSet(ResultSet resultSet) throws SQLException
    {
        Catagories cat = new Catagories();
        cat.setProductID(resultSet.getString("product_id"));
        cat.setName(resultSet.getString("product_name"));
        cat.setType(resultSet.getString("type"));
        cat.setPrice(resultSet.getDouble("price"));
        cat.setStatus(resultSet.getString("status"));
        return cat;
    }
    public boolean containID(String product_id) throws SQLException
    {
        String query = "SELECT * FROM catagory WHERE product_id='" + product_id + "'";
        ResultSet resultSet = DBUtil.ExecuteQuery(query);
        return resultSet.next();
    }
    @Override public void insert(Catagories t)
    {
        String query = "INSERT INTO catagory(product_id, product_name, type, price, status) VALUES"
                       + "('" + t.getProductID() + "','" + t.getName() + "','" + t.getType() + "'," + t.getPrice()
                       + ",'" + t.getStatus() + "')";
        System.out.println(query);
        try
        {
            DBUtil.ExecuteUpdate(query);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override public void update(Catagories t)
    {
        String query = "UPDATE catagory SET "
                       + "product_name="
                       + "'" + t.getName() + "'"
                       + ",type="
                       + "'" + t.getType() + "'"
                       + ",price=" + t.getPrice() + ",status="
                       + "'" + t.getStatus() + "'"
                       + " WHERE "
                       + "product_id="
                       + "'" + t.getProductID() + "'";
        System.out.println(query);
        try
        {
            DBUtil.ExecuteUpdate(query);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override public void delete(String product_id)
    {
        String query = "DELETE FROM catagory WHERE product_id='" + product_id + "'";
        System.out.println(query);
        try
        {
            DBUtil.ExecuteUpdate(query);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
