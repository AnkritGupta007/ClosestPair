package edu.cmich.cps542;

import java.util.ArrayList;


/** 
 * sorts an ArrayList of points before utilizing the
 * Efficient Closest Pair algorithm to find the two points
 * with the shortest distance (the closest pair). 
 * 
 * @author crowl1rr gupta4a
 * @since 1-31-2021
 */
public class ClosestPair {

	public static void main(String[] args) {

		// defined variables
		ArrayList<Point> points = new ArrayList<Point>();
		ArrayList<Point> pointsXOrdered = new ArrayList<Point>();
		ArrayList<Point> pointsYOrdered = new ArrayList<Point>();
		PointPair closestPair = new PointPair(null, null);
		
		/* {(2, 4), (10, 2), (3, 5), (7, 8), (6, 8), (1,1)} */
		points.add(new Point(2, 4));
		points.add(new Point(10, 2));
		points.add(new Point(3, 5));
		points.add(new Point(7, 8));
		points.add(new Point(6, 8));
		points.add(new Point(1, 1));
		
		System.out.println("points: " + points.toString());
		System.out.println("--------------------------------------------------------------------------");
		
		// initialize both ordered lists to be equal to the points ArrayList
		pointsXOrdered = (ArrayList<Point>) points.clone();
		pointsYOrdered = (ArrayList<Point>) points.clone();
		
		/* use your sort method here */
		sort(pointsXOrdered, 0, pointsXOrdered.size()-1, false);
		sort(pointsYOrdered, 0, pointsYOrdered.size() -1, true);
		
		System.out.println("xOrdered: " + pointsXOrdered.toString());
		System.out.println("yOrdered: " + pointsYOrdered.toString());

		
		/* call efficientClosestPair here */
		closestPair = efficientClosestPair(pointsXOrdered, pointsYOrdered);
		
		System.out.println(closestPair.toString()); // print out closest pair result to console

	}

	/**
	 * Implementation of the EfficientClosestPair algorithm. The algorithm
	 * works by splitting the points into two partitions and comparing points on
	 * both sides to find the smallest distance. The algorithm then checks
	 * the points closest to the partition line to see if the closest pair
	 * exists over that line.  Whatever two points have the smallest distance
	 * between them are stored in a PointPair and returned.
	 * 
	 * @param pointsXOrdered Set of points ordered in ascending X order
	 * @param pointsYOrdered Set of points ordered in ascending Y order
	 * @return PointPair containing the pair of points with the shortest
	 * distance between them
	 */
	public static PointPair efficientClosestPair(ArrayList<Point> pointsXOrdered, ArrayList<Point> pointsYOrdered) {

		ArrayList<Point> xOrderedLeft = new ArrayList<Point>();
		ArrayList<Point> yOrderedLeft = new ArrayList<Point>();
		ArrayList<Point> xOrderedRight = new ArrayList<Point>();
		ArrayList<Point> yOrderedRight = new ArrayList<Point>();

		PointPair distanceLeft;
		PointPair distanceRight;
		PointPair minDistPointPair = null;
		double minDistance;
		double minDistanceSqr;
		ArrayList<Point> S = new ArrayList<>();
		Point m;

		if(pointsXOrdered.size() <=3) {
			// few enough points that we can use a brute force approach 
			 minDistPointPair = bruteClosestPair(pointsXOrdered);
			 minDistance = minDistPointPair.distBetweenPoints();
			 minDistanceSqr = minDistPointPair.distSqrdBetweenPoints();
		}
		else {

			// copy first n/2 points of pointsXOrdered to array xOrderedLeft
			for(int i=0; i < pointsXOrdered.size()/2; i++) {
				xOrderedLeft.add(pointsXOrdered.get(i));
			}

			// copy same n/2 points from pointsYOrdered to array yOrderedLeft
			for(int i=0; i < pointsYOrdered.size()/2; i++) {
				yOrderedLeft.add(pointsYOrdered.get(i));
			}

			// copy remaining n/2 elements from pointsXOrdered to array xOrderedRight
			for(int i=pointsXOrdered.size()/2; i < pointsXOrdered.size(); i++) {
				xOrderedRight.add(pointsXOrdered.get(i));
			}

			// copy remaining n/2 elements from pointsYOrdered to array yOrderedRight
			for(int i=pointsYOrdered.size()/2; i < pointsYOrdered.size(); i++) {
				yOrderedRight.add(pointsYOrdered.get(i));
			}

			// find smallest distance on left half
			distanceLeft = efficientClosestPair(xOrderedLeft, yOrderedLeft);
			
			// find smallest distance on right half
			distanceRight = efficientClosestPair(xOrderedRight, yOrderedRight);

			// determine which side has the closest pair
			if(distanceLeft.distBetweenPoints() < distanceRight.distBetweenPoints()) {
				minDistPointPair = distanceLeft;
			} else {
				minDistPointPair = distanceRight;
			}
			
			// store the minimum distance to be utilized later
			minDistance = minDistPointPair.distBetweenPoints();


			 m  = pointsXOrdered.get(pointsXOrdered.size()/2 -1);

			 // store any points close to the partition line in another ArrayList (S)
			for(Point point: pointsYOrdered) {
				if(Math.abs((point.x - m.x))<minDistance) {
					S.add(point);
				}
			}
			minDistanceSqr = minDistPointPair.distSqrdBetweenPoints();
			int num = S.size();

			// two temp variables to store the pointPair in S and it's SquaredDistance
			PointPair temp;
			double tempDistanceSqr;
			
			// check point pairs close to the partition line to see if any pairs
			// have a minimal distance between them.  If so, then this is the new
			// point pair.
			for(int i=0; i<num -2 ;i++  ) {
				int k = i+1 ;
				while ( (k<= (num -1)) && (Math.pow(S.get(k).y -S.get(i).y, 2)< minDistanceSqr)) {
					temp = new PointPair(S.get(k),S.get(i));
					tempDistanceSqr=temp.distSqrdBetweenPoints();
					if(tempDistanceSqr < minDistanceSqr) {
						minDistanceSqr = tempDistanceSqr;
						minDistPointPair = temp ;
					}
					k++;

				}
			}	

		}

		return minDistPointPair;
	}

	
	
