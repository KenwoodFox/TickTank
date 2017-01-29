package ticktank.motionProfile;

public class DriveParameters {
	public double kP, kV, kA;

	public DriveParameters(double _kV, double _kA, double _kP) {
		this.kV = _kV;
		this.kA = _kA;
		this.kP = _kP;
	}
}
