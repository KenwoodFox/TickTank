package ticktank;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import ticktank.motionProfile.ProfileOutput;
import ticktank.motionProfile.ProfileSource;

/**
 * 
 * @author samcf_000
 * @param _motorA
 *            A SpeedController to set speed
 * @param _motorB
 *            A SpeedController to set speed
 * @param _enc
 *            An Encoder to read distance from
 */
public class DriveSide implements ProfileSource, ProfileOutput {

	private ArrayList<SpeedController> motors;
	private Encoder enc;

	public DriveSide(ArrayList<SpeedController> _motors, Encoder _enc) {
		this.enc = _enc;

		motors = new ArrayList<SpeedController>();

		// Copy motors from arg list to the private list
		for (SpeedController motor : _motors) {
			addMotor(motor);
		}
	}

	public void addMotor(SpeedController _motor) {
		motors.add(_motor);
	}

	@Override
	public void setSpeed(double speed) {
		for (SpeedController motor : this.motors) {
			motor.set(speed);
		}
	}

	@Override
	public double getDistance() {
		return enc.getDistance();
	}

	public double getSpeed() {
		return motors.get(0).get();
	}

	public void zeroEncoder() {
		enc.reset();
	}
}
