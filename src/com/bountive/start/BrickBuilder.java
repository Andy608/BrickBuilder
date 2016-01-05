package com.bountive.start;

import org.lwjgl.Sys;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import com.bountive.display.Window;
import com.bountive.display.callback.KeyboardCallback;
import com.bountive.display.callback.MousePositionCallback;
import com.bountive.graphics.render.BrickRenderer;
import com.bountive.graphics.view.FlyingCamera;
import com.bountive.init.InitializationHandler;
import com.bountive.setting.ControlOptions;
import com.bountive.util.logger.LoggerUtil;
import com.bountive.world.object.brick.AbstractBrick;
import com.bountive.world.object.brick.AbstractBrick.EnumBrickModel;
import com.bountive.world.object.brick.TestBrick;

public final class BrickBuilder {

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
	
	//TODO: MOVE THESE TO CORRECT SPOTS.
	private BrickRenderer renderer; // <-- Will be handled by the world rendering system.
	private AbstractBrick brick, brick2; // <-- Will be handled by the world generation system.
	private FlyingCamera c; // <-- Will be attached to a player.
	/////////////////////////////
	
	public BrickBuilder() {
		instance = this;
		LoggerUtil.logInfo(this.getClass(), "Creating " + Info.NAME + " " + Info.VERSION + " by " + Info.AUTHOR);
	}
	
	protected void run() {
		
		if (Sys.getVersion() == null) {
			System.out.println("HELLO?");
		}
		
//		LoggerUtil.logInfo(this.getClass(), "Initializing internal structure. Currently running on LWJGL " + Sys.getVersion() + ".");
		
		try {
			InitializationHandler.init();
			loop();
		} catch (Exception e) {
			LoggerUtil.logError(getClass(), Thread.currentThread(), e);
		} finally {
			Window.saveOptions();
			renderer.release();
			InitializationHandler.release();
			System.gc();
			System.exit(0);
		}
	}
	
	private void loop() {
		//TEMP. TODO: Move createCapabilities and model initialization to somewhere else.
//		GL.createCapabilities();									//////
//		ModelBrickList.createModels();								//////
		renderer = new BrickRenderer();								//////
		brick = new TestBrick(EnumBrickModel.FULL_1x1);			//////
		brick2 = new TestBrick(EnumBrickModel.FLAT_1x1);			//////
		c = new FlyingCamera();										//////
		
		MousePositionCallback.centerMouse(); 	//TODO: Move this to the world loading code.
		
		//////////////////////////////////////////////////////////////////
		
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
		
		//////TODO:Move this to somewhere better.
		if (!ControlOptions.isPaused()) {
			MousePositionCallback.centerMouse();
			c.update(deltaTime);
		}
		//////
		
		if (tickCount % TICKS_PER_SECOND == 0) {
			LoggerUtil.logInfo(this.getClass(), "Ticks: " + tickCount + ", Frames: " + frameCount);
			tickCount = 0;
			frameCount = 0;
		}
	}
	
	private void render(double lerp) {
		frameCount++;
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		
		if (KeyboardCallback.toggle)
			renderer.addBrickToBatch(brick);
		else
			renderer.addBrickToBatch(brick2);
		
		renderer.render(c);
		
		GLFW.glfwSwapBuffers(Window.getID());
	}
	
	public static BrickBuilder getInstance() {
		return instance;
	}
}
