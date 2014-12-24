package pl.bmaraszek;

import java.awt.Color;
import java.awt.Graphics;

import pl.bmaraszek.PhysicsBodies.Body;
import pl.bmaraszek.PhysicsBodies.Edge;
import pl.bmaraszek.PhysicsBodies.Vertex;
import pl.bmaraszek.forceGenerators.ForceGenerator2D;
import pl.bmaraszek.forceGenerators.ForceGeneratorFactory;
import pl.bmaraszek.integrators.Integrator;
import pl.bmaraszek.integrators.IntegratorFactory;
import pl.bmaraszek.math.BoundingBox;
import pl.bmaraszek.math.MinMax;
import pl.bmaraszek.math.Vector2D;

/**
 * @author Bartek Maraszek
 */
public class Physics {

	/**
	 * @author Bartek Maraszek
	 *         <p>
	 *         Inner helper class for handling collisions.
	 *         </p>
	 */
	private static class CollisionInfo {
		static double depth;
		static Vector2D normal = new Vector2D(0, 0);
		static Edge e;
		static Vertex v;
	}

	private final int MAX_BODIES = 100;
	private final int MAX_EDGES = 512;
	private final int MAX_VERTICES = 512;
	long start, stop;

	private int screenWidth;
	private int screenHeight;
	private int iterations;

	private int bodyCount = 0;
	private int edgeCount = 0;
	private int vertexCount = 0;

	private Body[] bodies = new Body[MAX_BODIES];

	private Edge[] edges = new Edge[MAX_EDGES];
	private Vertex[] vertices = new Vertex[MAX_VERTICES];

	private Vector2D axis = new Vector2D(0.0, 0.0);

	private ForceGenerator2D gravity = ForceGeneratorFactory.createGravity(new Vector2D(0, 500));

	private Integrator integrator = IntegratorFactory.create(IntegratorFactory.IntegratorType.VERLET);

	public Physics(int screenWidth, int screenHeight, int numberOfIterations) {
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		iterations = numberOfIterations;
	}

	public void addBody(Body body) {
		if (bodyCount > MAX_BODIES) {
			return;
		}
		bodies[bodyCount++] = body;
	}

	public void addEdge(Edge edge) {
		edges[edgeCount++] = edge;
	}

	public void addVertex(Vertex vertex) {
		vertices[vertexCount++] = vertex;
	}

	/**
	 * Performs a quick test for overlapping.
	 * 
	 * @param body1
	 * @param body2
	 * @return True if the bodies overlap.
	 */
	private boolean bodiesOverlap(Body body1, Body body2) {
		BoundingBox b1 = body1.getBox();
		BoundingBox b2 = body2.getBox();
		return (b1.getMinX() <= b2.getMaxX()) && (b1.getMinY() <= b2.getMaxY()) && (b1.getMaxX() >= b2.getMinX())
				&& (b2.getMaxY() >= b1.getMinY());
	}

