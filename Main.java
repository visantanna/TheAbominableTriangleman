package Problem979;

import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main
{
	static Scanner scanner;
	
	public static void main ( String[] args )
	{
		scanner = new Scanner ( System.in );
		
		while ( scanner.hasNext () )
		{
			final List<Point> magicGoldTriangle = new ArrayList<Point> ();
			final List<Point> planeWorldPointList = new ArrayList<Point> ();
			
			readInput ( magicGoldTriangle, planeWorldPointList );
			final List<Point> newMagicGoldTriangle = findNewMagicGoldTriangle ( magicGoldTriangle, planeWorldPointList );
			printOutput ( newMagicGoldTriangle );
			
			if ( scanner.hasNext () )
			{
				scanner.nextLine ();
			}
		}
	}
	
	private static void readInput ( final List<Point> magicGoldTriangle, final List<Point> planeWorldPointList )
	{
		for ( int originalCoordinate = 0; originalCoordinate < 3; originalCoordinate++ )
		{
			int x = scanner.nextInt ();
			int y = scanner.nextInt ();
			
			magicGoldTriangle.add ( new Point ( x, y ) );
		}
		
		int planeWorldPointNumber = scanner.nextInt ();
		for ( int planeWorldPoint = 0; planeWorldPoint < planeWorldPointNumber; planeWorldPoint++ )
		{
			int x = scanner.nextInt ();
			int y = scanner.nextInt ();
			
			planeWorldPointList.add ( new Point ( x, y ) );
		}
	}

	private static void printOutput ( final List<Point> newMagicGoldTriangle )
	{
		newMagicGoldTriangle.forEach ( ponto -> System.out.println ( ponto.getX () + " " + ponto.getY () ) );
		System.out.println ();
	}
	
	private static List<Point> findNewMagicGoldTriangle ( final List<Point> magicGoldTriangle,final List<Point> planeWorldPointList )
	{
		final double distanceOfXY = magicGoldTriangle.get ( 0 ).distanceTo ( magicGoldTriangle.get ( 1 ) );
		final double distanceOfXZ = magicGoldTriangle.get ( 0 ).distanceTo ( magicGoldTriangle.get ( 2 ) );
		final double distanceOfYZ = magicGoldTriangle.get ( 1 ).distanceTo ( magicGoldTriangle.get ( 2 ) );
		
		final List<Point> newMagicGoldTriangle = new ArrayList<Point> ();
		for ( int pointIndex = 0; pointIndex < planeWorldPointList.size (); pointIndex++ )
		{
			final List<Point> listOfDistancesXY = new ArrayList<Point> ();
			final List<Point> listOfDistancesXZ = new ArrayList<Point> ();
			
			for ( int pointToReferenceIndex = 0; pointToReferenceIndex < planeWorldPointList.size (); pointToReferenceIndex++ )
			{
				final Point point = planeWorldPointList.get ( pointIndex );
				final Point pointToReference = planeWorldPointList.get ( pointToReferenceIndex );

				final double distanceBetweenPoints = point.distanceTo ( pointToReference );
				final int added = getAddedValue ( distanceOfXY, distanceOfXZ, distanceBetweenPoints,listOfDistancesXY, listOfDistancesXZ, pointToReference );
				
				if ( added == 0 )
				{
					continue;
				}
				
				Point confirmedPoint = getConfirmedPoint ( added, listOfDistancesXY, listOfDistancesXZ, distanceOfYZ );
				
				if ( confirmedPoint != null )
				{
					newMagicGoldTriangle.add ( point );
					newMagicGoldTriangle.add ( pointToReference );
					newMagicGoldTriangle.add ( confirmedPoint );
					
					newMagicGoldTriangle.sort ( new PointComparator() );
					
					return newMagicGoldTriangle;
				}
			}
		}
		
		throw new IllegalArgumentException();
	}

	private static int getAddedValue ( final double distanceOfXY, final double distanceOfXZ, final double distanceBetweenPoints,
			final List<Point> listOfDistancesXY, final List<Point> listOfDistancesXZ, final Point pointToReference )
	{
		if ( distanceOfXY == distanceOfXZ )
		{
			if ( distanceBetweenPoints == distanceOfXY )
			{
				listOfDistancesXY.add ( pointToReference );
				listOfDistancesXZ.add ( pointToReference );
				
				return 1;
			}
		}
		else
		{
			if ( distanceBetweenPoints == distanceOfXY )
			{
				listOfDistancesXY.add ( pointToReference );
				
				return 1;
			}
			
			if ( distanceBetweenPoints == distanceOfXZ )
			{
				listOfDistancesXZ.add ( pointToReference );
				
				return 2;
			}
		}
		
		return 0;
	}

	private static Point getConfirmedPoint ( final int added, final List<Point> listOfDistancesXY, final List<Point> listOfDistancesXZ,
			final double distanceOfYZ )
	{
		if ( added == 1 )
		{
			final Point lastAdded = listOfDistancesXY.get ( listOfDistancesXY.size () - 1 );
			for ( Point pointDistXY : listOfDistancesXZ )
			{
				if ( lastAdded.distanceTo ( pointDistXY ) == distanceOfYZ )
				{
					return pointDistXY;
				}
			}
			
			return null;
		}
		
		if ( added == 2)
		{
			final Point lastAdded = listOfDistancesXZ.get ( listOfDistancesXZ.size () - 1 );
			for ( Point pointDistXZ : listOfDistancesXY )
			{
				if ( lastAdded.distanceTo ( pointDistXZ ) == distanceOfYZ )
				{
					return pointDistXZ;
				}
			}
			
			return null;
		}
		
		throw new IllegalArgumentException();
	}
}

class Point implements Comparable<Point>
{
	private final int x;
	private final int y;

	public Point ( final int x, final int y )
	{
		this.x = x;
		this.y = y;
	}

	public int getX ()
	{
		return x;
	}

	public int getY ()
	{
		return y;
	}

	public double distanceTo ( final Point otherPoint )
	{
		final int distanceOfX = Math.abs ( x - otherPoint.getX () );
		final int distanceOfY = Math.abs ( y - otherPoint.getY () );
		
		return Math.sqrt ( ( distanceOfX * distanceOfX ) + ( distanceOfY * distanceOfY ) );
	}

	@Override
	public int compareTo ( Point otherPoint )
	{
		if ( x > otherPoint.getX () )
		{
			return 1;
		}
		
		if ( x < otherPoint.getX () )
		{
			return -1;
		}
		
		if ( y >= otherPoint.getY () )
		{
			return 1;
		}
		
		if ( y < otherPoint.getY () )
		{
			return -1;
		}
		
		throw new IllegalArgumentException ();
	}
}

class PointComparator implements Comparator<Point>
{
	@Override
	public int compare ( Point pointOne, Point pointTwo )
	{
		return pointOne.compareTo ( pointTwo );
	}
}
