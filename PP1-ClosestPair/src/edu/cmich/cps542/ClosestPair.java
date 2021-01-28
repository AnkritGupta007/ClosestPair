package edu.cmich.cps542;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ClosestPair {

	public static void main(String[] args) {

		/* {(2, 4), (10, 2), (3, 5), (7, 8), (6, 8), (1,1)} */

		/* use your sort method here */

		/* call efficientClosestPair here */
		ArrayList<Point> points = new ArrayList<Point>();

		for(double i = 3.25; i >0; i--) {
			for(double j = 3.25; j > 0; j--) {
				points.add(new Point(i, j));
			}
		}
		points.add(new Point(0, 0));

		System.out.println(points.toString());
		System.out.println("-----------------------------------------");


		System.out.println(points.size());
		points = sort(points, 0 ,points.size()-1,false);
		
		

		System.out.println("-----------------------------------------");
		System.out.println(points.toString());
		ArrayList<Point> ypoints = new ArrayList<Point>();
		ypoints = (ArrayList<Point>) points.clone() ;


		ypoints = sort(points, 0 ,points.size()-1,true);
		System.out.println(points.toString());
		
		System.out.println(efficientClosestPair(points, ypoints));

	}

	public static PointPair efficientClosestPair(ArrayList<Point> pointsXOrdered, ArrayList<Point> pointsYOrdered) {

		ArrayList<Point> xOrderedLeft = new ArrayList<Point>();
		ArrayList<Point> yOrderedLeft = new ArrayList<Point>();
		ArrayList<Point> xOrderedRight = new ArrayList<Point>();
		ArrayList<Point> yOrderedRight = new ArrayList<Point>();

		PointPair distanceLeft;
		PointPair distanceRight;
		PointPair minDistPointPair = null;
		double minDistance;


		if(pointsXOrdered.size() <=3) {
			// return minimal distance found by brute-force algorithm
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

			distanceLeft = efficientClosestPair(xOrderedLeft, yOrderedLeft);
			distanceRight = efficientClosestPair(xOrderedRight, yOrderedRight);

			//			minDistance = Math.min(distanceLeft.distBetweenPoints(), distanceRight.distBetweenPoints());

			if(distanceLeft.distBetweenPoints() < distanceRight.distBetweenPoints()) {
				minDistPointPair = distanceLeft;
			} else {
				minDistPointPair = distanceRight;
			}

		}

		return minDistPointPair;

	}

	public static PointPair bruteClosestPair(ArrayList<Point> points) {


		return null;

	}


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

	public static void merge(ArrayList<Point> points,int left, int mid, int right,  boolean sortByYpoint) {
		int num1 = mid - left + 1;
		int num2 = right - mid;

		// try with Arrays.
		Point leftSub[] = new Point[num1];
		Point rightSub[] = new Point[num2];


		//copy data to temp ArrayLists

		//the problem in the loop
		for(int i = 0; i < num1; ++i) {
			leftSub[i] = points.get(left+i);
		}
		for(int i=0; i < num2; ++i) {
			rightSub[i]= points.get(mid+1+i);
		}

		// . . .
		int i = 0,j = 0 ;

		int k =left ;


		//merge by x-points or y points depending upon input
		if(sortByYpoint) {
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




