package ticktank.commands;

import edu.wpi.first.wpilibj.command.Command;
import pid.PID;
import ticktock.Ticker;
import ticktank.Direction;
import ticktank.TickTank;

public class Turn extends Command {

	private TickTank tank;

	private double setPoint; // The heading we are going to
	private double degrees;
	private double error; // The current distance we still have to go

	private PID controller;
	private Ticker ticker;

	public Turn(TickTank _tank, double _degrees, Direction _dir) {
		if (_dir == Direction.LEFT || _dir == Direction.CCW) {
			degrees = _degrees * -1;
		} else {
			degrees = _degrees;
		}
		this.tank = _tank;
		controller = new PID(tank, tank, tank.turnParams);
		ticker = new Ticker(controller, tank.turnParams.interval);
		requires(tank);
	}

	@Override
	protected void initialize() {
		if (tank.navx == null) {
			System.out.println("No gyro in use");
			end();
		}
		this.setPoint = tank.navx.getAngle() + degrees;
		controller.setSetpoint(setPoint);
		
		System.out.println("Turning");
		ticker.start();
	}

	@Override
	protected void execute() {
		error = setPoint - tank.navx.getAngle();
		System.out.println(error);
	}

	@Override
	protected boolean isFinished() {
		return (Math.abs(error) < 0.25) && (tank.navx.getRate() < 0.25);
	}

	@Override
	protected void end() {
		tank.stop();
		ticker.stop();
		System.out.println("Not turning");
	}

	@Override
	protected void interrupted() {
		end();
	}

}
