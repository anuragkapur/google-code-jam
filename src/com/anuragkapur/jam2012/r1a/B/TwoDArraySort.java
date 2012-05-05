package com.anuragkapur.jam2012.r1a.B;

import java.util.Arrays;
import java.util.Comparator;

public class TwoDArraySort {

	public void Sort()
	{   
	    Integer[][] theArray = 	{
	    							{
	    								10,
	    								10
	    							},
	    							{
	    								1,
	    								9
	    							}
	    						};

	    dump(theArray);
	    Arrays.sort(theArray, new Comparator<Integer[]>()
	    {
	        @Override
	        public int compare(Integer[] int1, Integer[] int2)
	        {
	            Integer numOfKeys1 = int1[1];
	            Integer numOfKeys2 = int2[1];
	            return numOfKeys1.compareTo(numOfKeys2);
	        }
	    });

	    System.out.println("====");
	    dump(theArray);     
	}

	public void dump(Integer[][] array)
	{
	    for(int p = 0, q = 10; p < q; p++)
	    {
	        System.out.println(array[p][0] + " " + array[p][1]);
	    }
	}
	
	public static void main(String args[]) {
		TwoDArraySort sorter = new TwoDArraySort();
		sorter.Sort();
	}

}
