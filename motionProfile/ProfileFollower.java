package ticktank.motionProfile;

import ticktock.lib.Tickable;

/**
 * 
 * @author samcf_000
 *
 */

public class ProfileFollower implements Tickable {
	// Profile to be followed
	Profile profile;
	// Step of profile
	private int i = 0;
	// Source for position feedback
	public ProfileSource source;
	// Output for the motor power
	public ProfileOutput output;
	// Constants + Gains
	double kV, kA, kP;
	double endPosition;
	public volatile boolean onTarget;
	public volatile double error;

	boolean isInverted;

	public ProfileFollower(Profile p, ProfileSource source, ProfileOutput output, DriveParameters params,
			boolean _isInverted) {
		this.profile = p;
		this.source = source;
		this.output = output;
		this.kV = params.kV;
		this.kA = params.kA;
		this.kP = params.kP;

		this.endPosition = p.get(p.length() - 1).position;

		this.isInverted = _isInverted;
	}

	public void restart() {
		this.i = 0;
	}

	@Override
	public void update() {
		// Make sure i is within bounds of profile
		if (i < 0) {
			i = 0;
		} else if (i >= profile.length()) {
			i = profile.length() - 1;
		}
		// Get position from the profile
		double position = profile.get(i).position;
		// Determine if we are on target. Allowing error of +/- .1
		onTarget = Math.abs(endPosition + source.getDistance()) < .1;

		error = endPosition - source.getDistance();

		// Calculate the acceleration and velocity feedforward
		double v = kV * profile.get(i).speed;
		// Calculate the correction
		double correction = kP * (position - source.getDistance());
		// Set the speed of the motor with correction
		if (isInverted) {
			output.setSpeed(-(v + correction));
		} else {
			output.setSpeed(v + correction);
		}
		// Increment our loop counter
		i++;
	}
}
