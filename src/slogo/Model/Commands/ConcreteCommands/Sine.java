package slogo.Model.Commands.ConcreteCommands;

import slogo.Model.CommandInfrastructure.CommandDatabase;
import slogo.Model.Commands.Command;

/**
 * Subclass to create a LeftCommand
 *
 * @author Frank Tang
 */
public class Sine extends Command {


  private double returnArgValue;
  private Number amountOfDegrees;
  private static final int argumentsNeeded = 1;
  private CommandDatabase database;



  public Sine(CommandDatabase data) {
    super(data);
    database = data;


  }

  /**
   * Rotates a turtle by a counterclockwise rotation of a degree amount.
   */
  @Override
  public Double executeAndReturnValue() {
    amountOfDegrees = database.getParameterStack().pop();
    returnArgValue = Math.sin(Math.toRadians(amountOfDegrees.doubleValue()));
    System.out.println(returnArgValue);
    return this.returnArgValue;
  }

  @Override
  public int getArgumentsNeeded(){
    return this.argumentsNeeded;
  }


}


