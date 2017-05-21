package ticktank.commands;

import edu.wpi.first.wpilibj.command.Command;
import ticktank.TickTank;

/**
 * Shift into high gear.
 *
 * @author samcf
 */
public class ShiftUp extends Command {

	TickTank tank;

	public ShiftUp(TickTank _tank) {
		this.tank = _tank;
	}

	@Override
	protected void initialize() {
		tank.shiftUp();
	}

	@Override
	protected boolean isFinished() {
		return true;
	}
}
