package ticktank.motionProfile;

/**
 * A ProfileParameters object contains all of the parameters necessary to build
 * a motion profile. You can pass an instance of ProfileParameters to the
 * profile constructor.
 * 
 * @author Sam
 * @param d
 *            The maximum distance of the profile (ft)
 * @param v
 *            The maximum allowable maxVelocity (ft/s)
 * @param a
 *            The maximum allowable maxAccel (ft/s/s)
 * @param j
 *            The maximum allowable maxJerk (ft/s/s/s)
 */
public class ProfileParameters {
	public double distance, maxVelocity, maxAccel, maxJerk, margin;

	public static final double DEFAULT_MARGIN = 1;

	public ProfileParameters(double d, double v, double a, double j, double _margin) {
		distance = d;
		maxVelocity = v;
		maxAccel = a;
		maxJerk = j;
		margin = _margin;
	}

	public ProfileParameters(double d, double v, double a, double j) {
		this(d, v, a, j, DEFAULT_MARGIN);
	}
}
