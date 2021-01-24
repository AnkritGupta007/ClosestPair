package edu.cmich.cps542;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

class ClosestPairTest {

	@Test
	public void closestPairStressTest() {
		ArrayList<Point> points = new ArrayList<Point>();
		
		for(double i = -350.25; i < 350; i++) {
			for(double j = -350.25; j < 350; j++) {
				points.add(new Point(i, j));
			}
		}
		points.add(new Point(0, 0));
		
		ArrayList<Point> pointsXOrdered = (ArrayList<Point>) points.stream().
				sorted((p1, p2) -> (Double.compare(p1.x, p2.x))).
				collect(Collectors.toList());
	
		ArrayList<Point> pointsYOrdered = (ArrayList<Point>) points.stream().
				sorted((p1, p2) -> (Double.compare(p1.y, p2.y))).
				collect(Collectors.toList());
	
//		PointPair closestPair = ClosestPair.bruteClosestPair(pointsXOrdered);		
		PointPair closestPair = ClosestPair.efficientClosestPair(pointsXOrdered, pointsYOrdered);
		assertTrue(closestPair.equals(new Point(0,0), new Point(-0.25, -0.25), 1e-5));
		
	}

	@Test
	public void bruteClosestPairWThreePoints() {
		ArrayList<Point> points = new ArrayList<Point>();
		points.add(new Point(0, 0));
		points.add(new Point(1, 1));
		points.add(new Point(3, 3));
		
		PointPair closestPair = ClosestPair.bruteClosestPair(points);
		assertTrue(closestPair.equals(new Point(0,0), new Point(1, 1), 1e-5));
		
	}
	
	@Test
	public void efficientClosestPairWThreePoints() {
		ArrayList<Point> points = new ArrayList<Point>();
		points.add(new Point(0, 0));
		points.add(new Point(1, 1));
		points.add(new Point(3, 3));
		
		PointPair closestPair = ClosestPair.efficientClosestPair(points, points);
		assertTrue(closestPair.equals(new Point(0,0), new Point(1, 1), 1e-5));
		
	}
	
	
	@Test
	public void efficientClosestPairWTenPoints() {
		
		ArrayList<Point> points = new ArrayList<Point>();
		points.add(new Point(3, 2));
		points.add(new Point(2, 4));
		points.add(new Point(25, 28));
		points.add(new Point(20, 20));
		points.add(new Point(20, 21));
		points.add(new Point(1, 10));
		points.add(new Point(50, 60));
		points.add(new Point(-50, -60));
		points.add(new Point(-2, 2));
		points.add(new Point(-10, 25));
		
		ArrayList<Point> pointsXOrdered = (ArrayList<Point>) points.stream().
				sorted((p1, p2) -> (p1.x < p2.x ? -1 : 1)).
				collect(Collectors.toList());
	
		ArrayList<Point> pointsYOrdered = (ArrayList<Point>) points.stream().
				sorted((p1, p2) -> (p1.y < p2.y ? -1 : 1)).
				collect(Collectors.toList());
	
		PointPair closestPair = ClosestPair.efficientClosestPair(points, points);
		assertTrue(closestPair.equals(new Point(20,20), new Point(20, 21), 1e-5));
		
	}
	
	@Test
	public void bruteClosestPairWTenPoints() {
		
		ArrayList<Point> points = new ArrayList<Point>();
		points.add(new Point(3, 2));
		points.add(new Point(2, 4));
		points.add(new Point(25, 28));
		points.add(new Point(20, 20));
		points.add(new Point(20, 21));
		points.add(new Point(1, 10));
		points.add(new Point(50, 60));
		points.add(new Point(-50, -60));
		points.add(new Point(-2, 2));
		points.add(new Point(-10, 25));
	
		PointPair closestPair = ClosestPair.bruteClosestPair(points);
		assertTrue(closestPair.equals(new Point(20,20), new Point(20, 21), 1e-5));
		
	}
	
}
