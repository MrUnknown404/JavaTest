package main.java.javatest.client;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import main.java.javatest.blocks.Block;
import main.java.javatest.client.gui.DebugHud;
import main.java.javatest.util.Console;
import main.java.javatest.util.CreateTestLevel;
import main.java.javatest.util.handlers.ObjectHandler;
import main.java.javatest.util.math.Vec2i;

public class JavaGameTest extends Canvas implements Runnable {

	private static final long serialVersionUID = -2518563563721413864L;
	
	public static final int WIDTH = 854, HEIGHT = 480;
	public static final int BLOCK_WIDTH = (WIDTH / Block.SIZE) - 0, BLOCK_HEIGHT = (HEIGHT / Block.SIZE) - 1;
	
	private int fps;
	private boolean running = false;
	private Thread thread;
	private ObjectHandler objectHandler = new ObjectHandler();
	
	
	public JavaGameTest() {
		new Window(WIDTH, HEIGHT, "Java Test!", this);
	}
	
	private void preInit() {
		System.out.println(Console.info(Console.EnumWarningType.Info) + "Pre-Initialization started!");
		System.out.println(Console.info() + "Window size: " + new Vec2i(WIDTH, HEIGHT).toString());
		System.out.println(Console.info() + "Block map size: " + new Vec2i(BLOCK_WIDTH, BLOCK_HEIGHT).toString());
		
		CreateTestLevel.createLevel(BLOCK_WIDTH, BLOCK_HEIGHT);
		
		addKeyListener(new KeyInput());
		addMouseListener(new MouseInput());
		addMouseMotionListener(new MouseInput());
		
		init();
	}
	
	private void init() {
		System.out.println(Console.info(Console.EnumWarningType.Info) + "Initialization started!");
		
		postInit();
	}
	
	private void postInit() {
		System.out.println(Console.info(Console.EnumWarningType.Info) + "Post-Initialization started!");
	}
	
	public synchronized void start() {
		System.out.println(Console.getTimeExample());
		System.out.println(Console.info(Console.EnumWarningType.Info) + "Starting!");
		
		thread = new Thread(this);
		thread.start();
		running = true;
		
		preInit();
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
		System.out.println(Console.info(Console.EnumWarningType.Info) + "Started Run Loop");
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
		objectHandler.tick();
	}
	
	private void gameTick() {
		objectHandler.gameTick();
	}
	
	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			System.out.println(Console.info(Console.EnumWarningType.Info) + "Started Render Loop");
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		objectHandler.render(g);
		DebugHud.drawText(g, "FPS: " + fps, 1, 15);
		DebugHud.getInfo();
		
		g.dispose();
		bs.show();
	}
	
	public static void main(String args[]) {
		new JavaGameTest();
	}
}
