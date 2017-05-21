package ticktank.commands;

import edu.wpi.first.wpilibj.command.Command;
import ticktank.TickTank;

/**
 * Drives tank off a basic tank drive. Sets each drive side to the raw Y value
 * read from its respective joystick.
 *
 * @author samcf
 */
public class DriveWithJoysticks extends Command {

	private TickTank tank;
	boolean isReverse = false;

	public DriveWithJoysticks(TickTank _tank, boolean _isReverse) {
		this.tank = _tank;
		requires(tank);
		this.isReverse = _isReverse;
	}

	public DriveWithJoysticks(TickTank _tank) {
		this(_tank, false);
	}

	@Override
	protected void initialize() {

	}

	@Override
	protected void execute() {
		if (isReverse) {
			tank.setLeftSpeed(tank.rightStick.getY());
			tank.setRightSpeed(tank.leftStick.getY());
		} else {
			tank.setLeftSpeed(-tank.leftStick.getY());
			tank.setRightSpeed(-tank.rightStick.getY());
		}
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		tank.stop();
	}

	@Override
	protected void interrupted() {
		tank.stop();
	}
}
