package main.java.javatest;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import main.java.javatest.blocks.Block;
import main.java.javatest.client.Camera;
import main.java.javatest.client.KeyInput;
import main.java.javatest.client.MouseInput;
import main.java.javatest.client.Renderer;
import main.java.javatest.client.Window;
import main.java.javatest.client.gui.DebugHud;
import main.java.javatest.util.Console;
import main.java.javatest.util.ObjectHandler;
import main.java.javatest.util.math.Vec2i;
import main.java.javatest.world.World;

public class Main extends Canvas implements Runnable {

	private static final long serialVersionUID = -2518563563721413864L;
	
	public static final int WIDTH = 854, HEIGHT = 480;
	public static final int BLOCK_WIDTH = (WIDTH / Block.getBlockSize()) - 0, BLOCK_HEIGHT = (HEIGHT / Block.getBlockSize()) - 1;
	
	private int fps;
	private boolean running = false;
	private Thread thread;
	private static ObjectHandler objectHandler = new ObjectHandler();
	private static Camera camera = new Camera();
	private Renderer renderer = new Renderer();
	private DebugHud debugHud = new DebugHud();
	
	public static void main(String args[]) {
		new Main();
	}
	
	public Main() {
		new Window(WIDTH, HEIGHT, "Java Test!", this);
	}
	
	public synchronized void start() {
		System.out.println(Console.getTimeExample());
		System.out.println(Console.info() + "Window size: " + new Vec2i(WIDTH, HEIGHT).toString());
		System.out.println(Console.info() + "Block map size: " + new Vec2i(BLOCK_WIDTH, BLOCK_HEIGHT).toString());
		System.out.println(Console.info(Console.WarningType.Info) + "Starting!");
		
		preInit();
		init();
		postInit();
	}
	
	private void preInit() {
		System.out.println(Console.info(Console.WarningType.Info) + "Pre-Initialization started...");
		
		renderer.findTextures();
		
		World.generateWorld(200, 100);
		
		MouseInput mouse = new MouseInput(camera);
		
		addKeyListener(new KeyInput());
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		
		System.out.println(Console.info(Console.WarningType.Info) + "Pre-Initialization Finished!");
		
	}
	
	private void init() {
		System.out.println(Console.info(Console.WarningType.Info) + "Initialization started...");
		
		System.out.println(Console.info(Console.WarningType.Info) + "Initialization Finished!");
	}
	
	private void postInit() {
		System.out.println(Console.info(Console.WarningType.Info) + "Post-Initialization started...");
		
		thread = new Thread(this);
		thread.start();
		running = true;
		
		System.out.println(Console.info(Console.WarningType.Info) + "Post-Initialization Finished!");
	}
	
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		System.out.println(Console.info(Console.WarningType.Info) + "Started Run Loop!");
		requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double amountOfGameTicks = 20.0;
		double ns = 1000000000 / amountOfTicks;
		double ns2 = 1000000000 / amountOfGameTicks;
		double delta = 0;
		double delta2 = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			delta2 += (now - lastTime) / ns2;
			lastTime = now;
			
			while (delta >= 1) {
				tick();
				delta--;
			}
			
			while (delta2 >= 1) {
				gameTick();
				delta2--;
			}
			
			if (running) {
				render();
				frames++;
				
				if (System.currentTimeMillis() - timer > 1000) {
					timer += 1000;
					fps = frames;
					frames = 0;
				}
			} else {
				stop();
			}
		}
	}
	
	private void tick() {
		camera.tick();
		objectHandler.tick();
	}
	
	private void gameTick() {
		objectHandler.gameTick();
	}
	
	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			System.out.println(Console.info(Console.WarningType.Info) + "Started Render Loop!");
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2 = (Graphics2D) g;
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		g2.translate(camera.getPositionX(), camera.getPositionY());
		
		renderer.render(g);
		
		g2.translate(-camera.getPositionX(), -camera.getPositionY());
		
		debugHud.drawText(g, "FPS: " + fps, 1, 15);
		DebugHud.getInfo();
		
		g.dispose();
		bs.show();
	}
	
	public static Camera getCamera() {
		return camera;
	}
}