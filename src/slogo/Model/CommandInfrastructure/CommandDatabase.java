package slogo.Model.CommandInfrastructure;

import static java.util.Map.entry;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javafx.beans.property.ListProperty;
import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;
import javafx.util.Pair;
import slogo.Model.Commands.BooleanOperations.AndCommand;
import slogo.Model.Commands.BooleanOperations.EqualCommand;
import slogo.Model.Commands.BooleanOperations.GreaterThanCommand;
import slogo.Model.Commands.BooleanOperations.LessThanCommand;
import slogo.Model.Commands.BooleanOperations.NotCommand;
import slogo.Model.Commands.BooleanOperations.NotEqualCommand;
import slogo.Model.Commands.BooleanOperations.OrCommand;
import slogo.Model.Commands.Command;
import slogo.Model.Commands.ControlCommands.DoTimesCommand;
import slogo.Model.Commands.ControlCommands.IfCommand;
import slogo.Model.Commands.ControlCommands.MakeVariableCommand;
import slogo.Model.Commands.ControlCommands.RepeatCommand;
import slogo.Model.Commands.MathOperations.ArcTangentCommand;
import slogo.Model.Commands.MathOperations.CosineCommand;
import slogo.Model.Commands.MathOperations.DifferenceCommand;
import slogo.Model.Commands.MathOperations.MinusCommand;
import slogo.Model.Commands.MathOperations.NaturalLogCommand;
import slogo.Model.Commands.MathOperations.PiCommand;
import slogo.Model.Commands.MathOperations.PowerCommand;
import slogo.Model.Commands.MathOperations.ProductCommand;
import slogo.Model.Commands.MathOperations.QuotientCommand;
import slogo.Model.Commands.MathOperations.RandomCommand;
import slogo.Model.Commands.MathOperations.RemainderCommand;
import slogo.Model.Commands.MathOperations.SineCommand;
import slogo.Model.Commands.MathOperations.SumCommand;
import slogo.Model.Commands.MathOperations.TangentCommand;
import slogo.Model.Commands.TurtleCommands.BackCommand;
import slogo.Model.Commands.TurtleCommands.ClearScreenCommand;
import slogo.Model.Commands.TurtleCommands.ForwardCommand;
import slogo.Model.Commands.TurtleCommands.HideTurtleCommand;
import slogo.Model.Commands.TurtleCommands.HomeCommand;
import slogo.Model.Commands.TurtleCommands.LeftCommand;
import slogo.Model.Commands.TurtleCommands.PenDownCommand;
import slogo.Model.Commands.TurtleCommands.PenUpCommand;
import slogo.Model.Commands.TurtleCommands.RightCommand;
import slogo.Model.Commands.TurtleCommands.SetHeadingCommand;
import slogo.Model.Commands.TurtleCommands.SetPositionCommand;
import slogo.Model.Commands.TurtleCommands.ShowTurtleCommand;
import slogo.Model.Commands.TurtleCommands.TowardsCommand;
import slogo.Model.Commands.TurtleQueries.HeadingCommand;
import slogo.Model.Commands.TurtleQueries.IsPenDownCommand;
import slogo.Model.Commands.TurtleQueries.IsShowingCommand;
import slogo.Model.Commands.TurtleQueries.XCoordinateCommand;
import slogo.Model.Commands.TurtleQueries.YCoordinateCommand;
import slogo.Model.Commands.ControlCommands.IfElseCommand;
import slogo.Model.Commands.ControlCommands.ForCommand;

import slogo.Model.ModelParser;
import slogo.Model.TurtleData;

public class CommandDatabase {

  private String targetVariable;
  private static final Integer zeroParameterNeeded = 0;
  private static final Integer oneParameterNeeded = 1;
  private static final Integer twoParametersNeeded = 2;
  private Number parameterOne;
  private Number parameterTwo;
  private Map<String, Pair<Command, Integer>> POSSIBLE_COMMANDS_MAP;
  private MapProperty<String, Number> VARIABLE_MAP = new SimpleMapProperty(FXCollections.observableMap(new LinkedHashMap<String, Number>()));
  private ListProperty<String> HISTORY_LIST = new SimpleListProperty(FXCollections.observableList(new ArrayList<>()));
  private ListProperty<Command> COMMAND_LIST = new SimpleListProperty<>();

