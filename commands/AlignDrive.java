package ticktank.commands;

import org.usfirst.frc.team236.robot.Robot;
import org.usfirst.frc.team236.robot.lib.Thrustmaster;

import edu.wpi.first.wpilibj.command.Command;
import ticktank.TickTank;

/**
 * Runs the drive in arcade mode with the speed scaled down significantly to
 * aid precise alignment with field elements. Change speedfactor and turnFactor
 * to adjust the speed of forward and rotational movement, respectively. 
 *
 * @author samcf
 */
public class AlignDrive extends Command {
	public static final double DEFAUlT_SPEED = 0.5;
	public static final double DEFAULT_TURN = 0.5;

	double speedFactor, turnFactor;
	TickTank tank;

	public AlignDrive(TickTank _tank, double _speed, double _turn) {
		this.tank = _tank;
		requires(tank);

		speedFactor = _speed;
		turnFactor = _turn;
	}

	public AlignDrive(TickTank _tank) {
		this(_tank, DEFAUlT_SPEED, DEFAULT_TURN);
	}

	@Override
	protected void initialize() {
		Robot.tank.stop();
	}

	@Override
	protected void execute() {
		double y = Robot.oi.left.getRawAxis(Thrustmaster.Axes.Y);
		double z = Robot.oi.left.getRawAxis(Thrustmaster.Axes.Z);

		double leftSpeed = speedFactor * y;
		double rightSpeed = speedFactor * y;

		leftSpeed += turnFactor * z;
		rightSpeed -= turnFactor * z;

		Robot.tank.left.setSpeed(leftSpeed);
		Robot.tank.right.setSpeed(rightSpeed);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.tank.stop();
	}

	@Override
	protected void interrupted() {
		end();
	}
}
