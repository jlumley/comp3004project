package core;

public class AI extends Player{
	private AIStrategy strategy;
	
	public AI(AIStrategy strat) {
		super();
		setStrategy(strat);
	}
	
	public void playTurn() {
		strategy.playTurn();
	}
	public void setStrategy(AIStrategy strat) {
		strategy = strat;
	}
}
