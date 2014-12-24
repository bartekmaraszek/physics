package pl.bmaraszek.PhysicsBodies;

import pl.bmaraszek.Physics;
import pl.bmaraszek.math.Vector2D;

/**
 * @author Bartek Maraszek
 */
public class BodyFactory {
	
	private Physics world;
	
	public BodyFactory(Physics world){
		this.world =  world;
	}
	
	public void createRectangle(Vector2D a, double width, double height){
		this.createRectangle(a.getX(), a.getY(), width, height);
	}

	public void createRectangle(double x, double y, double width, double height){
		Body rectangle = new Body(world);
		
		Vertex v1 = new Vertex( world, rectangle, new Vector2D(x, y));
        Vertex v2 = new Vertex( world, rectangle, new Vector2D(x + width, y));
        Vertex v3 = new Vertex( world, rectangle, new Vector2D(x + width, y + height));
        Vertex v4 = new Vertex( world, rectangle, new Vector2D(x, y + height));

        new Edge( world, rectangle, v1, v2, true );
        new Edge( world, rectangle, v2, v3, true );
        new Edge( world, rectangle, v3, v4, true );
        new Edge( world, rectangle, v4, v1, true );

        new Edge( world, rectangle, v1, v3, false );
        new Edge( world, rectangle, v2, v4, false );
	}
	
	public void createTriangle(Vector2D a, Vector2D b, Vector2D c){
		Body triangle = new Body(world); 

		Vertex v1 = new Vertex(world, triangle, a);
		Vertex v2 = new Vertex(world, triangle, b);
		Vertex v3 = new Vertex(world, triangle, c);

		new Edge(world, triangle, v1, v2);
		new Edge(world, triangle, v2, v3);
		new Edge(world, triangle, v3, v1);
	}
	
}
