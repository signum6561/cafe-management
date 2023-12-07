package controller;

import dao.CatagoriesDAO;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Catagories;
import utils.TableUtil;



public class DashboardController implements Initializable
{

    @FXML private AnchorPane main_form;

    @FXML private Button close;

    @FXML private Button minimize;

    @FXML private Label username;

    @FXML private Button dashboard_btn;

    @FXML private Button avaialbeFD_btn;

    @FXML private Button order_btn;

    @FXML private Button logout;

    @FXML private AnchorPane dashboard_form;

    @FXML private Label dashboard_NC;

    @FXML private Label dashboard_TI;

    @FXML private Label dashboard_TIncome;

    @FXML private BarChart<?, ?> dashboard_NOCChart;

    @FXML private AreaChart<?, ?> dashboard_ICChart;

    @FXML private AnchorPane availableFD_form;

    @FXML private TextField availableFD_productID;

    @FXML private TextField availableFD_productName;

    @FXML private ComboBox<?> availableFD_productType;

    @FXML private TextField availableFD_productPrice;

    @FXML private ComboBox<?> availableFD_productStatus;

    @FXML private Button availableFD_addBtn;

    @FXML private Button availableFD_updateBtn;

    @FXML private Button availableFD_clearBtn;

    @FXML private Button availableFD_deleteBtn;

    @FXML private TextField availableFD_search;

    @FXML private TableView<Catagories> availableFD_tableView;

    @FXML private TableColumn<Catagories, String> availableFD_col_productID;

    @FXML private TableColumn<Catagories, String> availableFD_col_productName;

    @FXML private TableColumn<Catagories, String> availableFD_col_type;

    @FXML private TableColumn<Catagories, String> availableFD_col_price;

    @FXML private TableColumn<Catagories, String> availableFD_col_status;

    @FXML private AnchorPane order_form;

    @FXML private TableView<?> order_tableView;

    @FXML private TableColumn<?, ?> order_col_productID;

    @FXML private TableColumn<?, ?> order_col_productName;

    @FXML private TableColumn<?, ?> order_col_tyoe;

    @FXML private TableColumn<?, ?> order_col_price;

    @FXML private TableColumn<?, ?> order_col_quantity;

    @FXML private ComboBox<?> order_productID;

    @FXML private ComboBox<?> order_productName;

    @FXML private Spinner<Integer> order_quantity;

    @FXML private Button order_addBtn;

    @FXML private Label order_total;

    @FXML private TextField order_amount;

    @FXML private Label order_balance;

    @FXML private Button order_payBtn;

    @FXML private Button order_receiptBtn;

    @FXML private Button order_removeBtn;
    private Alert alert;
    List<String> status = new ArrayList<>();
    List<String> type = new ArrayList<>();

