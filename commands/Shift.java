package ticktank.commands;

import edu.wpi.first.wpilibj.command.Command;
import ticktank.Gear;
import ticktank.TickTank;

public class Shift extends Command {

	TickTank tank;
	Gear gear;
	
	public Shift(TickTank _tank, Gear _gear) {
		this.tank = _tank;
		this.gear = _gear;
	}

	@Override
	protected void initialize() {
		if (gear == Gear.HIGH) {
			tank.shiftUp();
		} else {
			tank.shiftDown();
		}
	}

	@Override
	protected void execute() {
		
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

	@Override
	protected void end() {
		
	}

	@Override
	protected void interrupted() {
		
	}
}
