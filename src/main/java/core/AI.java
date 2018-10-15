package core;

public class AI extends Player{
	private AIStrategy strategy;
	
	public AI() {
		super();
	}
	
	public void playTurn() {
		strategy.playTurn();
	}
	public void setStategy(AIStrategy strat) {
		strategy = strat;
	}
}
