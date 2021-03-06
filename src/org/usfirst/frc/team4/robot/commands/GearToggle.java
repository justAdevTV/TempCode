package org.usfirst.frc.team4.robot.commands;

import org.usfirst.frc.team4.robot.Robot;
import org.usfirst.frc.team4.robot.ControllerConstants;
import org.usfirst.frc.team4.robot.subsystems.Chassis.DriveSpeed;

import edu.wpi.first.wpilibj.Joystick.RumbleType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GearToggle extends Command {

	public GearToggle() {
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		if (Robot.oi.R1(ControllerConstants.driveController)) {
			
			if (Robot.chassis.currentGear != DriveSpeed.HIGH){
				ControllerConstants.driveController.setRumble(RumbleType.kLeftRumble, .75f);
				ControllerConstants.driveController.setRumble(RumbleType.kRightRumble, .75f);
	
				Timer.delay(.15);
	
				ControllerConstants.driveController.setRumble(RumbleType.kLeftRumble, 0);
				ControllerConstants.driveController.setRumble(RumbleType.kRightRumble, 0);
			}
			
			Robot.chassis.currentGear = DriveSpeed.HIGH;
			
		} else if (Robot.oi.L1(ControllerConstants.driveController)) {

			if (Robot.chassis.currentGear != DriveSpeed.LOW){
				ControllerConstants.driveController.setRumble(RumbleType.kLeftRumble, .75f);
				ControllerConstants.driveController.setRumble(RumbleType.kRightRumble, .75f);
	
				Timer.delay(.15);
	
				ControllerConstants.driveController.setRumble(RumbleType.kLeftRumble, 0);
				ControllerConstants.driveController.setRumble(RumbleType.kRightRumble, 0);
			}
			
			Robot.chassis.currentGear = DriveSpeed.LOW;

		} else {
			System.out.println("No Button Mapped");
		}
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return true;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
