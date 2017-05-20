package ticktank.commands;

import edu.wpi.first.wpilibj.command.Command;
import ticktank.TickTank;

public class CubedDriveWithJoysticks extends Command {

	private TickTank tank;
	boolean isReverse = false;

	public CubedDriveWithJoysticks(TickTank _tank, boolean _isReverse) {
		this.tank = _tank;
		requires(tank);
		this.isReverse = _isReverse;
	}

	public CubedDriveWithJoysticks(TickTank _tank) {
		this(_tank, false);
	}

	@Override
	protected void initialize() {

	}

	@Override
	protected void execute() {
		if (isReverse) {
			tank.setLeftSpeed(Math.pow(tank.rightStick.getY(), 3));
			tank.setRightSpeed(Math.pow(tank.leftStick.getY(), 3));
		} else {
			tank.setLeftSpeed(Math.pow(-tank.leftStick.getY(), 3));
			tank.setRightSpeed(Math.pow(-tank.rightStick.getY(), 3));
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
