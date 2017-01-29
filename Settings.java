package ticktank;

import edu.wpi.first.wpilibj.Joystick;
import pid.PIDParameters;
import ticktank.motionProfile.DriveParameters;

/**
 * Contains all of the configuration variables for the tank drive. You should
 * create an instance of this class in the main robot init, load it up with
 * objects, then pass it on to the Drive module.
 */
public class Settings {
	// Poorly named class
	public Joystick leftStick, rightStick;
	public int motorCount;
	public ControllerType controllerType;
	public boolean leftInv, rightInv;
	public boolean leftInvEncoder, rightInvEncoder;
	public int leftEncoderA, leftEncoderB;
	public int rightEncoderA, rightEncoderB;
	public boolean hasGyro;
	public double dpp;
	public DriveParameters leftParams, rightParams;
	public PIDParameters turnParams;
	public int solForward, solReverse;
	public boolean hasEncoders;
	public boolean hasGears;

	public Settings() {
		motorCount = 2;
		leftInv = false;
		rightInv = false;
		leftInvEncoder = false;
		rightInvEncoder = false;
		controllerType = ControllerType.VICTORSP;
		hasGyro = false;
		hasGears = false;
		dpp = 1;

		// Default port assignments
		hasEncoders = true;
		leftEncoderA = 0;
		leftEncoderB = 1;
		rightEncoderA = 2;
		rightEncoderB = 3;
	}
}
