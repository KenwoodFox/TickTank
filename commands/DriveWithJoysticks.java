package ticktank.commands;

import edu.wpi.first.wpilibj.command.Command;
import ticktank.TickTank;

public class DriveWithJoysticks extends Command {

	private TickTank tank;

	public DriveWithJoysticks(TickTank _tank) {
		this.tank = _tank;
		requires(_tank);
	}

	@Override
	protected void initialize() {

	}

	@Override
	protected void execute() {
		tank.setLeftSpeed(-tank.leftStick.getY());
		tank.setRightSpeed(-tank.rightStick.getY());
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
