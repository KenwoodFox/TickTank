package ticktank;

import java.util.ArrayList;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import pid.PIDOutput;
import pid.PIDParameters;
import pid.PIDSource;
import ticktank.commands.DriveWithJoysticks;
import ticktank.motionProfile.DriveParameters;

public class TickTank extends Subsystem implements PIDSource, PIDOutput {
	public Joystick leftStick, rightStick;
	private Encoder leftEncoder, rightEncoder;
	private ArrayList<SpeedController> leftMotors, rightMotors;
	public DriveSide left, right;
	public Settings config;
	public AHRS navx;
	public PIDParameters turnParams;
	private DoubleSolenoid sol;

	public DriveParameters leftDriveParams;
	public DriveParameters rightDriveParams;

	/**
	 * Generates a TickTank using the specified Settings object.
	 *
	 * @param _settings
	 *            The settings that define the TickTank object
	 */
	public TickTank(Settings _settings) {
		this.config = _settings;
		int pwm = 0; // TODO Allow user to define available pwm ports

		leftMotors = makeMotors(config.motorCount, pwm, config.controllerType);
		rightMotors = makeMotors(config.motorCount, pwm + config.motorCount, config.controllerType);

		invertMotors(leftMotors, config.leftInv);
		invertMotors(rightMotors, config.rightInv);

		setLeftStick(config.leftStick);
		setRightStick(config.rightStick);

		if (config.hasEncoders) {
			leftEncoder = new Encoder(config.leftEncoderA, config.leftEncoderB);
			rightEncoder = new Encoder(config.rightEncoderA, config.rightEncoderB);

			leftEncoder.setDistancePerPulse(config.dpp);
			rightEncoder.setDistancePerPulse(config.dpp);
		}

		if (config.hasGyro) {
			navx = new AHRS(SPI.Port.kMXP);
		}

		left = new DriveSide(leftMotors, leftEncoder, config.leftInvEncoder);
		right = new DriveSide(rightMotors, rightEncoder, config.rightInvEncoder);

		this.turnParams = config.turnParams;
		this.leftDriveParams = config.leftParams;
		this.rightDriveParams = config.rightParams;

		if (config.hasGears) {
			this.sol = new DoubleSolenoid(config.solForward, config.solReverse);
		}

		this.turnParams = config.turnParams;
	}

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new DriveWithJoysticks(this));
	}

	/**
	 * Generate an array of motors.
	 *
	 * @param count
	 *            The number of motors on each side
	 * @param pwmStartPort
	 *            The PWM port to begin at. Will occupy the next `count` ports.
	 * @param type
	 *            The type of SpeedController to use
	 * @return An `ArrayList` of `count` motors assigned to the correct PWM
	 *         ports and type.
	 */
	private static ArrayList<SpeedController> makeMotors(int count, int pwmStartPort, ControllerType type) {

		ArrayList<SpeedController> motors = new ArrayList<SpeedController>(count);

		for (int port = pwmStartPort; port < pwmStartPort + count; port++) {

			switch (type) {
			case VICTORSP:
				motors.add(new VictorSP(port));
				break;

			case VICTOR:
				motors.add(new Victor(port));
				break;

			case TALON:
				motors.add(new Talon(port));
				break;

			case JAGUAR:
				motors.add(new Jaguar(port));
				break;

			case SPARK:
				motors.add(new Spark(port));
			}
		}

		return motors;
	}

	/**
	 * Reverse the direction of the given motors.
	 * 
	 * @param motors An array of motors to be inverted
	 * @param inv Invert the motors only if inv is true
	 */
	private void invertMotors(ArrayList<SpeedController> motors, boolean inv) {
		for (SpeedController motor : motors) {
			motor.setInverted(inv);
		}
	}

	private void setLeftStick(Joystick stick) {
		this.leftStick = stick;
	}

	private void setRightStick(Joystick stick) {
		this.rightStick = stick;
	}

	@Deprecated
	public void setLeftSpeed(double speed) {
		left.setSpeed(speed);
	}

	@Deprecated
	public void setRightSpeed(double speed) {
		right.setSpeed(speed);
	}

	public void setSpeeds(double leftSpeed, double rightSpeed) {
		left.setSpeed(leftSpeed);
		right.setSpeed(rightSpeed);
	}

	public void stop() {
		setSpeeds(0, 0);
	}

	@Deprecated
	public Encoder getLeftEncoder() {
		return leftEncoder;
	}

	@Deprecated
	public Encoder getRightEncoder() {
		return rightEncoder;
	}

	public void zeroEncoders() {
		left.zeroEncoder();
		right.zeroEncoder();
	}

	private void setSol(int direction) {
		switch (direction) {
		case 1:
			sol.set(DoubleSolenoid.Value.kForward);
			break;
		case -1:
			sol.set(DoubleSolenoid.Value.kReverse);
			break;
		default:
			sol.set(DoubleSolenoid.Value.kOff);
			break;
		}
	}

	// TODO Reorganize shifting
	public void shiftUp() {
		setSol(1);
	}

	public void shiftDown() {
		setSol(-1);
	}

	@Override
	/**
	 * Set turn speed
	 * 
	 * @param speed
	 *            The speed at which the robot will spin to the right
	 */
	public void pidSet(double speed) {
		this.setSpeeds(speed, -speed);
	}

	@Override
	public double pidGet() {
		return navx.getAngle();
	}
}
