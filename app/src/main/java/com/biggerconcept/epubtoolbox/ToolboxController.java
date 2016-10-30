package com.biggerconcept.epubtoolbox;

import com.biggerconcept.epubtoolbox.exceptions.NoChoiceMadeException;
import com.biggerconcept.epubtoolbox.services.*;
import com.biggerconcept.epubtoolbox.actions.*;
import com.biggerconcept.epubtoolbox.utilities.PickUtility;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ToolboxController implements Initializable {
    public ToolboxService toolbox;
    public ConsoleService console;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
        toolbox = new ToolboxService();
        console = new ConsoleService(consoleView);
    }
    
    private Stage toolboxStage() {
        Stage stage = (Stage) consoleView.getScene().getWindow();
        return stage;
    }
    
    public File makeInputChoice(
        boolean dirChoice, String choice, String prompt)
        throws NoChoiceMadeException {

        if (dirChoice | isCollectionMode()) {
            return toolbox.chooseDirectory(
                    choice + " epubs " + prompt, toolboxStage());
        }
        else {
            return toolbox.chooseFile(
                    choice + " epub " + prompt, toolboxStage());
        }
    }
    
    @FXML
    public TreeView<String> consoleView;
    
    @FXML
    private void handleUnpackClick(MouseEvent event) {
        try {
            // Choose epub for expansion
            File inFile = makeInputChoice(false, "Choose", "for expansion");
            
            // Choose expansion location
            File outFileChoice = makeInputChoice(true, "Choose ",
                    " expansion location");
            
            // Expand into directory with epub name
            File outFile = new File(
                    outFileChoice.getAbsolutePath()
            );
            
             Action unpackAction = new UtilityAction(
                        "Unpack Utility", inFile, outFile, isCollectionMode());

             toolbox.performAction(unpackAction);
             console.out(unpackAction.getResult(), unpackAction.getTask());
        }
        catch (NoChoiceMadeException ncm) {
            // Do nothing
        }
        catch (Exception e) {
            console.err("Epub expansion", e);
        }
    }
    
    @FXML
    private void handlePackClick(MouseEvent event) {
        try {
            // Choose expanded epub(s) for packing
            File inFile = makeInputChoice(true, "Choose expanded",
                    "for packing");
            // Choose packed epub(s) output location
            File outFileChoice = makeInputChoice(true, "Choose packed",
                    "output location");
            
            // Add epub name to output location
            File outFile = new File(
                    outFileChoice.getAbsolutePath() 
                    + File.separator
                    + inFile.getName() 
                    + ".epub"
            );
            
            if (inFile.exists() && outFileChoice.exists()) {
                Action packAction = new UtilityAction(
                        "Pack Utility", inFile, outFile, isCollectionMode());

                toolbox.performAction(packAction);
                console.out(packAction.getResult(), packAction.getTask());
            }

        }
        catch (NoChoiceMadeException ncm) {
            // Do nothing
        }
        catch (Exception e) {
            console.err("Epub pack", e);
        }
    }
    
    @FXML
    private void handlePickClick(MouseEvent event) {
        try {
            File collectionLocation = makeInputChoice(true,
                    "Choose",
                    "collection location");

            String choices = toolbox.listPrompt(
                    "Pick ePubs from folder",
                    "Please enter the names of the ePubs"
                    + " you'd like to select (separated by commas):",
                    "i.e.: 'spain.epub, madrid.epub' etc.");

            String [] targets = choices.split(",");
            File outLocation = makeInputChoice(true, "Choose",
                    "selection output location");

            PickUtility pickUtility = new PickUtility(
                    collectionLocation, outLocation, targets);
            pickUtility.doJob();

            console.out(pickUtility.getResult(), "Pick ePubs from Folder");
        }
        catch (NoChoiceMadeException ncm) {
            // Do nothing
        }
        catch (Exception e) {
            console.err("Epub selection", e);
        }
    }
    
    @FXML
    private void handleEpubCheckClick(MouseEvent event) {
        try {
            // Choose epub(s) for epubcheck
            File inFile = makeInputChoice(false, "Choose", "for epub check");

            if (inFile.exists()) {
                Action epubCheck = new CheckerAction(
                        "Epub Checker", inFile, isCollectionMode());

                toolbox.performAction(epubCheck);
                console.out(epubCheck.getResult(), epubCheck.getTask());
            }

        }
        catch (NoChoiceMadeException ncm) {
            // Do nothing
        }
        catch (Exception e) {
            console.err("Epub check", e);
        }
    }
    
    @FXML
    private void handleImageCheckClick(MouseEvent event) {
        try { }
        catch (Exception e) {
            
        }
    }
    
    @FXML
    private void handleSizeCheckClick(MouseEvent event) {
        try { }
        catch (Exception e) {
            
        }
    }
    
    @FXML
    private void handleAllCheckClick(MouseEvent event) {
        try { }
        catch (Exception e) {
            
        }
    }
    
    @FXML
    private void handleArtefactRemove(ActionEvent event) {
        try { }
        catch (Exception e) {
            
        }
    }
    
    @FXML
    private void handleItunesMetaRemove(ActionEvent event) {
        try { }
        catch (Exception e) {
            
        }
    }

    private boolean isCollectionMode() {
        return false;
    }
}
