package edu.cmich.cps542;

import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//import java.util.stream.Collectors;

public class ClosestPair {

	public static void main(String[] args) {

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
		

		pointsXOrdered = (ArrayList<Point>) points.clone();
		pointsYOrdered = (ArrayList<Point>) points.clone();
		
		/* use your sort method here */
		sort(pointsXOrdered, 0, pointsXOrdered.size()-1, false);
		sort(pointsYOrdered, 0, pointsYOrdered.size() -1, true);
		
//		System.out.println("XOrdered: " + pointsXOrdered.toString());
//		System.out.println("YOrdered: " + pointsYOrdered.toString());
		
		/* call efficientClosestPair here */
		closestPair = efficientClosestPair(pointsXOrdered, pointsYOrdered);
		
		System.out.println(closestPair.toString());

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
		double minDistanceSqr;
		ArrayList<Point> S = new ArrayList<>();
		Point m;

		if(pointsXOrdered.size() <=3) {
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

			distanceLeft = efficientClosestPair(xOrderedLeft, yOrderedLeft);
			distanceRight = efficientClosestPair(xOrderedRight, yOrderedRight);

			//			minDistance = Math.min(distanceLeft.distBetweenPoints(), distanceRight.distBetweenPoints());

			if(distanceLeft.distBetweenPoints() < distanceRight.distBetweenPoints()) {
				minDistPointPair = distanceLeft;
			} else {
				minDistPointPair = distanceRight;
			}
			minDistance = minDistPointPair.distBetweenPoints();


			 m  = pointsXOrdered.get(pointsXOrdered.size()/2 -1);

			
			// values are not getting stored in the S after each recursive call
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
			for(int i=0; i<num -2 ;i++  ) {
				int k = i+1 ;
				while ( (k<= (num -1)) && (Math.pow(S.get(k).y -S.get(i).y, 2)< minDistanceSqr)) {
					temp = new PointPair(S.get(k),S.get(i));
					tempDistanceSqr=temp.distSqrdBetweenPoints();
					if(tempDistanceSqr < minDistanceSqr) {
						minDistanceSqr = tempDistanceSqr;
						minDistPointPair = temp ;
						k++;
					}

				}
			}	

		}

		return minDistPointPair;

	}

	public static PointPair bruteClosestPair(ArrayList<Point> points) {
		PointPair temp = null; 
		PointPair result = null;

		double minDistance = Double.POSITIVE_INFINITY;
		double tempDistance = 0;

		for(int i =0; i<points.size();i++) {
			for(int j=i+1 ; j<points.size(); j++) {
				temp =new PointPair(points.get(i), points.get(j));
				tempDistance = temp.distBetweenPoints();
				if(tempDistance < minDistance){
					result =temp ;
					minDistance=tempDistance ;
				}
			}
		}
		return result;

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

		// . . .
		int i = 0,j = 0 ;

		int k =left ;


		//merge by x-points or y points depending upon input
		if(mergeByYpoint) {
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
