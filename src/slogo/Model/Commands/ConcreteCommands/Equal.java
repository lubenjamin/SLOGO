package slogo.Model.Commands.ConcreteCommands;

import slogo.Model.CommandInfrastructure.CommandDatabase;
import slogo.Model.Commands.Command;

/**
 * Subclass to create a LeftCommand
 *
 * @author Frank Tang
 */
public class Equal extends Command {
  //moves turtle to an absolute screen position, where (0, 0) is the center of the screen
  //returns the distance turtle moved

  private double returnArgValue;
  private Number firstTerm;
  private Number secondTerm;
  private static final int argumentsNeeded = 2;
  private CommandDatabase database;

  public Equal(CommandDatabase data) {
    super(data);
    database = data;


  }

  /**
   * Rotates a turtle by a counterclockwise rotation of a degree amount.
   */
  @Override
  public Integer executeAndReturnValue() {
    firstTerm = database.getParameterOne();
    secondTerm = database.getParameterTwo();
    if (Math.abs(firstTerm.doubleValue() - secondTerm.doubleValue()) < 0.00001) {
      returnArgValue = 1;
    } else {
      returnArgValue = 0;
    }

    System.out.println(returnArgValue);
//    System.out.println("turtle Y " + turtleObject.getTurtleY());
    return (int) this.returnArgValue;
  }
  @Override
  public int getArgumentsNeeded(){
    return this.argumentsNeeded;
  }


}


