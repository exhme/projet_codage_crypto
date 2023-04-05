package com.chargo.controllers;

import com.chargo.Carte;
import com.chargo.helper.Helper;
import com.chargo.helper.IOHelper;
import com.chargo.ui.GUI;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class GUIController implements Initializable {
    protected Stage stage;
    protected Helper helper;
    protected IOHelper ioHelper;

    @FXML
    private TextArea textArea1, textArea2;

    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        this.stage = GUI.stage;
        this.ioHelper = new IOHelper();
        this.helper = new Helper();
    }


    @FXML
    public void crypt_message(MouseEvent mouseEvent) {

        // ENCODED MESSAGE
        char[] message = this.textArea1.getText().toCharArray();


        if (mouseEvent.getButton() == MouseButton.PRIMARY){


            // CREATE CARD
            ArrayList<Carte> cards = helper.createCardInOrder();
            Collections.shuffle(cards);

            this.ioHelper.exportCardOrder(cards);

            // TODO crypt message here (after saving secret key)

            // SHOW MESSAGE IN TEXT AREA
            this.textArea2.setText("TODO - cryped message here");
        }
    }

    @FXML
    public void decrypt_message(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY){
            FileChooser fc = new FileChooser();
            File file = fc.showOpenDialog(stage);

            ArrayList<Carte> secretCardsOrder = this.ioHelper.importCardOrder(file.toString());

            ArrayList<Integer> keyFlowImport = this.helper.generateKeyFlow(this.textArea1.getText().length(), secretCardsOrder);
            char[] keyImport = this.helper.intToCharTab(keyFlowImport);


            System.out.println("charArray : " + this.textArea1.getText());
            char[] encodedMessageChar = this.textArea1.getText().toCharArray();
            char[] decodedMessage = this.helper.decode(encodedMessageChar,keyImport); // TODO - here is decoded char array

            System.out.println("decodedMessageString : " + new String(decodedMessage));
            this.textArea2.setText("TODO - decrypted message here");
        }
    }
}
