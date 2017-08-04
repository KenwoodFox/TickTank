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

public abstract class TickTank extends Subsystem implements PIDSource, PIDOutput {
	public Joystick leftStick, rightStick;
	private Encoder leftEncoder, rightEncoder;
	private ArrayList<SpeedController> leftMotors, rightMotors = new ArrayList<SpeedController>();
	public DriveSide left, right;
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
	public TickTank(Joystick _leftStick, Joystick _rightStick) {

		for (int i = 0; i < 2; i++) {
			leftMotors.add(new VictorSP(i));
			rightMotors.add(new VictorSP(i + 2));
		}

		navx = new AHRS(Port.kMXP);

		this.leftStick = _leftStick;
		this.rightStick = _rightStick;
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
