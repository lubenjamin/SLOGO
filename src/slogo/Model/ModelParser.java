package slogo.Model;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.regex.Pattern;
import slogo.Model.CommandInfrastructure.CommandDatabase;
import slogo.Model.CommandInfrastructure.CommandProducer;

public class ModelParser {

  private enum ParserEnum{
    Constant,
    Variable,
    Command,
    List,
    Comment
  }

  private ParserEnum symbolName;

  /**
   * Simple parser based on regular expressions that matches input strings to kinds of program elements.
   * Based off of ProgramParser by Robert Duvall
   * 2/20/2020
   * @author Frank Tang
   */

  private String commandFromController;
  private static final String RESOURCES_PACKAGE = "languages/";
  private static final String REGEX_SYNTAX = "Syntax";
  private List<Entry<String, Pattern>> mySymbols;
  private CommandDatabase commandDatabase;
  private CommandProducer commandProducer;
  private int argumentThreshold;
  private List<String> linesArray;
  private int currentLinesIndex;


  public ModelParser(String language, CommandDatabase commandData){
    setUpModelParserLanguage(language);

    commandDatabase = commandData;
    commandData.addParser(this);
    commandProducer = new CommandProducer(commandData);
  }

  public void setUpModelParserLanguage(String language){
    mySymbols = new ArrayList<>();
    addPatterns(language);
    addPatterns(REGEX_SYNTAX);

  }


  /**
   * Adds the given resource file to this language's recognized types
   */
  public void addPatterns (String syntax) {
    ResourceBundle resources = ResourceBundle.getBundle(RESOURCES_PACKAGE + syntax);
    for (String key : Collections.list(resources.getKeys())) {
      String regex = resources.getString(key);
      mySymbols.add(new SimpleEntry<>(key,
          // THIS IS THE IMPORTANT LINE
          Pattern.compile(regex, Pattern.CASE_INSENSITIVE)));
    }
  }

  /**
   * Returns language's type associated with the given text if one exists
   */
  public String getSymbol (String text) {
    final String ERROR = "NO MATCH";
    for (Entry<String, Pattern> e : mySymbols) {
      if (match(text, e.getValue())) {
        return e.getKey();
      }
    }
    // FIXME: perhaps throw an exception instead
    return ERROR;
  }

  // utility function that reads given file and returns its entire contents as a single string
  public String readFileToString (String inputSource) {
    try {
      // this one line is dense, hard to read, and throws exceptions so better to wrap in method
      return new String(Files.readAllBytes(Paths.get(new URI(inputSource))));
    }
    catch (URISyntaxException | IOException e) {
      // NOT ideal way to handle exception, but this is just a simple test program
      System.out.println("ERROR: Unable to read input file " + e.getMessage());

      //potential error pop-up code
//      String errorMessage = "ERROR: Unable to read input f ile " + e.getMessage();
//      Alert alert = new Alert(Alert.AlertType.ERROR);
//      alert.setTitle("Error");
//      alert.setHeaderText(errorMessage);
//      Platform.runLater(alert::showAndWait);
      return "";
    }
  }

  // Returns true if the given text matches the given regular expression pattern
  private boolean match (String text, Pattern regex) {
    // THIS IS THE IMPORTANT LINE
    return regex.matcher(text).matches();
  }

  public void initializeNewParserTextandParse (List<String> lines) {
    linesArray = lines;
    parseText(lines);
  }

  // given some text, prints results of parsing it using the given language
  public void parseText (List<String> lines) {
    //System.out.println(lines);
    Stack<String> commandStack = new Stack<>();
    Stack<Integer> argumentStack = new Stack<>();
    for (int index = 0; index < lines.size(); index++) {
      if (lines.get(index).trim().length() > 0) {
        currentLinesIndex = index;
//        System.out.println(currentLinesIndex);
        //enum stuff that will probably used for the final implementation
//        System.out.print(this.getSymbol(line));
//        symbolName = ParserEnum.valueOf(this.getSymbol(line));
//        switch (symbolName){
//          case Constant:
//            argumentStack.push(Integer.parseInt(line));
//          case Variable:
//          case Command:
//            commandStack.push(this.getSymbol(line));
//          case Comment:
//          case List:
//        }

        if(this.getSymbol(lines.get(index)).equals("Constant")){
          argumentStack.push(Integer.parseInt(lines.get(index)));
        }
        else if(commandDatabase.isInCommandMap(this.getSymbol(lines.get(index)))) {
          commandStack.push(this.getSymbol(lines.get(index)));
          argumentThreshold = argumentStack.size() + commandDatabase.getAmountOfParametersNeeded(commandStack.peek());
        }
        else if(this.getSymbol(lines.get(index)).equals("ListStart")){
          int listEnd = lines.subList(index,lines.size()).indexOf("]");
          index = index + listEnd;
//          System.out.println("lineend " + listEnd);
//          System.out.println("test");
          continue;
        }
        System.out.println("Before Parse: " + commandStack);
        System.out.println("Before Parse: " + argumentStack);
        commandProducer.parseStacks(commandStack, argumentStack, argumentThreshold);
        System.out.println("After Parse: " + commandStack);
        System.out.println("After Parse: " + argumentStack);
      }
    }

  }

  public List<String> getLinesArray(){
    System.out.println(linesArray);
    return linesArray;
  }

  public int getCurrentLinesIndex(){
    return currentLinesIndex;
  }

}
