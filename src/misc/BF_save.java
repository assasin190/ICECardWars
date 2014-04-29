package misc;

import java.util.List;

import main.Battlefield;
import main.Card;
import main.CardHolder;
import main.Inw;
import main.Main;

public class BF_save {
	public Inw player;
	public Inw opponent;
	public List<Integer> playerDeck ;
	public List<Integer> opponentDeck;
	public Card my1;
	public Card my2;
	public Card my3;
	public Card my4;
	public Card th1;
	public Card th2;
	public Card th3;
	public Card th4;
	public CardHolder pHand;
	public CardHolder oHand;
	public CardHolder pDumpster;
	public CardHolder oDumpster;
	public BF_save(Battlefield bf){
		this.player = Battlefield.player;
		this.opponent = Battlefield.opponent;
		playerDeck = bf.playerDeck;
		opponentDeck = bf.opponentDeck;
		pDumpster = bf.p_dumpster;
		oDumpster = bf.o_dumpster;
		my1 = bf.Mylane1.getCard();
		my2 = bf.Mylane2.getCard();
		my3 = bf.Mylane3.getCard();
		my4 = bf.Mylane4.getCard();
		th1 = bf.Theirlane1.getCard();
		th2 = bf.Theirlane2.getCard();
		th3 = bf.Theirlane3.getCard();
		th4 = bf.Theirlane4.getCard();
	}
}
