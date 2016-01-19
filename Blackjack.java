//Code by Solomon Astley, #3938540
//CS 0445 Ramirez
//Edited Last on 19 January 2016
//This file is the main program that simulates a simplified game of the popular card game Blackjack

import java.util.*;

public class Blackjack
{
	private int numRounds, currentRound, numDecks, numCards;
	private RandIndexQueue<Card> shoe, discard, playerHand, dealerHand;

	public Blackjack(int numRounds, int numDecks)
	{
		this.numRounds = numRounds;
		this.numDecks = numDecks;
		numCards = numDecks * 52;
		shoe = new RandIndexQueue<Card>(numCards);
		discard = new RandIndexQueue<Card>(numCards);

		for (int i = 0; i < numDecks; i++)
		{
			for (Card.Suits s: Card.Suits.values())
			{
				for (Card.Ranks r: Card.Ranks.values())
				{
					shoe.addItem(new Card(s, r));
				}
			}
		}
		shoe.shuffle();

		System.out.println("Starting Blackjack with " + numRounds + " rounds and " + numDecks + " decks in the shoe\n");

		int dealerWins = 0;
		int playerWins = 0;
		int pushes = 0;
		currentRound = 0;
		for (int i = 0; i < numRounds; i++)
		{
			int theWinner = playRound();
			if (theWinner == 0)
				dealerWins++;
			else if (theWinner == 1)
				playerWins++;
			else
				pushes++;
			currentRound++;
			discard();
			System.out.println();
		}

		System.out.println("After " + numRounds + " rounds, here are the results: ");
		System.out.println("\tDealer wins: " + dealerWins);
		System.out.println("\tPlayer wins: " + playerWins);
		System.out.println("\tPushes: " + pushes);
	}

	public static void main(String [] args)
	{
		int numRounds = Integer.parseInt(args[0]);
		int numDecks = Integer.parseInt(args[1]);
		new Blackjack(numRounds, numDecks);
	}

	public int playRound()
	{
		System.out.println("Round " + currentRound + " beginning");

		playerHand = new RandIndexQueue<Card>(21);
		dealerHand = new RandIndexQueue<Card>(21);
		int playerTotal = 0;
		int dealerTotal = 0;

		playerHand.addItem(shoe.removeItem());
		dealerHand.addItem(shoe.removeItem());
		playerHand.addItem(shoe.removeItem());
		dealerHand.addItem(shoe.removeItem());

		playerTotal += playerHand.get(0).value() + playerHand.get(1).value();
		dealerTotal += dealerHand.get(0).value() + dealerHand.get(1).value();

		System.out.println("Player: Contents: " + playerHand.get(0).toString() + " " + playerHand.get(1).toString() + " : " + playerTotal);
		System.out.println("Dealer: Contents: " + dealerHand.get(0).toString() + " " + dealerHand.get(1).toString() + " : " + dealerTotal);

		if (playerTotal == 21 && dealerTotal == 21)
		{
			System.out.println("Result: Push!");
			return 2;
		}
		else if (playerTotal == 21)
		{
			System.out.println("Result: Player Blackjack wins!");
			return 1;
		}
		else if (dealerTotal == 21)
		{
			System.out.println("Result: Dealer Blackjack wins!");
			return 0;
		}

		while (playerTotal < 17)
		{
			playerHand.addItem(shoe.removeItem());
			playerTotal += playerHand.get(playerHand.size() - 1).value();
			System.out.println("Player hits: " + playerHand.get(playerHand.size() - 1).toString());
		}

		if (playerTotal > 21)
		{
			System.out.print("Player BUSTS: Contents: ");
			for (int i = 0; i < playerHand.size(); i++)
			{
				System.out.print(playerHand.get(i).toString() + " ");
			}
			System.out.println();
			System.out.println("Result: Dealer wins!");
			return 0;
		}
		else
		{
			System.out.print("Player STANDS: Contents: ");
			for (int i = 0; i < playerHand.size(); i++)
			{
				System.out.print(playerHand.get(i).toString() + " ");
			}
			System.out.println();
		}

		while (dealerTotal < 17)
		{
			dealerHand.addItem(shoe.removeItem());
			dealerTotal += dealerHand.get(dealerHand.size() - 1).value();
			System.out.println("Dealer hits: " + dealerHand.get(dealerHand.size() - 1).toString());
		}

		if (dealerTotal > 21)
		{
			System.out.print("Dealer BUSTS: Contents: ");
			for (int i = 0; i < dealerHand.size(); i++)
			{
				System.out.print(dealerHand.get(i).toString() + " ");
			}
			System.out.println();
			System.out.println("Result: Player wins!");
			return 1;
		}
		else
		{
			System.out.print("Dealer STANDS: Contents: ");
			for (int i = 0; i < dealerHand.size(); i++)
			{
				System.out.print(dealerHand.get(i).toString() + " ");
			}
			System.out.println();
		}

		if (playerTotal > dealerTotal)
		{
			System.out.println("Result: Player wins!");
			return 1;
		}
		else if (dealerTotal > playerTotal)
		{
			System.out.println("Result: Dealer wins!");
			return 0;
		}
		else
		{
			System.out.println("Result: Push!");
			return 2;
		}
	}

	public void discard()
	{
		for (int i = 0; i < playerHand.size(); i++)
			discard.addItem(playerHand.removeItem());
		for (int i = 0; i < dealerHand.size(); i++)
			discard.addItem(dealerHand.removeItem());
	}
}