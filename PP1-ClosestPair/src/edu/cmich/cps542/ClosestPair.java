package edu.cmich.cps542;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ClosestPair {

	public static void main(String[] args) {
	
		/* {(2, 4), (10, 2), (3, 5), (7, 8), (6, 8), (1,1)} */
	
		/* use your sort method here */
		/* document says to implement a divide-and-conquer sorting algorithm that runs in theta(nlogn) time
		
		merge sort is divide-and-conquer in all cases, but
		QuickSort is theta(n log n) in average-case and best-case
		
		
		
		sort in ascending order by the x and y coordinates?
		
		*/
		
		
		
		
		/* call efficientClosestPair here */
		
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
	
	
	public static ArrayList<Point> sort(ArrayList<Point> points) {
		
		
		return null;
	}
		
}