    @FXML void availableFDAdd(ActionEvent event)
    {
        Catagories cat = new Catagories();
        try
        {
            cat.setProductID(availableFD_productID.getText());
            cat.setName(availableFD_productName.getText());
            cat.setType((String)availableFD_productType.getSelectionModel().getSelectedItem());
            cat.setPrice(Double.parseDouble(availableFD_productPrice.getText()));
            cat.setStatus((String)availableFD_productStatus.getSelectionModel().getSelectedItem());

            if(availableFD_productID.getText().isEmpty() || availableFD_productName.getText().isEmpty()
               || availableFD_productType.getSelectionModel() == null || availableFD_productPrice.getText().isEmpty()
               || availableFD_productStatus.getSelectionModel() == null)
            {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
                return;
            }
            else if(CatagoriesDAO.Instance().containID(availableFD_productID.getText()))
            {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("ProductID: " + availableFD_productID.getText() + " was already exist!");
                alert.showAndWait();
            }
            else
            {
                CatagoriesDAO.Instance().insert(cat);
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

    @FXML void availableFDClear()
    {
        availableFD_productID.setText("");
        availableFD_productName.setText("");
        availableFD_productType.getSelectionModel().clearSelection();
        availableFD_productPrice.setText("");
        availableFD_productStatus.getSelectionModel().clearSelection();
    }

    @FXML void availableFDDelete(ActionEvent event)
    {
        try
        {
            if(availableFD_productID.getText().isEmpty())
            {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }
            if(!CatagoriesDAO.Instance().containID(availableFD_productID.getText()))
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
                alert.setContentText("Are you sure you want to DELETE ProductID: " + availableFD_productID.getText()
                                     + "?");

                Optional<ButtonType> option = alert.showAndWait();
                if(option.get().equals(ButtonType.OK))
                {
                    CatagoriesDAO.Instance().delete(availableFD_productID.getText());
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

    @FXML void availableFDSearch()
    {
    }

    @FXML void availableFDSelect(MouseEvent event)
    {
        Catagories catData = availableFD_tableView.getSelectionModel().getSelectedItem();
        int num = availableFD_tableView.getSelectionModel().getSelectedIndex();
        if(num - 1 < -1)
            return;
        try
        {
            availableFD_productID.setText(catData.getProductID());
            availableFD_productName.setText(catData.getName());
            availableFD_productType.getSelectionModel().select(type.indexOf(catData.getType()));
            availableFD_productPrice.setText(String.valueOf(catData.getPrice()));
            availableFD_productStatus.getSelectionModel().select(status.indexOf(catData.getStatus()));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @FXML void availableFDUpdate(ActionEvent event)
    {
        Catagories cat = new Catagories();
        try
        {
            cat.setProductID(availableFD_productID.getText());
            cat.setName(availableFD_productName.getText());
            cat.setType((String)availableFD_productType.getSelectionModel().getSelectedItem());
            cat.setPrice(Double.parseDouble(availableFD_productPrice.getText()));
            cat.setStatus((String)availableFD_productStatus.getSelectionModel().getSelectedItem());

            if(availableFD_productID.getText().isEmpty() || availableFD_productName.getText().isEmpty()
               || availableFD_productType.getSelectionModel() == null || availableFD_productPrice.getText().isEmpty()
               || availableFD_productStatus.getSelectionModel() == null)
            {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }
            if(!CatagoriesDAO.Instance().containID(availableFD_productID.getText()))
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
                alert.setContentText("Are you sure you want to UPDATED ProductID: " + availableFD_productID.getText()
                                     + "?");

                Optional<ButtonType> option = alert.showAndWait();
                if(option.get().equals(ButtonType.OK))
                {
                    CatagoriesDAO.Instance().update(cat);
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

    private ObservableList<Catagories> availableFDList;

    public void availableFDShowData()
    {
        availableFDList = CatagoriesDAO.Instance().getAll();
        availableFD_col_productID.setCellValueFactory(new PropertyValueFactory<>("productID"));
        availableFD_col_productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        availableFD_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        availableFD_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        availableFD_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
    
        availableFD_tableView.setItems(availableFDList);
        TableUtil.autoResizeColumns(availableFD_tableView);
    }

    public void availableFDStatus()
    {
        status.add("Available");
        status.add("Not Available");
        ObservableList listData = FXCollections.observableArrayList(status);
        availableFD_productStatus.setItems(listData);
    }

    public void availableFDType()
    {
        type.add("Meals");
        type.add("Drinks");
        ObservableList listData = FXCollections.observableArrayList(type);
        availableFD_productType.setItems(listData);
    }

    @FXML void logout(ActionEvent event)
    {
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

    @FXML void orderAdd(ActionEvent event)
    {
    }

    @FXML void orderAmount(ActionEvent event)
    {
    }

    @FXML void orderPay(ActionEvent event)
    {
    }

    public void orderCustomerId()
    {
    }

    @FXML void orderProductId()
    {
    }

    @FXML void orderProductName()
    {
    }

    @FXML void orderQuantity(MouseEvent event)
    {
    }

    @FXML void orderReceipt()
    {
    }

    @FXML void orderRemove(ActionEvent event)
    {
    }

    @FXML void orderSelectData(MouseEvent event)
    {
    }

    @FXML void switchForm(ActionEvent event)
    {
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

    public void displayUsername()
    {
        String user = data.username;
        username.setText(user);
    }

    @Override public void initialize(URL arg0, ResourceBundle arg1)
    {
        displayUsername();
        availableFDStatus();
        availableFDType();
        availableFDShowData();
    }
}