	private boolean detectCollision(Body b1, Body b2) {
		/* Initialize the length of the collision vector to a large value */
		double minDistance = 10000.0;
		/* A nice way of iterating through all the edges of both bodies at once */
		int totalEdges = b1.getEdgeCount() + b2.getEdgeCount();
		for (int i = 0; i < totalEdges; ++i) {
			Edge e;
			if (i < b1.getEdgeCount()) {
				e = b1.getEdges()[i];
			} else {
				e = b2.getEdges()[i - b1.getEdgeCount()];
			}

			/* Skips edges inside the bodies, as they don't matter. */
			if (e.getBoundary() == false) {
				continue;
			}
			/* Calculate the perpendicular to this edge and normalize it */
			axis.set(e.getV1().getPosition().getY() - e.getV2().getPosition().getY(), e.getV2().getPosition().getX()
					- e.getV1().getPosition().getX());
			axis.normalize();
			/* Project both bodies onto the perpendicular */
			MinMax dataA = b1.projectToAxis(axis);
			MinMax dataB = b2.projectToAxis(axis);
			/* Calculate the distance between the two intervals */
			double distance = IntervalDistance(dataA.getMin(), dataA.getMax(), dataB.getMin(), dataB.getMax());
			/* If the intervals don't overlap, return, for there is no collision */
			if (distance > 0.0f) {
				return false;
			} else if (Math.abs(distance) < minDistance) {
				minDistance = Math.abs(distance);
				/* Save collision information for later */
				CollisionInfo.normal.set(axis);
				/* Store the edge, as it is the collision edge */
				CollisionInfo.e = e;
			}
		}
		CollisionInfo.depth = minDistance;
		/*
		 * Ensure that the body containing the collision edge lies in b2 and the
		 * one containing the collision vertex in b1
		 */
		if (CollisionInfo.e.getBody() != b2) {
			Body temp = b2;
			b2 = b1;
			b1 = temp;
		}
		/*
		 * int Sign = SGN( CollisionInfo.Normal.multiplyVal(
		 * B1.Center.minus(B2.Center) ) ); //This is needed to make sure that
		 * the collision normal is pointing at B1
		 */
		double xx = b1.getCenterOfMass().getX() - b2.getCenterOfMass().getX();
		double yy = b1.getCenterOfMass().getY() - b2.getCenterOfMass().getY();
		double mult = CollisionInfo.normal.getX() * xx + CollisionInfo.normal.getY() * yy;
		/*
		 * Remember that the line equation is N*( R - R0 ). We choose B2->Center
		 * as R0; the normal N is given by the collision normal
		 */
		if (mult < 0) {
			/* Revert the collision normal if it points away from B1 */
			CollisionInfo.normal.invert();
		}
		/* Initialize the smallest distance to a large value */
		double smallestD = 10000.0;

		for (int i = 0; i < b1.getVertexCount(); ++i) {
			/*
			 * Measure the distance of the vertex from the line using the line
			 * equation: double distance =
			 * CollisionInfo.normal.multiplyVal(B1.Vertices
			 * [I].Position.minus(B2.Center))
			 */
			xx = b1.getVertices()[i].getPosition().getX() - b2.getCenterOfMass().getX();
			yy = b1.getVertices()[i].getPosition().getY() - b2.getCenterOfMass().getY();
			double distance = CollisionInfo.normal.getX() * xx + CollisionInfo.normal.getY() * yy;

			if (distance < smallestD) {
				/*
				 * If the measured distance is smaller than the smallest
				 * distance reported so far, set the smallest distance and the
				 * collision vertex
				 */
				smallestD = distance;
				CollisionInfo.v = b1.getVertices()[i];
			}
		}
		/* No separating axis - report a collision! */
		return true;
	}

	/**
	 * Finds the Vertex placed nearest to the place clicked.
	 * 
	 * @param x
	 *            X coordinate of the mouse click.
	 * @param y
	 *            Y coordinate of the mouse click.
	 * @return The nearest Vertex.
	 */
	public Vertex findVertex(int x, int y) {
		Vertex nearestVertex = null;
		double minDistance = 1000.0;

		Vector2D coords = new Vector2D(x, y);

		for (int i = 0; i < vertexCount; i++) {
			double distance = Math.hypot(vertices[i].getPosition().getX() - coords.getX(), vertices[i].getPosition()
					.getY() - coords.getY());

			if (distance < minDistance) {
				nearestVertex = vertices[i];
				minDistance = distance;
			}
		}
		return nearestVertex;
	}

	public int getMaxVertices() {
		return MAX_VERTICES;
	}

	private void integrate(Vertex v, double dt) {
		integrator.integrate(v, dt);
	}

	private double IntervalDistance(double minA, double maxA, double minB, double maxB) {
		if (minA < minB) {
			return minB - maxA;
		} else {
			return minA - maxB;
		}
	}

	private void iterateCollisions() {
		/* Repeat a couple times for more exact results */
		for (int i = 0; i < iterations; ++i) {
			/* Bounce off the walls - a small hack */
			for (int t = 0; t < vertexCount; ++t) {
				Vector2D pos = vertices[t].getPosition();
				pos.set(Math.max(Math.min(pos.getX(), screenWidth), 0.0),
						Math.max(Math.min(pos.getY(), screenHeight), 0.0));
			}
			/* Edge correction step */
			updateEdges();

			/* Iterate through all the bodies */
			for (int b1 = 0; b1 < bodyCount; ++b1) {
				this.bodies[b1].calculateCenterOfMass();
				for (int b2 = b1 + 1; b2 < bodyCount; ++b2) {
					if (!bodiesOverlap(bodies[b1], bodies[b2])) {
						continue;
					}

					if (detectCollision(bodies[b1], bodies[b2])) {
						processCollision();
					}

				}
			}
		}
	}