  private List<TurtleData> Turtle_List = new ArrayList<>();
  private TurtleData targetTurtle;
  private ModelParser originParser;

  public CommandDatabase(TurtleData turtle){
    targetTurtle = turtle;
  }


  /**
   * Prompt the user to make a bet from a menu of choices.
   */
  public void addParser (ModelParser parser) {
    originParser = parser;
    updateCommandMap();
  }

  /**
   * Prompt the user to make a bet from a menu of choices.
   */
  public Command makeZeroParameterCommand (String targetCommand) {
    updateCommandMap();
    return POSSIBLE_COMMANDS_MAP.get(targetCommand).getKey();
  }

  /**
   * Prompt the user to make a bet from a menu of choices.
   */
  public void setVariableName(String targetCommand) {
    targetVariable = targetCommand;
  }


  /**
   * Prompt the user to make a bet from a menu of choices.
   */
  public Command makeOneParameterCommand (String targetCommand, Number value) {
    parameterOne = value;
    updateCommandMap();
    return POSSIBLE_COMMANDS_MAP.get(targetCommand).getKey();
  }

  /**
   * Prompt the user to make a bet from a menu of choices.
   */
  public Command makeTwoParameterCommand (String targetCommand, Number value1, Number value2) {
    parameterOne = value1;
    parameterTwo = value2;
    updateCommandMap();
    return POSSIBLE_COMMANDS_MAP.get(targetCommand).getKey();
  }

  /**
   * Prompt the user to make a bet from a menu of choices.
   */
  public Integer getAmountOfParametersNeeded(String targetCommand) {
    return POSSIBLE_COMMANDS_MAP.get(targetCommand).getValue();
  }

  /**
   * Prompt the user to make a bet from a menu of choices.
   */
  public boolean isInCommandMap(String targetCommand) {
    return POSSIBLE_COMMANDS_MAP.containsKey(targetCommand);
  }

  /**
   * Prompt the user to make a bet from a menu of choices.
   */
  public MapProperty getVariableMap() {
    return this.VARIABLE_MAP;
  }

  public void bindHistory(ListProperty displayedHistory) {
    displayedHistory.bind(HISTORY_LIST);
  }

  public void bindCommands(MapProperty displayedCommands) {
    displayedCommands.bind(COMMAND_LIST);
  }

  public void bindVariables(MapProperty displayedVariables) {
    displayedVariables.bind(VARIABLE_MAP);
  }

  public void addToHistory(String command) {
    HISTORY_LIST.getValue().add(command);
  }

  public void addToVariableMap(String command, Number expression) {
    this.VARIABLE_MAP.putIfAbsent(command, expression);
    this.VARIABLE_MAP.put(command, expression);
  }