	/**
	 * Brute force approach to find a pair of points with the smallest
	 * distance between them. Compares the distance between all
	 * existing points to find the closest pair.
	 * 
	 * @param points ArrayList of points to check
	 * @return PointPair with the smallest distance between points
	 */
	public static PointPair bruteClosestPair(ArrayList<Point> points) {
		PointPair temp = null; 
		PointPair result = null;

		double minDistance = Double.POSITIVE_INFINITY; // set minDistance to highest possible value
		double tempDistance = 0;

		for(int i =0; i<points.size();i++) {
			for(int j=i+1 ; j<points.size(); j++) {
				temp = new PointPair(points.get(i), points.get(j));
				tempDistance = temp.distBetweenPoints();
				if(tempDistance < minDistance){
					result =temp ;
					minDistance=tempDistance ;
				}
			}
		}
		
		return result;
	}


	/**
	 * Sorts an ArrayList of points utilizing the Merge Sort approach.
	 * 
	 * @param points The ArrayList of points to be sorted
	 * @param left The leftmost index value
	 * @param right The rightmost index value
	 * @param sortByYpoint Boolean denoting whether we want to sort by the X or Y coordinate.
	 * False signifies sort by X values and True signifies sort by Y values.
	 * @return ArrayList of sorted Points
	 */
	public static ArrayList<Point>  sort(ArrayList<Point> points, int left, int right, boolean sortByYpoint) {

		if(left<right) {
			int mid = (left+right) /2;


			if(sortByYpoint) {
				//sort the first and second halves
				sort(points,left,mid,true);
				sort(points, mid+1, right,true);


				merge(points, left, mid, right, true);
			}
			else {
				sort(points,left,mid,false);
				sort(points, mid+1, right,false);


				merge(points, left, mid, right,false);
			}
		}
		
		return points;
	}

	/**
	 * Merging method for Merge Sort-ing the ArrayLists of points.  Compares values
	 * and merges them into the list one by one in ascending order.
	 * 
	 * @param points The ArrayList of Points to be sorted
	 * @param left The smallest index value
	 * @param mid The mid point index
	 * @param right The largest index value
	 * @param mergeByYpoint Boolean for whether to merge based on X or Y coordinate value.
	 * False means to merge based on X-coordinate values and True means to merge based on
	 * Y-coordinate values.
	 */
	public static void merge(ArrayList<Point> points,int left, int mid, int right,  boolean mergeByYpoint) {
		int num1 = mid - left + 1;
		int num2 = right - mid;

		// try with Arrays.
		Point leftSub[] = new Point[num1];
		Point rightSub[] = new Point[num2];


		//copy data to temp ArrayLists
		for(int i = 0; i < num1; ++i) {
			leftSub[i] = points.get(left+i);
		}
		for(int i=0; i < num2; ++i) {
			rightSub[i]= points.get(mid+1+i);
		}

		int i = 0,j = 0 ;
		int k =left ;

		if(mergeByYpoint) {
			// merge by y-coordinate values
			while(i<num1 && j <num2) {
				if((leftSub[i].y) <= (rightSub[j].y)) {
					points.set(k, leftSub[i]) ;
					i++ ; 
				}
				else {
					points.set(k, rightSub[j]) ;
					j++;
				}
				k++ ;
			}
		}
		else {
			// merge by x-coordinate values
			while(i<num1 && j <num2) {
				if((leftSub[i].x) <= (rightSub[j].x)) {
					points.set(k, leftSub[i]) ;
					i++ ; 
				}
				else {
					points.set(k, rightSub[j]) ;
					j++;
				}
				k++ ;
			}
		}

		while (i<num1) {
			points.set(k, leftSub[i]) ;
			i++ ; 
			k++ ;
		}

		while (j<num2) {
			points.set(k, rightSub[j]) ;
			j++ ; 
			k++ ;
		}
	}

}
