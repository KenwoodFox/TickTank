package ticktank;

import java.util.ArrayList;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.SpeedController;
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
	 * Your constructor should usually do the following things:
	 * 1. Instantiate the left and right DriveSides
	 * 2. Instantiate the encoders
	 * 3. Set distance per pulse for encoders
	 * 4. Create a NavX object
	 * 5. Set parameters for turning and drive sides
	 * 6. Create the solenoids for shifting
	 * 7. Set turn params
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
