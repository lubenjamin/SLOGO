package slogo.Model.Commands.MathOperations;

import slogo.Model.Commands.Command;

/**
 * Subclass to create a Sum Command
 *
 * @author Frank Tang
 */
public class RemainderCommand extends Command {

  private double firstTerm;
  private double secondTerm;
  private double returnArgValue;


  public RemainderCommand(double parameterOne, double parameterTwo) {
    firstTerm = parameterOne;
    secondTerm = parameterTwo;
  }


  /**
   * Moves the turtle forward by a pixel amount.
   */
  @Override
  public void execute() {
    returnArgValue = firstTerm % secondTerm;
//    System.out.println("turtle X " + turtleObject.getTurtleX());
//    System.out.println("turtle Y " + turtleObject.getTurtleY());

  }

  @Override
  public Integer returnArgValue() {
    return (int) this.returnArgValue;
  }

}