	/**
	 * Abra Kadabra:
	 */
	private void processCollision() {
		Vertex e1 = CollisionInfo.e.getV1();
		Vertex e2 = CollisionInfo.e.getV2();

		Vector2D collisionVector = new Vector2D().addScaledVector(CollisionInfo.normal, CollisionInfo.depth);

		double t;
		Vector2D e1Pos = e1.getPosition();
		Vector2D e2Pos = e2.getPosition();
		if (Math.abs(e1Pos.getX() - e2Pos.getX()) > Math.abs(e1Pos.getY() - e2Pos.getY())) {
			t = (CollisionInfo.v.getPosition().getX() - collisionVector.getX() - e1Pos.getX())
					/ (e2Pos.getX() - e1Pos.getX());
		} else {
			t = (CollisionInfo.v.getPosition().getY() - collisionVector.getY() - e1Pos.getY())
					/ (e2Pos.getY() - e1Pos.getY());
		}
		double lambda = 1.0f / (t * t + (1 - t) * (1 - t));
		double edgeMass = t * e2.getBody().getMass() + (1f - t) * e1.getBody().getMass();
		/* Calculate the mass at the intersection point */
		double invCollisionMass = 1.0f / (edgeMass + CollisionInfo.v.getBody().getMass());

		double ratio1 = CollisionInfo.v.getBody().getMass() * invCollisionMass;
		double ratio2 = edgeMass * invCollisionMass;

		e1.getPosition().addScaledVector(collisionVector, -((1 - t) * ratio1 * lambda));
		e2.getPosition().addScaledVector(collisionVector, -(t * ratio1 * lambda));

		CollisionInfo.v.getPosition().addScaledVector(collisionVector, ratio2);
	}

	/**
	 * Renders the scene. TODO: Possibly move this method to another object.
	 * 
	 * @param g
	 *            Graphics
	 */
	public void render(Graphics g) {
		/* TODO: Monitor render time: */
		long start = System.nanoTime();
		g.setColor(Color.black);
		g.fillRect(0, 0, screenWidth, screenHeight);
		g.setColor(Color.PINK);

		for (int i = 0; i < edgeCount; i++) {
			g.drawLine((int) edges[i].getV1().getPosition().getX(), (int) edges[i].getV1().getPosition().getY(),
					(int) edges[i].getV2().getPosition().getX(), (int) edges[i].getV2().getPosition().getY());
		}

		g.setColor(Color.white);

		for (int i = 0; i < vertexCount; i++) {
			g.fillOval((int) vertices[i].getPosition().getX() - 2, (int) vertices[i].getPosition().getY() - 2, 4, 4);
		}
		long stop = System.nanoTime();
		System.out.println("render: " + ((double) (stop - start) / 1000000000));
	}

	/**
	 * For every vertex in the simulation:
	 * <ul>
	 * <li />Update forces acting upon the Vertex
	 * <li />Integrate the equations of movement
	 * <li />Resolve the collisions
	 * </ul>
	 * 
	 * @param dt
	 *            The timestep
	 */
	public void update(double dt) {
		/* TODO: Monitor update time: */
		start = System.nanoTime();
		for (int i = 0; i < vertexCount; ++i) {
			updateForces(vertices[i], dt);
		}
		stop = System.nanoTime();
		System.out.println("update forces: " + ((double) (stop - start) / 1000000000));
		start = System.nanoTime();
		for (int i = 0; i < vertexCount; ++i) {
			integrate(vertices[i], dt);
		}
		stop = System.nanoTime();
		System.out.println("integrate: " + ((double) (stop - start) / 1000000000));
		start = System.nanoTime();
		iterateCollisions();
		stop = System.nanoTime();
		System.out.println("collisions: " + ((double) (stop - start) / 1000000000));
		System.out.println();
	}

	/**
	 * Enforces the rigid body constraints:
	 * <ul>
	 * <li />Iterates through all the Edges (pairs of Vertices)
	 * <li />Calculates the current distance between them and .
	 * <li />Compares the current distance to the constrained distance
	 * <li />Pushes the Vertices apart by half the difference, so the distance
	 * is restored to the original length.
	 * </ul>
	 */
	private void updateEdges() {
		for (int i = 0; i < edgeCount; i++) {
			Edge e = edges[i];
			Vector2D v1v2 = e.getCurrentDistanceBetweenVertices();
			/* Calculate the difference from the original length */
			double diff = v1v2.magnitude() - e.getLength();
			v1v2.normalize();
			/* Push back */
			e.getV1().getPosition().addScaledVector(v1v2, diff * 0.5);
			e.getV2().getPosition().addScaledVector(v1v2, -diff * 0.5);
		}
	}

	/**
	 * Updates the forces acting upon the Vertices:
	 * <ul>
	 * <li />Clears the forceAccum of the particle
	 * <li />For every forceGeneratore associated with the particle, applies the
	 * force
	 * </ul>
	 * 
	 * @param v
	 *            The vertex to update
	 * @param dt
	 *            The timestep in seconds
	 */
	private void updateForces(Vertex v, double dt) {
		/*
		 * TODO: Apply other forces than gravity force:
		 * 
		 * for (int i = 0; i < vertexCount; ++i) { List forceGenerators =
		 * vertices[i].getForceGenerators(); for (ForceGenerator2D g :
		 * forceGenerators) { vertices[i].addForce(g.generateForce(vertices[i],
		 * dt)); } }
		 */
		v.clearForceAccum();
		v.addForce(gravity.generateForce(v, dt));
	}
}
