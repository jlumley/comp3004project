package core;

import java.util.ArrayList;

public class Strategy3 implements AIStrategy{
	
	public void playTurn() {
		System.out.println("Player 3 Turn");
		System.out.println("Player 3 Hand: " + TileRummyMain.player3.getHand());
		//Check for 30 point limit and play it
		ArrayList<ArrayList<Tile>> p3Melds = TileRummyMain.player3.playAllTiles();
		System.out.println("Possible Melds in Hand: " + p3Melds);
		//Check if still first turn (not yet played 30P)
		if(TileRummyMain.player3.firstTurn == false) { //first turn has not passed yet
			if(TileRummyMain.player3.checkPlays(p3Melds)) { //checks for >=30 size
				for(int i = 0; i < p3Melds.size(); i++) { //Play all the tiles it can
					TileRummyMain.addMend(p3Melds.get(i)); //add all melds to board
				}
				TileRummyMain.player3.firstTurn = true;
			}
			else { //return cards to hand and draw a card
				returnCardsAndDrawCard(p3Melds);
			}
		}
		else { //Play all tiles including reuse of the board
			ArrayList<ArrayList<Tile>> p3NewBoard = TileRummyMain.player3.playAllTiles(TileRummyMain.field);
			//Check if can win this turn, if so Win
			if(TileRummyMain.player3.countTiles() == 0) {
				TileRummyMain.field = p3NewBoard; //replace old board with this new one
				for(int i = 0; i < p3Melds.size(); i++) { 
					TileRummyMain.addMend(p3Melds.get(i)); 
				}
				return;
			}
			//(Observer) Ask game for the Hand sizes of all other players
			if(existsAComparativelySmallHand()) { //If This has +3 than any of them: Play all Tiles possible
				if(TileRummyMain.field.size() != p3NewBoard.size()) { //check num of melds
					TileRummyMain.field = p3NewBoard; //replace old board with this new one
					for(int i = 0; i < p3Melds.size(); i++) { 
						TileRummyMain.addMend(p3Melds.get(i)); 
					}
					return;
				}
				else { //if equal num melds
					for(int i = 0; i < TileRummyMain.field.size(); i++) { //check size of each meld
						if(TileRummyMain.field.get(i).size() != p3NewBoard.get(i).size()) {
							TileRummyMain.field = p3NewBoard; //replace old board with this new one
							for(int k = 0; k < p3Melds.size(); k++) { 
								TileRummyMain.addMend(p3Melds.get(k)); 
							}
							return;
						}
					}
				}
				//otherwise
				System.out.println("p3 could play but has no tile to play");
				returnCardsAndDrawCard(p3Melds);
			}
			else {//Else: keep all in-hand melds, and try adding to existing melds on board
				if(TileRummyMain.field.size() != p3NewBoard.size()) { //check num of melds
					TileRummyMain.field = p3NewBoard; //replace old board with this new one
					return;
				}
				else { //if equal num melds
					for(int i = 0; i < TileRummyMain.field.size(); i++) { //check size of each meld
						if(TileRummyMain.field.get(i).size() != p3NewBoard.get(i).size()) {
							TileRummyMain.field = p3NewBoard; //replace old board with this new one
							return;
						}
					}
				}
				returnCardsAndDrawCard(p3Melds);
			}
		}
	}
	
	//return cards to hand and draw a card
	public void returnCardsAndDrawCard(ArrayList<ArrayList<Tile>> p3Melds) {
		//return unPlayed Melds back into Hand
		for(int i = 0; i < p3Melds.size(); i++) { //double loops in and adds all Tiles back
			for(int j = 0; j < p3Melds.get(i).size(); j++) {
				TileRummyMain.player3.addTile(p3Melds.get(i).get(j));
			}
		}
		//draw a Tile
		if(!TileRummyMain.initDeck.isEmpty()){ //if not empty NORMAL CASE
			System.out.println("Player 3 draws: " + TileRummyMain.initDeck.get(0));
			TileRummyMain.player3.drawTile(TileRummyMain.initDeck);
		}
		else { //just when doing partial tests
			TileRummyMain.initDeck = TileRummyMain.buildDeck(TileRummyMain.suites, TileRummyMain.values);
			System.out.println("Player 3 draws: " + TileRummyMain.initDeck.get(0));
			TileRummyMain.player3.drawTile(TileRummyMain.initDeck);
		}
	}
	
	public boolean existsAComparativelySmallHand() { // 3 or less Tiles then in this Player's Hand
		int[] handSizes = TileRummyMain.getHandSizeOfOtherPlayers(TileRummyMain.player3); //get HandSizes
		for(int i = 0; i < handSizes.length; i++) { 
			if(TileRummyMain.player3.getHand().size() >= (handSizes[i] + 3)) {
				return true;
			}
		}
		return false;
	}
	
}
