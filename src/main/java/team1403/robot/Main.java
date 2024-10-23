package team1403.robot;

import edu.wpi.first.wpilibj.RobotBase;

/**
 * It is unlikely that anything will go here.
 *
 * <p>This is only a wrapper to bootstrap the WPI libraries.
 * The WPI library will construct our CougarRobot class and call
 * into it.
 *
 * <p>Our code is in the CougarRobotImpl class and its dependencies.
 */
public final class Main {
  /**
   * Class is not instantiatable.
   */
  private Main() {}

  /**
   * Robot program entry point.
   *
   * @param args Command line args from java execution.
   */
  public static void main(String... args) {
    RobotBase.startRobot(Robot::new);
  }
}