  /**
   * Prompt the user to make a bet from a menu of choices.
   */
  private void updateCommandMap() {
    POSSIBLE_COMMANDS_MAP = Map.ofEntries(
        //Zero Parameter Commands
        entry("PenUp", new Pair<>(new PenUpCommand(targetTurtle), zeroParameterNeeded)),
        entry("PenDown", new Pair<>(new PenDownCommand(targetTurtle), zeroParameterNeeded)),
        entry("ShowTurtle", new Pair<>(new ShowTurtleCommand(targetTurtle), zeroParameterNeeded)),
        entry("HideTurtle", new Pair<>(new HideTurtleCommand(targetTurtle), zeroParameterNeeded)),
        entry("Home", new Pair<>(new HomeCommand(targetTurtle), zeroParameterNeeded)),
        entry("ClearScreen", new Pair<>(new ClearScreenCommand(targetTurtle), zeroParameterNeeded)),
        entry("Pi", new Pair<>(new PiCommand(), zeroParameterNeeded)),
        entry("XCoordinate", new Pair<>(new XCoordinateCommand(targetTurtle), zeroParameterNeeded)),
        entry("YCoordinate", new Pair<>(new YCoordinateCommand(targetTurtle), zeroParameterNeeded)),
        entry("Heading", new Pair<>(new HeadingCommand(targetTurtle), zeroParameterNeeded)),
        entry("IsShowing", new Pair<>(new IsShowingCommand(targetTurtle), zeroParameterNeeded)),
        entry("IsPenDown", new Pair<>(new IsPenDownCommand(targetTurtle), zeroParameterNeeded)),

        //One Parameter Commands
        entry("Forward", new Pair<>(new ForwardCommand(targetTurtle, parameterOne), oneParameterNeeded)),
        entry("Backward", new Pair<>(new BackCommand(targetTurtle, parameterOne), oneParameterNeeded)),
        entry("Left", new Pair<>(new LeftCommand(targetTurtle, parameterOne), oneParameterNeeded)),
        entry("Right", new Pair<>(new RightCommand(targetTurtle, parameterOne), oneParameterNeeded)),
        entry("SetHeading", new Pair<>(new SetHeadingCommand(targetTurtle, parameterOne), oneParameterNeeded)),
        entry("Random", new Pair<>(new RandomCommand(parameterOne), oneParameterNeeded)),
        entry("Sine", new Pair<>(new SineCommand(parameterOne), oneParameterNeeded)),
        entry("Cosine", new Pair<>(new CosineCommand(parameterOne), oneParameterNeeded)),
        entry("Tangent", new Pair<>(new TangentCommand(parameterOne), oneParameterNeeded)),
        entry("ArcTangent", new Pair<>(new ArcTangentCommand(parameterOne), oneParameterNeeded)),
        entry("NaturalLog", new Pair<>(new NaturalLogCommand(parameterOne), oneParameterNeeded)),
        entry("Minus", new Pair<>(new MinusCommand(parameterOne), oneParameterNeeded)),
        entry("MakeVariable", new Pair<>(new MakeVariableCommand(targetVariable, parameterOne, this), oneParameterNeeded)),
        //Two Parameter Commands
        entry("Sum", new Pair<>(new SumCommand(parameterOne, parameterTwo), twoParametersNeeded)),
        entry("Difference", new Pair<>(new DifferenceCommand(parameterOne, parameterTwo), twoParametersNeeded)),
        entry("Product", new Pair<>(new ProductCommand(parameterOne, parameterTwo), twoParametersNeeded)),
        entry("Quotient", new Pair<>(new QuotientCommand(parameterOne, parameterTwo), twoParametersNeeded)),
        entry("Remainder", new Pair<>(new RemainderCommand(parameterOne, parameterTwo), twoParametersNeeded)),
        entry("SetTowards", new Pair<>(new TowardsCommand(targetTurtle, parameterOne, parameterTwo), twoParametersNeeded)),
        entry("SetPosition", new Pair<>(new SetPositionCommand(targetTurtle, parameterOne, parameterTwo), twoParametersNeeded)),
        entry("Power", new Pair<>(new PowerCommand(parameterOne, parameterTwo), twoParametersNeeded)),
        entry("And", new Pair<>(new AndCommand(parameterOne, parameterTwo), twoParametersNeeded)),
        entry("Or", new Pair<>(new OrCommand(parameterOne, parameterTwo), twoParametersNeeded)),
        entry("NotEqual", new Pair<>(new NotEqualCommand(parameterOne, parameterTwo), twoParametersNeeded)),
        entry("Equal", new Pair<>(new EqualCommand(parameterOne, parameterTwo), twoParametersNeeded)),
        entry("Not", new Pair<>(new NotCommand(parameterOne, parameterTwo), twoParametersNeeded)),
        entry("LessThan", new Pair<>(new LessThanCommand(parameterOne, parameterTwo), twoParametersNeeded)),
        entry("GreaterThan", new Pair<>(new GreaterThanCommand(parameterOne, parameterTwo), twoParametersNeeded)),
        //
        // TODO: make into reflection



        //Control Parameter Commands
        entry("Repeat", new Pair<>(new RepeatCommand(parameterOne, originParser), oneParameterNeeded)),
        entry("DoTimes", new Pair<>(new DoTimesCommand(originParser, this), zeroParameterNeeded)),
        entry("If", new Pair<>(new IfCommand(parameterOne, originParser), oneParameterNeeded)),
        entry("IfElse", new Pair<>(new IfElseCommand(parameterOne, originParser), oneParameterNeeded)),
        entry("For", new Pair<>(new ForCommand(originParser, this), zeroParameterNeeded))
    );

  }
}

