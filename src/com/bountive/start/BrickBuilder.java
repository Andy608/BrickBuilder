package com.bountive.start;

import org.apache.log4j.Logger;
import org.lwjgl.Sys;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import com.bountive.brick.Brick;
import com.bountive.brick.ClayBrick;
import com.bountive.display.Window;
import com.bountive.graphics.model.ModelBrickList;
import com.bountive.graphics.render.BrickRenderer;
import com.bountive.init.InitializationHandler;
import com.bountive.util.logger.ErrorFileLogger;

public final class BrickBuilder {

	private static final Logger logger = Logger.getLogger(BrickBuilder.class);
	private static BrickBuilder instance;
	
	private static final int TICKS_PER_SECOND = 60;
	private static final double TIME_SLICE = 1 / (double)TICKS_PER_SECOND;
	private static final float LAG_CAP = 0.15f;
	
	private int tickCount;
	private int frameCount;
	
	private double lastTime;
	private double currentTime;
	private double deltaTime;
	private double elapsedTime;
	
	private BrickRenderer renderer;
	private ClayBrick brick;
	
	public BrickBuilder() {
		instance = this;
		logger.info("Creating " + Info.NAME + " " + Info.VERSION + " by " + Info.AUTHOR);
	}
	
	protected void run() {
		logger.info("Initializing internal structure. Currently running on LWJGL " + Sys.getVersion() + ".");
		
		try {
			InitializationHandler.init();
			loop();
		} catch (Exception e) {
			ErrorFileLogger.logError(Thread.currentThread(), e);
		} finally {
			Window.saveSettings();
			renderer.release();
			InitializationHandler.release();
			System.gc();
			System.exit(0);
		}
	}
	
	private void loop() {
		GL.createCapabilities();
		ModelBrickList.createModels();//TEMP. TODO: Move createCapabilities and model initialization to somewhere else.
		
		renderer = new BrickRenderer();
		brick = new ClayBrick(Brick.EnumBrickModel.FULL_1x1);
		
		lastTime = GLFW.glfwGetTime();
		
		while (GLFW.glfwWindowShouldClose(Window.getID()) == GL11.GL_FALSE) {
			currentTime = GLFW.glfwGetTime();
			deltaTime = currentTime - lastTime;
			lastTime = currentTime;
			elapsedTime += deltaTime;
			
			if (elapsedTime >= LAG_CAP) {
				elapsedTime = LAG_CAP;
			}
			
			while (elapsedTime >= TIME_SLICE) {
				elapsedTime -= TIME_SLICE;
				GLFW.glfwPollEvents();
				update(TIME_SLICE);
			}
			render((double)(elapsedTime / TIME_SLICE));
		}
	}
	
	private void update(double deltaTime) {
		tickCount++;
		
		if (tickCount % TICKS_PER_SECOND == 0) {
			System.out.println("Ticks: " + tickCount + ", Frames: " + frameCount);
			tickCount = 0;
			frameCount = 0;
		}
	}
	
	private void render(double lerp) {
		frameCount++;
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		
		renderer.addBrickToBatch(brick);
		renderer.render();
		
		GLFW.glfwSwapBuffers(Window.getID());
	}
	
	public static BrickBuilder getInstance() {
		return instance;
	}
}
