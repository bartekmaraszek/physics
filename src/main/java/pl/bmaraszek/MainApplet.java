package pl.bmaraszek;

import java.awt.AWTEvent;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

import javax.swing.BoxLayout;
import javax.swing.JApplet;

import pl.bmaraszek.PhysicsBodies.BodyFactory;
import pl.bmaraszek.PhysicsBodies.Vertex;
import pl.bmaraszek.math.Vector2D;

@SuppressWarnings("serial")
public class MainApplet extends JApplet implements Runnable {
	static final int FPS = 30;
	static final int SECOND_TO_NANOSECOND = 1000000000; /* a billion */
	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 450;

	/* The vertex that is currently dragged around with the mouse */
	private Vertex dragVertex = null;

	/* The main physics world */
	private Physics world;
	private BodyFactory bodyFactory;

	private int mouseX;
	private int mouseY;

	@Override
	public void start() {
		this.enableEvents(AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK);
		new Thread(this).start();
	}

	@Override
	protected void processEvent(AWTEvent e) {
		switch (e.getID()) {
		case Event.MOUSE_DOWN:
			/* Set a new Vertex to be dragged in left mouse button was clicked */
			if (e instanceof MouseEvent) {
				mouseX = ((MouseEvent) e).getX();
				mouseY = ((MouseEvent) e).getY();
				if (((MouseEvent) e).getButton() == 1) {
					dragVertex = world.findVertex(mouseX, mouseY);
				} else {
					bodyFactory.createRectangle(mouseX - 20, mouseY - 30, 40, 60);
				}
			}
			break;
		case Event.MOUSE_UP:
			/* Stop dragging the vertex */
			dragVertex = null;
			break;
		case Event.MOUSE_MOVE:
			break;
		case Event.MOUSE_DRAG:
			if (e instanceof MouseEvent) {
				mouseX = ((MouseEvent) e).getX();
				mouseY = ((MouseEvent) e).getY();
			}
			break;
		}
	}
	
	private void setOutputFile(){
		File file = null;
		FileOutputStream fis = null;
		try{
		file = new File("log.txt");  
		fis = new FileOutputStream(file); 
		}catch(Exception e){
			System.out.println(e);
		}
		PrintStream out = new PrintStream(fis);  
		System.setOut(out);  
		System.out.println("First Line");
	}

	private void initPhysics(){
		setOutputFile();
		/* 
		 * Create a new physics instance with gravity pointing downwards and
		 * using 12 iterations
		 */
		world = new Physics(SCREEN_WIDTH, SCREEN_HEIGHT, 12);
		bodyFactory = new BodyFactory(world);
		
		for (int x = 20; x < SCREEN_WIDTH; x += 100) {
			for (int y = 50; y < SCREEN_HEIGHT - 50; y += 100) { 
				bodyFactory.createRectangle(x, y, 50, 50);
			}
		}
		for (int x = 50; x < SCREEN_WIDTH - 50; x += 130) {
			bodyFactory.createTriangle(new Vector2D(x, 45), new Vector2D(x + 50, 0), new Vector2D(x + 100, 45));
		}
	}

	@Override
	public void run() {
		/* The time we started calculating this frame */
		long lastFrame = 0;
		/* For AppletViewer, remove later */
		setSize(SCREEN_WIDTH, SCREEN_HEIGHT);

		/* Set up the graphics and double-buffering */
		BufferedImage screen = new BufferedImage(SCREEN_WIDTH, SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics g = screen.getGraphics();
		Graphics appletGraphics = getGraphics();

		/* Set up the physics */
		initPhysics();
		/* Wait until the graphics context is valid */
		while (!isActive()) {
			Thread.yield();
		}

		/* Game loop */
		do {
			/* Run at correct FPS
			 * TODO: Monitor FPS
			 */
			long start = System.nanoTime();
			while (System.nanoTime() < lastFrame + SECOND_TO_NANOSECOND / FPS) {
				try {
					/* Give the EDT lots of time */
					Thread.sleep(1);
					System.out.println("---------sleep----------");
				} catch (Throwable e) {
				}
			}
			lastFrame = System.nanoTime();

			/*
			 * Set the position of the dragVertex to the mouse position to drag
			 * it around
			 */
			if (dragVertex != null) {
				dragVertex.getPosition().set(mouseX, mouseY);
			}

			world.update(1.0 / FPS);
			/* TODO: Free the physics simulation! */
			world.render(g);

			/* Draw everything on screen */
			appletGraphics.drawImage(screen, 0, 0, null);
			long stop = System.nanoTime();
			System.out.println("FPS: " + (1.0 / ((double) (stop - start) / 1000000000)));
		} while (this.isActive());
	}
}