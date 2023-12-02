package controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import db.database;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DashboardController implements Initializable {

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button close;

    @FXML
    private Button minimize;

    @FXML
    private Label username;

    @FXML
    private Button dashboard_btn;

    @FXML
    private Button avaialbeFD_btn;

    @FXML
    private Button order_btn;

    @FXML
    private Button logout;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private Label dashboard_NC;

    @FXML
    private Label dashboard_TI;

    @FXML
    private Label dashboard_TIncome;

    @FXML
    private BarChart<?, ?> dashboard_NOCChart;

    @FXML
    private AreaChart<?, ?> dashboard_ICChart;

    @FXML
    private AnchorPane availableFD_form;

    @FXML
    private TextField availableFD_productID;

    @FXML
    private TextField availableFD_productName;

    @FXML
    private ComboBox<?> availableFD_productType;

    @FXML
    private TextField availableFD_productPrice;

    @FXML
    private ComboBox<?> availableFD_productStatus;

    @FXML
    private Button availableFD_addBtn;

    @FXML
    private Button availableFD_updateBtn;

    @FXML
    private Button availableFD_clearBtn;

    @FXML
    private Button availableFD_deleteBtn;

    @FXML
    private TextField availableFD_search;

    @FXML
    private TableView<catagories> availableFD_tableView;

    @FXML
    private TableColumn<catagories, String> availableFD_col_productID;

    @FXML
    private TableColumn<catagories, String> availableFD_col_productName;

    @FXML
    private TableColumn<catagories, String> availableFD_col_type;

    @FXML
    private TableColumn<catagories, String> availableFD_col_price;

    @FXML
    private TableColumn<catagories, String> availableFD_col_status;

    @FXML
    private AnchorPane order_form;

    @FXML
    private TableView<product> order_tableView;

    @FXML
    private TableColumn<product, String> order_col_productID;

    @FXML
    private TableColumn<product, String> order_col_productName;

    @FXML
    private TableColumn<product, String> order_col_tyoe;

    @FXML
    private TableColumn<product, String> order_col_price;

    @FXML
    private TableColumn<product, String> order_col_quantity;

    @FXML
    private ComboBox<?> order_productID;

    @FXML
    private ComboBox<?> order_productName;

    @FXML
    private Spinner<Integer> order_quantity;

    @FXML
    private Button order_addBtn;

    @FXML
    private Label order_total;

    @FXML
    private TextField order_amount;

    @FXML
    private Label order_balance;

    @FXML
    private Button order_payBtn;

    @FXML
    private Button order_receiptBtn;

    @FXML
    private Button order_removeBtn;
    private Alert alert;
    private String[] catagories = {"Meals", "Drinks"};
    private String[] status = {"Available", "Not Available"};
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    @FXML
    void availableFDAdd(ActionEvent event) {
        String sql = "INSERT INTO catagory(product_id, product_name, type, price, status)"
            + "VALUES(?,?,?,?,?)";
        connect = database.connectDB();
        try
        {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, availableFD_productID.getText());
            prepare.setString(2, availableFD_productName.getText());
            prepare.setString(3, (String) availableFD_productType.getSelectionModel().getSelectedItem());
            prepare.setString(4, availableFD_productPrice.getText());
            prepare.setString(5, (String) availableFD_productStatus.getSelectionModel().getSelectedItem());

            if(availableFD_productID.getText().isEmpty()
            || availableFD_productName.getText().isEmpty()
            || availableFD_productType.getSelectionModel() == null
            || availableFD_productPrice.getText().isEmpty()
            || availableFD_productStatus.getSelectionModel() == null)
            {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
                return;
            }
            else
            {
                String checkData = "SELECT product_id FROM catagory WHERE product_id = '"
                    + availableFD_productID.getText() + "'";
                connect = database.connectDB();
                statement = connect.createStatement();
                result = statement.executeQuery(checkData);
            }
            if(result.next())
            {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("ProductID: " + availableFD_productID.getText() + " was already exist!");
                alert.showAndWait();
            }
            else
            {
                prepare.executeUpdate();
                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Added!");
                alert.showAndWait();
                availableFDShowData();
                availableFDClear();
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    void availableFDClear() {
        availableFD_productID.setText("");
        availableFD_productName.setText("");
        availableFD_productType.getSelectionModel().clearSelection();
        availableFD_productPrice.setText("");
        availableFD_productStatus.getSelectionModel().clearSelection();
    }

    @FXML
    void availableFDDelete(ActionEvent event) {
        String sql = "DELETE FROM catagory WHERE product_id = '" + availableFD_productID.getText() + "'";
        connect = database.connectDB();
        try
        {
            prepare = connect.prepareStatement(sql);
            if(availableFD_productID.getText().isEmpty()
            || availableFD_productName.getText().isEmpty()
            || availableFD_productType.getSelectionModel() == null
            || availableFD_productPrice.getText().isEmpty()
            || availableFD_productStatus.getSelectionModel() == null)
            {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }
            else
            {
                String checkData = "SELECT product_id FROM catagory WHERE product_id = '"
                    + availableFD_productID.getText() + "'";
                connect = database.connectDB();
                statement = connect.createStatement();
                result = statement.executeQuery(checkData);
            }
            if(!result.next())
            {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("ProductID: " + availableFD_productID.getText() + " was not exist!");
                alert.showAndWait();
            }
            else
            {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to DELETE ProductID: " + availableFD_productID.getText() + "?");

                Optional<ButtonType> option = alert.showAndWait();
                if(option.get().equals(ButtonType.OK))
                {
                    prepare.executeUpdate();
                    alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();
                    availableFDShowData();
                    availableFDClear();
                }
                else
                {
                    return;
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    void availableFDSearch() {
        
    }

    @FXML
    void availableFDSelect(MouseEvent event) {
        catagories catData = availableFD_tableView.getSelectionModel().getSelectedItem();
        int num = availableFD_tableView.getSelectionModel().getSelectedIndex();
        if(num - 1 < -1)
            return;
        availableFD_productID.setText(catData.getProductID());
        availableFD_productName.setText(catData.getName());
        availableFD_productPrice.setText(String.valueOf(catData.getPrice()));
        
    }

    @FXML
    void availableFDUpdate(ActionEvent event) {
        String sql = "UPDATE catagory SET "
            + "product_name = ?, type = ?, price = ?, status = ? "
            + "WHERE product_id = ?";
        connect = database.connectDB();
        try
        {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, availableFD_productName.getText());
            prepare.setString(2, (String) availableFD_productType.getSelectionModel().getSelectedItem());
            prepare.setString(3, availableFD_productPrice.getText());
            prepare.setString(4, (String) availableFD_productStatus.getSelectionModel().getSelectedItem());
            prepare.setString(5, availableFD_productID.getText());

            if(availableFD_productID.getText().isEmpty()
            || availableFD_productName.getText().isEmpty()
            || availableFD_productType.getSelectionModel() == null
            || availableFD_productPrice.getText().isEmpty()
            || availableFD_productStatus.getSelectionModel() == null)
            {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }
            else
            {
                String checkData = "SELECT product_id FROM catagory WHERE product_id = '"
                    + availableFD_productID.getText() + "'";
                connect = database.connectDB();
                statement = connect.createStatement();
                result = statement.executeQuery(checkData);
            }
            if(!result.next())
            {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("ProductID: " + availableFD_productID.getText() + " was not exist!");
                alert.showAndWait();
            }
            else
            {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATED ProductID: " + availableFD_productID.getText() + "?");

                Optional<ButtonType> option = alert.showAndWait();
                if(option.get().equals(ButtonType.OK))
                {
                    prepare.executeUpdate();
                    alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();
                    availableFDShowData();
                    availableFDClear();
                }
                else
                {
                    return;
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public ObservableList<catagories> availableFDListData()
    {
        ObservableList<catagories> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM catagory";
        connect = database.connectDB();
        try
        {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            catagories cat;
            while(result.next())
            {
                cat = new catagories(
                    result.getString("product_id"),
                    result.getString("product_name"), 
                    result.getString("type"),
                    result.getDouble("price"),
                    result.getString("status")
                );
                listData.add(cat);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return listData;
    }
    private ObservableList<catagories> availableFDList;
    public void availableFDShowData()
    {
        availableFDList = availableFDListData();
        availableFD_col_productID.setCellValueFactory(new PropertyValueFactory<>("productID"));
        availableFD_col_productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        availableFD_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        availableFD_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        availableFD_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));

        availableFD_tableView.setItems(availableFDList);
    }

    public void availableFDStatus() {
        List<String> listStatus= new ArrayList<>();
        for(String data : status)
        {
            listStatus.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(listStatus);
        availableFD_productStatus.setItems(listData);
    }

    public void availableFDType() {
        List<String> listCatagory = new ArrayList<>();
        for(String data : catagories)
        {
            listCatagory.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(listCatagory);
        availableFD_productType.setItems(listData);
    }

    @FXML
    void logout(ActionEvent event) {
        try 
        {
            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure want to logout?");
            Optional<ButtonType> option = alert.showAndWait();
            
            if(option.get().equals(ButtonType.OK))
            {
                logout.getScene().getWindow().hide();

                Parent root = FXMLLoader.load(getClass().getResource("../view/loginPage.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setResizable(false);
                stage.setScene(scene);
                stage.show();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    @FXML
    void orderAdd(ActionEvent event) {
        
    }

    @FXML
    void orderAmount(ActionEvent event) {

    }

    @FXML
    void orderPay(ActionEvent event) {
        
    }

    public void orderCustomerId() {
        
    }
    @FXML
    void orderProductId() {

    }

    @FXML
    void orderProductName() {


    }
    @FXML
    void orderQuantity(MouseEvent event) {
    }

    @FXML
    void orderReceipt() {

    }

    @FXML
    void orderRemove(ActionEvent event) {
        
    }
    @FXML
    void orderSelectData(MouseEvent event) {
    }

    @FXML
    void switchForm(ActionEvent event) {
        if(event.getSource() == dashboard_btn)
        {
            dashboard_form.setVisible(true);
            availableFD_form.setVisible(false);
            order_form.setVisible(false);
        }
        else if(event.getSource() == avaialbeFD_btn)
        {
            dashboard_form.setVisible(false);
            availableFD_form.setVisible(true);
            order_form.setVisible(false);

            availableFDShowData();
        }
        else if(event.getSource() == order_btn)
        {
            dashboard_form.setVisible(false);
            availableFD_form.setVisible(false);
            order_form.setVisible(true);

            orderProductId();
            orderProductName();
        }
    }

    public void displayUsername() {
        String user = data.username;
        username.setText(user);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        displayUsername();
        availableFDStatus();
        availableFDType();

        availableFDShowData();

        orderProductId();
        orderProductName();
    }
}
