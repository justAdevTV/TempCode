package org.usfirst.frc.team4.robot.subsystems;

import org.usfirst.frc.team4.robot.RobotMap;
import org.usfirst.frc.team4.robot.commands.IntakeController;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Intake extends Subsystem {

	private VictorSP intakeRoller, intakeArm;

	public Intake() {
		super();

		intakeRoller = new VictorSP(RobotMap.kIntakeMotorRoller);
		intakeArm = new VictorSP(RobotMap.kIntakeMotorArm);
	}

	public void initDefaultCommand() {
		setDefaultCommand(new IntakeController());
	}

	public void setRollerSpeed(double speed) {
		intakeRoller.set(speed);
	}

	public void setArmAngle(double angle) {
		intakeArm.set(angle);
	}

	public void stopRoller(){
		intakeRoller.stopMotor();
	}
	
	public void stopArm(){
		intakeArm.stopMotor();
	}
	
	public void stop(){
		stopArm();
		stopRoller();
	}
	
}
