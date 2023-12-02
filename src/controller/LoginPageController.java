package controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import db.database;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoginPageController implements Initializable {

    @FXML
    private TextField fp_answer;

    @FXML
    private Button fp_back;

    @FXML
    private Button fp_proceedBtn;

    @FXML
    private ComboBox<?> fp_question;

    @FXML
    private AnchorPane fp_questionForm;

    @FXML
    private TextField fp_username;

    @FXML
    private Button np_back;

    @FXML
    private Button np_changePassBtn;

    @FXML
    private PasswordField np_confirmPassword;

    @FXML
    private AnchorPane np_newPassForm;

    @FXML
    private PasswordField np_newPassword;

    @FXML
    private Hyperlink si_forgotPass;

    @FXML
    private Button si_loginBtn;

    @FXML
    private AnchorPane si_loginForm;

    @FXML
    private PasswordField si_password;

    @FXML
    private TextField si_username;

    @FXML
    private Button side_CreateBtn;

    @FXML
    private Button side_alreadyHave;

    @FXML
    private AnchorPane side_form;

    @FXML
    private TextField su_answer;

    @FXML
    private PasswordField su_password;

    @FXML
    private ComboBox<?> su_question;

    @FXML
    private Button su_signupBtn;

    @FXML
    private AnchorPane su_signupForm;

    @FXML
    private TextField su_username;

    public String[] questionList = { "What is your favorite color?", "What is your favorite food?",
            "What is your birth date?" };

    private Alert alert;
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    @FXML
    void backToLoginForm(ActionEvent event) {
        fp_questionForm.setVisible(false);
        si_loginForm.setVisible(true);
    }

    @FXML
    void backToQuestionForm(ActionEvent event) {

    }

    @FXML
    void changePassBtn(ActionEvent event) {
        if(np_newPassword.getText().isEmpty()
        || np_confirmPassword.getText().isEmpty())
        {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        }
        else if(np_newPassword.getText().equals(np_confirmPassword.getText()))
        {
            String updatePass = "UPDATE employee SET pass = ? WHERE username = ?";
            connect = database.connectDB();
            try
            {
                prepare = connect.prepareStatement(updatePass);
                prepare.setString(1, fp_username.getText());
                prepare.setString(2, np_newPassword.getText());
                prepare.executeUpdate();

                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully changed password");
                alert.showAndWait();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Not match");
            alert.showAndWait();
        }
    }

    @FXML
    void loginBtn(ActionEvent event) {
        if(si_username.getText().isEmpty()
        || si_password.getText().isEmpty())
        {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Incorrect Username/Password");
            alert.showAndWait();
        }
        else
        {
            String selectData  = "SELECT username, pass FROM employee WHERE username = ? and pass = ?";
            connect = database.connectDB();
            try
            {
                prepare = connect.prepareStatement(selectData);
                prepare.setString(1, si_username.getText());
                prepare.setString(2, si_password.getText());
                result = prepare.executeQuery();
                if(result.next())
                {
                    data.username = si_username.getText();
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Infomation Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Login");
                    alert.showAndWait();

                    si_loginBtn.getScene().getWindow().hide();

                    Parent root = FXMLLoader.load(getClass().getResource("../view/dashboard.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                }
                else
                {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Incorrect Username/Password");
                    alert.showAndWait();
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void proceedBtn(ActionEvent event) {
        if(fp_question.getSelectionModel().getSelectedItem() == null
        || fp_answer.getText().isEmpty()
        || fp_username.getText().isEmpty())
        {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        }
        else 
        {
            String selectData = "SELECT username, question, answer FROM employee WHERE username = ? AND question = ? AND answer = ?";
            connect = database.connectDB();
            try
            {
                prepare = connect.prepareStatement(selectData);
                prepare.setString(1, fp_username.getText());
                prepare.setString(2, (String) fp_question.getSelectionModel().getSelectedItem());
                prepare.setString(3, fp_answer.getText());
                result = prepare.executeQuery();
                if(result.next())
                {
                    np_newPassForm.setVisible(true);
                    fp_questionForm.setVisible(false);
                }
                else
                {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Incorrect Information");
                    alert.showAndWait();     
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void regBtn(ActionEvent event) {
        if (su_username.getText().isEmpty()
                || su_password.getText().isEmpty()
                || su_question.getSelectionModel().getSelectedItem() == null
                || su_answer.getText().isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        } else {
            String reqData = "INSERT INTO employee (username, pass, question, answer, date)"
                    + "VALUES(?,?,?,?,?)";
            connect = database.connectDB();
            try {

                String checkUsername = "SELECT username FROM employee WHERE username = '"
                    + su_username.getText() + "'";
                prepare = connect.prepareStatement(checkUsername);
                result = prepare.executeQuery();
                if(result.next())
                {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText(su_username.getText() + "is already taken");
                    alert.showAndWait();
                }
                else if(su_password.getText().length() < 8)
                {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid passwords, at least 8 characters needed");
                    alert.showAndWait();
                }
                else
                {
                    prepare = connect.prepareStatement(reqData);
                    prepare.setString(1, su_username.getText());
                    prepare.setString(2, su_password.getText());
                    prepare.setString(3, (String) su_question.getSelectionModel().getSelectedItem());
                    prepare.setString(4, su_answer.getText());
                    
                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    prepare.setString(5, String.valueOf(sqlDate));

                    prepare.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully requestered Account");
                    alert.showAndWait();

                    su_username.setText("");
                    su_password.setText("");
                    su_question.getSelectionModel().clearSelection();
                    su_answer.setText("");

                    TranslateTransition slider = new TranslateTransition();

                    slider.setNode(side_form);
                    slider.setToX(300);
                    slider.setDuration(Duration.seconds(.5));
                    slider.setOnFinished((ActionEvent e) -> {
                        side_alreadyHave.setVisible(false);
                        side_CreateBtn.setVisible(true);
                    });
                    slider.play();
                }
            } 
            catch (Exception e)  
            {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void switchForgotPass(ActionEvent event) {
        fp_questionForm.setVisible(true);
        si_loginForm.setVisible(false);

        forgetPassQuestionList();
    }
    public void forgetPassQuestionList()
    {
        List<String> listQ = new ArrayList<>();
        for(String data : questionList)
        {
            listQ.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(listQ);
        fp_question.setItems(listData);
    }

    public void reqLquestionList() {
        List<String> listQ = new ArrayList<>();
        for (String data : questionList) {
            listQ.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(listQ);
        su_question.setItems(listData);
    }

    @FXML
    void switchForm(ActionEvent event) {
        TranslateTransition slider = new TranslateTransition();

        if (event.getSource() == side_CreateBtn) {
            slider.setNode(side_form);
            slider.setToX(300);
            slider.setDuration(Duration.seconds(.5));
            slider.setOnFinished((ActionEvent e) -> {
                side_alreadyHave.setVisible(true);
                side_CreateBtn.setVisible(false);
                fp_questionForm.setVisible(false);
                si_loginForm.setVisible(true);
                np_newPassForm.setVisible(false);
                reqLquestionList();
            });
            slider.play();
        } else if (event.getSource() == side_alreadyHave) {
            slider.setNode(side_form);
            slider.setToX(0);
            slider.setDuration(Duration.seconds(.5));
            slider.setOnFinished((ActionEvent e) -> {
                side_alreadyHave.setVisible(false);
                side_CreateBtn.setVisible(true);
            });
            slider.play();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
