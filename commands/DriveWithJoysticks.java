package ticktank.commands;

import edu.wpi.first.wpilibj.command.Command;
import ticktank.TickTank;

public class DriveWithJoysticks extends Command {

	private TickTank tank;

	public DriveWithJoysticks(TickTank _tank) {
		this.tank = _tank;
		requires(_tank);
	}

	protected void initialize() {

	}

	protected void execute() {
		tank.setLeftSpeed(-tank.leftStick.getY());
		tank.setRightSpeed(-tank.rightStick.getY());
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
		tank.stop();
	}

	protected void interrupted() {
		tank.stop();
	}
}
