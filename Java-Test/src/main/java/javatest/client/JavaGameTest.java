package main.java.javatest.client;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import main.java.javatest.entity.entityliving.EntityPlayer;
import main.java.javatest.entity.entityliving.EntityTest;
import main.java.javatest.util.Console;
import main.java.javatest.util.EnumWarningType;
import main.java.javatest.util.handlers.ObjectHandler;

public class JavaGameTest extends Canvas implements Runnable {

	private static final long serialVersionUID = -2518563563721413864L;
	
	public static final int WIDTH = 854, HEIGHT = 480;
	
	private boolean running = false;
	private Thread thread;
	private ObjectHandler objectHandler = new ObjectHandler();
	
	public JavaGameTest() {
		addKeyListener(new KeyInput());
		
		new Window(WIDTH, HEIGHT, "Java Test!", this);
		
		//* do this in a for loop
		ObjectHandler.addObject(new EntityTest((WIDTH / 2) - 336, (HEIGHT / 2) - 24, 24, 24));
		ObjectHandler.addObject(new EntityTest((WIDTH / 2) - 312, (HEIGHT / 2) - 24, 24, 24));
		ObjectHandler.addObject(new EntityTest((WIDTH / 2) - 288, (HEIGHT / 2) - 24, 24, 24));
		ObjectHandler.addObject(new EntityTest((WIDTH / 2) - 264, (HEIGHT / 2) - 24, 24, 24));
		ObjectHandler.addObject(new EntityTest((WIDTH / 2) - 240, (HEIGHT / 2) - 24, 24, 24));
		ObjectHandler.addObject(new EntityTest((WIDTH / 2) - 216, (HEIGHT / 2) - 24, 24, 24));
		ObjectHandler.addObject(new EntityTest((WIDTH / 2) - 192, (HEIGHT / 2) - 24, 24, 24));
		ObjectHandler.addObject(new EntityTest((WIDTH / 2) - 168, (HEIGHT / 2) - 24, 24, 24));
		ObjectHandler.addObject(new EntityTest((WIDTH / 2) - 144, (HEIGHT / 2) - 24, 24, 24));
		ObjectHandler.addObject(new EntityTest((WIDTH / 2) - 120, (HEIGHT / 2) - 24, 24, 24));
		ObjectHandler.addObject(new EntityTest((WIDTH / 2) - 96,  (HEIGHT / 2) - 24, 24, 24));
		ObjectHandler.addObject(new EntityTest((WIDTH / 2) - 72,  (HEIGHT / 2) - 24, 24, 24));
		ObjectHandler.addObject(new EntityTest((WIDTH / 2) - 48,  (HEIGHT / 2) - 24, 24, 24));
		ObjectHandler.addObject(new EntityTest((WIDTH / 2) - 24,  (HEIGHT / 2) - 24, 24, 24));
		ObjectHandler.addObject(new EntityTest((WIDTH / 2),       (HEIGHT / 2) - 24, 24, 24));
		ObjectHandler.addObject(new EntityTest((WIDTH / 2) + 24,  (HEIGHT / 2) - 24, 24, 24));
		ObjectHandler.addObject(new EntityTest((WIDTH / 2) + 48,  (HEIGHT / 2) - 24, 24, 24));
		ObjectHandler.addObject(new EntityTest((WIDTH / 2) + 72,  (HEIGHT / 2) - 24, 24, 24));
		ObjectHandler.addObject(new EntityTest((WIDTH / 2) + 96,  (HEIGHT / 2) - 24, 24, 24));
		ObjectHandler.addObject(new EntityTest((WIDTH / 2) + 120, (HEIGHT / 2) - 24, 24, 24));
		
		ObjectHandler.addObject(new EntityTest((WIDTH / 2) + 48, (HEIGHT / 2) - 48, 24, 24));
		ObjectHandler.addObject(new EntityTest((WIDTH / 2) + 48, (HEIGHT / 2) - 72, 24, 24));
		ObjectHandler.addObject(new EntityTest((WIDTH / 2) + 48, (HEIGHT / 2) - 96, 24, 24));
		
		ObjectHandler.addObject(new EntityTest((WIDTH / 2) - 120, (HEIGHT / 2) - 48,  24, 24));
		ObjectHandler.addObject(new EntityTest((WIDTH / 2) - 144, (HEIGHT / 2) - 72,  24, 24));
		ObjectHandler.addObject(new EntityTest((WIDTH / 2) - 168, (HEIGHT / 2) - 96,  24, 24));
		ObjectHandler.addObject(new EntityTest((WIDTH / 2) - 192, (HEIGHT / 2) - 120, 24, 24));
		ObjectHandler.addObject(new EntityTest((WIDTH / 2) - 216, (HEIGHT / 2) - 96,  24, 24));
		ObjectHandler.addObject(new EntityTest((WIDTH / 2) - 240, (HEIGHT / 2) - 72,  24, 24));
		ObjectHandler.addObject(new EntityTest((WIDTH / 2) - 264, (HEIGHT / 2) - 48,  24, 24));
		
		ObjectHandler.addObject(new EntityTest((WIDTH / 2) - 72,  (HEIGHT / 2) - 96,  24, 24));
		ObjectHandler.addObject(new EntityTest((WIDTH / 2) - 96,  (HEIGHT / 2) - 120, 24, 24));
		ObjectHandler.addObject(new EntityTest((WIDTH / 2) - 120, (HEIGHT / 2) - 144, 24, 24));
		ObjectHandler.addObject(new EntityTest((WIDTH / 2) - 144, (HEIGHT / 2) - 168, 24, 24));
		ObjectHandler.addObject(new EntityTest((WIDTH / 2) - 168, (HEIGHT / 2) - 192, 24, 24));
		ObjectHandler.addObject(new EntityTest((WIDTH / 2) - 192, (HEIGHT / 2) - 216, 24, 24));
		ObjectHandler.addObject(new EntityTest((WIDTH / 2) - 216, (HEIGHT / 2) - 192, 24, 24));
		ObjectHandler.addObject(new EntityTest((WIDTH / 2) - 240, (HEIGHT / 2) - 168, 24, 24));
		ObjectHandler.addObject(new EntityTest((WIDTH / 2) - 264, (HEIGHT / 2) - 144, 24, 24));
		ObjectHandler.addObject(new EntityTest((WIDTH / 2) - 288, (HEIGHT / 2) - 120, 24, 24));
		ObjectHandler.addObject(new EntityTest((WIDTH / 2) - 312, (HEIGHT / 2) - 96,  24, 24));
		//*/
		
		ObjectHandler.addObject(new EntityPlayer((WIDTH / 2) - 24, (HEIGHT / 2) - 256, 24, 48));
	}
	
	public synchronized void start() {
		System.out.println(Console.info(EnumWarningType.Info) + "Starting!");
		thread = new Thread(this);
		thread.start();
		running = true;
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
		System.out.println(Console.info(EnumWarningType.Info) + "Started Run Loop");
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
					System.out.println(Console.info() + "FPS: " + frames);
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
			System.out.println(Console.info(EnumWarningType.Info) + "Started Render Loop");
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		objectHandler.render(g);
		
		g.dispose();
		bs.show();
	}
	
	public static void main(String args[]) {
		new JavaGameTest();
	}
}
