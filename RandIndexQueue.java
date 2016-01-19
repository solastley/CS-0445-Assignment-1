//Code by Solomon Astley, #3938540
//CS 0445 Ramirez
//Edited Last on 19 January 2016
//This is a class file to represent a queue from which items will be taken at random
//The queue may also be shuffled and accessed by indices

import java.util.*;
import java.lang.*;

public class RandIndexQueue<T> implements MyQ<T>, Indexable<T>, Shufflable
{
	private T[] theData;
	private int numItems, count;

	public RandIndexQueue(int numI)
	{
		numItems = numI;
		@SuppressWarnings("unchecked")
		T[] d = (T[]) new Object [numItems];

		theData = d;

		count = 0;
	}

	public boolean addItem(T item)
	{
		if (!full())
		{
			theData[count] = item;
			count++;
			return true;
		}
		else
			return false;
	}

	public T removeItem()
	{
		if (count == 0)
			return null;
		else
		{
			T temp = theData[0];
			count--;
			for (int i = 0; i < count; i++)
			{
				theData[i] = theData[i + 1];
			}
			theData[count] = null;
			return temp;
		}
	}

	public boolean full()
	{
		if (count == numItems)
			return true;
		else
			return false;
	}

	public boolean empty()
	{
		if (count == 0)
			return true;
		else
			return false;
	}

	public int size()
	{
		return count;
	}

	public void clear()
	{
		for (int i = 0; i < count - 1; i++)
		{
			theData[i] = null;
		}
		count = 0;
	}

	public T get(int i)
	{
		if (i >= numItems)
		{
			System.out.println("Out of bounds");
			return null;
		}
		else
			return theData[i];
	}

	public void set(int i, T item)
	{
		if (i >= numItems)
		{
			System.out.println("Out of bounds");
		}
		else
		{
			if (i >= count)
			{
				theData[i] = item;
				count++;
			}
			else
				theData[i] = item;
		}
	}

	public void shuffle()
	{
		Random r = new Random();
		int [] randArray = new int [count];
		int tally = 0;

		for (int i = 0; i < count; i++)
		{
			boolean good = false;
			do
			{
				boolean found = false;
				int randNum = r.nextInt(count);
				for (int j = 0; j < tally; j++)
				{
					if (randArray[j] == randNum)
					{
						found = true;
						break;
					}
				}

				if (!found)
				{
					good = true;
					randArray[tally] = randNum;
					tally++;
				}
			} while (!good);
		}

		@SuppressWarnings("unchecked")
		T [] tempArray = (T []) new Object [numItems];
		for (int k = 0; k < count; k++)
		{
			int index = randArray[k];
			tempArray[k] = theData[index];
		}
		theData = tempArray;
	}

	public String toString()
	{
		StringBuilder b = new StringBuilder("Contents: ");
		for (int i = 0; i < count; i++)
		{
			b.append(theData[i] + " ");
		}
		return b.toString();
	}

	public boolean hasInstanceOf(int value)
	{
		for (int i = 0; i < count; i++)
		{
			if (theData[i].value() == value)
				return true;
		}
		return false;
	}
}