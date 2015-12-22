package com.bountive.shader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import com.bountive.resource.ResourceLocation;
import com.bountive.util.ErrorFileLogger;

public abstract class AbstractShader {

	private int shaderProgramID;
	private int vertexShaderID;
	private int fragmentShaderID;
	
	private ResourceLocation vertexLocation;
	private ResourceLocation fragmentLocation;
	
	public AbstractShader(ResourceLocation vertexFileLocation, ResourceLocation fragmentFileLocation) {
		shaderProgramID = GL20.glCreateProgram();
		vertexLocation = vertexFileLocation;
		fragmentLocation = fragmentFileLocation;
		createVertexShader();
		createFragmentShader();
		link();
		bindUniformVariables();
	}
	
	protected abstract void bindUniformVariables();
	
	/**
	 * Takes in the file location of the vertex shader and creates a vertex shader.
	 * @param fileLocation : The location of the shader.
	 */
	private void createVertexShader() {
		String vertexSource = readShaderFile(vertexLocation);
		vertexShaderID = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
		GL20.glShaderSource(vertexShaderID, vertexSource);
		
		GL20.glCompileShader(vertexShaderID);
		if (GL20.glGetShaderi(vertexShaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			ErrorFileLogger.logError(Thread.currentThread(), new IllegalStateException("Unable to compile vertex shader: " + vertexLocation.getResourceName()));
		}
		GL20.glAttachShader(shaderProgramID, vertexShaderID);
	}
	
	/**
	 * Takes in the file location of the fragment shader and creates a fragment shader.
	 * @param fileLocation : The location of the shader.
	 */
	private void createFragmentShader() {
		String fragmentSource = readShaderFile(fragmentLocation);
		fragmentShaderID = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
		GL20.glShaderSource(fragmentShaderID, fragmentSource);
		
		GL20.glCompileShader(fragmentShaderID);
		if (GL20.glGetShaderi(fragmentShaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			ErrorFileLogger.logError(Thread.currentThread(), new IllegalStateException("Unable to compile fragment shader: " + fragmentLocation.getResourceName()));
		}
		GL20.glAttachShader(shaderProgramID, fragmentShaderID);
	}
	
	/**
	 * Links the shader program to the current OpenGL context.
	 */
	private void link() {
		GL20.glLinkProgram(shaderProgramID);
		
		if (GL20.glGetProgrami(shaderProgramID, GL20.GL_LINK_STATUS) == GL11.GL_FALSE) {
			ErrorFileLogger.logError(Thread.currentThread(), new IllegalStateException("Unable to link shader program. Vertex Shader: " + vertexLocation.getResourceName() 
			+ ". Fragment Shader: " + fragmentLocation.getResourceName() + "."));
		}
	}
	
	private String readShaderFile(ResourceLocation loc) {
		StringBuilder shaderSource = new StringBuilder();
		
		try (BufferedReader r = new BufferedReader(new InputStreamReader(AbstractShader.class.getClassLoader().getResourceAsStream(loc.getFullPath())))) {
			String s;
			while ((s = r.readLine()) != null) {
				shaderSource.append(s);
			}
		} catch (IOException e) {
			ErrorFileLogger.logError(Thread.currentThread(), e);
		}
		
		return shaderSource.toString();
	}
}
