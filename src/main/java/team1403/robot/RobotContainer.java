// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package team1403.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

import team1403.robot.Motor;
import team1403.robot.Constants;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {

  private Motor m_motor;
  private final CommandXboxController m_driverController;

  private Command m_teleopCommand;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings

    m_motor = new Motor();
    m_driverController = new CommandXboxController(Constants.Driver.pilotPort);

    m_driverController.rightBumper().and(() -> m_motor.m_atBottom).onTrue(new InstantCommand(() -> m_motor.setUp()));
    m_driverController.rightBumper().and(() -> m_motor.m_atTop).onTrue(new InstantCommand(() -> m_motor.setDown()));

  }
  
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */

  public Command getTeleopCommand() {
    return m_teleopCommand;
  }
}
