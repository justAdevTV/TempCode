package org.usfirst.frc.team4.robot.subsystems;

import org.usfirst.frc.team4.robot.RobotMap;
import org.usfirst.frc.team4.robot.commands.Drive;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Chassis extends Subsystem {

	// Toggle Drive
	public enum DriveState {
		ARCADE, TANK;
	}
	// Drive Speeds
	public enum DriveSpeed {
		HIGH, LOW;
	}

	public DriveSpeed currentGear = DriveSpeed.HIGH;
	public DriveState driveState = DriveState.TANK;
	
	private Double kJERK_REDUCTION = .45;

	// TODO: Change to actual speed controller
	private VictorSP leftFwd, leftBwd, rightFwd, rightBwd;
	private RobotDrive drive;
	private Encoder leftEncoder, rightEncoder;
	private AnalogGyro gyro;

	public Chassis() {
		// Registers Subsystem
		super();

		leftFwd = new VictorSP(RobotMap.CHASSIS_MOTOR_LEFTFRONT);
		leftBwd = new VictorSP(RobotMap.CHASSIS_MOTOR_LEFTREAR);
		rightFwd = new VictorSP(RobotMap.CHASSIS_MOTOR_RIGHTFRONT);
		rightBwd = new VictorSP(RobotMap.CHASSIS_MOTOR_RIGHTREAR);

		leftFwd.setInverted(true);
		leftBwd.setInverted(true);
		rightFwd.setInverted(true);
		rightBwd.setInverted(true);

		drive = new RobotDrive(leftFwd, leftBwd, rightFwd, rightBwd);

		leftEncoder = new Encoder(RobotMap.CHASSIS_LEFT_ENCODER_FWD, RobotMap.CHASSIS_LEFT_ENCODER_BCK);
		rightEncoder = new Encoder(RobotMap.CHASSIS_RIGHT_ENCODER_FWD, RobotMap.CHASSIS_RIGHT_ENCODER_BCK);

		gyro = new AnalogGyro(RobotMap.GYRO);

		LiveWindow.addActuator("Chassis", "Front_Left Motor", leftFwd);
		LiveWindow.addActuator("Chassis", "Back Left Motor", leftBwd);
		LiveWindow.addActuator("Chassis", "Front Right Motor", rightFwd);
		LiveWindow.addActuator("Chassis", "Back Right Motor", rightBwd);
		LiveWindow.addSensor("Chassis", "Left Encoder", leftEncoder);
		LiveWindow.addSensor("Chassis", "Right Encoder", rightEncoder);
		LiveWindow.addSensor("Chassis", "Gyro", gyro);

	}

	public void initDefaultCommand() {
		setDefaultCommand(new Drive());
	}

	public void tankDrive(GenericHID c) {
		// Squared to make slower easier
		drive.tankDrive(driveFilter(c.getRawAxis(RobotMap.CONT_LY) * gearSetter(currentGear), kJERK_REDUCTION),
				driveFilter(c.getRawAxis(RobotMap.CONT_RY) * gearSetter(currentGear), kJERK_REDUCTION), true);
	}

	public void arcadeDrive(GenericHID stick) {
		// Squared to make slower speeds easier
		drive.arcadeDrive(driveFilter(stick.getRawAxis(RobotMap.CONT_LY) * gearSetter(currentGear), kJERK_REDUCTION),
				driveFilter(stick.getRawAxis(RobotMap.CONT_LX) * gearSetter(currentGear), kJERK_REDUCTION), true);
	}

	public void stop() {
		drive.stopMotor();
	}

	// Reduces Jerk
	public double driveFilter(double n, double t) {
		return Math.abs(n) > t ? n : 0;
	}

	public double gearSetter(DriveSpeed s) {
		return s == DriveSpeed.HIGH ? 1.0 : .75;
	}
	
	public double getDistance() {
		return (leftEncoder.getDistance() + rightEncoder.getDistance()) / 2;
	}
	
	public double getAngle(){
		return gyro.getAngle();
	}

	public void reset() {
		gyro.reset();
		leftEncoder.reset();
		rightEncoder.reset();
	}
	
	public void log() {
		SmartDashboard.putNumber("Distance", (leftEncoder.getDistance() + rightEncoder.getDistance()) / 2);
		SmartDashboard.putNumber("Left Speed", leftEncoder.getRate());
		SmartDashboard.putNumber("Right Speed", rightEncoder.getRate());
		SmartDashboard.putNumber("Average Speed", (rightEncoder.getRate() + leftEncoder.getRate()) / 2);
		SmartDashboard.putNumber("Gyro", gyro.getAngle());
	}	
}
