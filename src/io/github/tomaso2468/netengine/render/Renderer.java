package io.github.tomaso2468.netengine.render;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

import io.github.tomaso2468.netengine.Color;

public interface Renderer extends WindowingSystem {
	public default void preInit() {
		
	}
	public default void init() {
		
	}
	public void startFrame();
	public void clearScreen(Color color);
	
	public VertexObject createStaticVO(float[] vertices);
	public IndexedVertexObject createStaticVO(float[] vertices, int[] indices);
	public TexturedVertexObject createStaticVOTextured(float[] data, int[] indices);
	public RenderState createRenderState();
	
	public Shader createShader(String[] vertexShaders, String[] fragmentShaders);
	public default Shader createShader(InputStream[] vertexShaders, InputStream[] fragmentShaders) throws IOException {
		String[] vertexShaders2 = new String[vertexShaders.length];
		String[] fragmentShaders2 = new String[fragmentShaders.length];
		
		
		for (int i = 0; i < vertexShaders.length; i++) {
			StringBuilder textBuilder = new StringBuilder();
			
			InputStream in = vertexShaders[i];
			try (Reader reader = new BufferedReader(new InputStreamReader(in))) {
		        int c = 0;
		        while ((c = reader.read()) != -1) {
		            textBuilder.append((char) c);
		        }
		    }
			
			vertexShaders2[i] = textBuilder.toString();
		}
		
		for (int i = 0; i < fragmentShaders.length; i++) {
			StringBuilder textBuilder = new StringBuilder();
			
			InputStream in = fragmentShaders[i];
			try (Reader reader = new BufferedReader(new InputStreamReader(in))) {
		        int c = 0;
		        while ((c = reader.read()) != -1) {
		            textBuilder.append((char) c);
		        }
		    }
			
			fragmentShaders2[i] = textBuilder.toString();
		}
		
		return createShader(vertexShaders2, fragmentShaders2);
	}
	public void setShader(Shader shader);
	public default Shader createShader(URL[] vertexShaders, URL[] fragmentShaders) throws IOException {
		InputStream[] vertexShaders2 = new InputStream[vertexShaders.length];
		InputStream[] fragmentShaders2 = new InputStream[fragmentShaders.length];
		
		
		for (int i = 0; i < vertexShaders.length; i++) {
			vertexShaders2[i] = vertexShaders[i].openStream();
		}
		
		for (int i = 0; i < fragmentShaders.length; i++) {
			fragmentShaders2[i] = fragmentShaders[i].openStream();
		}
		
		return createShader(vertexShaders2, fragmentShaders2);
	}
	public default Shader createShader(URL vertexShader, URL fragmentShader) throws IOException {
		return createShader(new URL[] {vertexShader}, new URL[] {fragmentShader});
	}
	public default Shader createShader(File[] vertexShaders, File[] fragmentShaders) throws IOException {
		InputStream[] vertexShaders2 = new InputStream[vertexShaders.length];
		InputStream[] fragmentShaders2 = new InputStream[fragmentShaders.length];
		
		
		for (int i = 0; i < vertexShaders.length; i++) {
			vertexShaders2[i] = new FileInputStream(vertexShaders[i]);
		}
		
		for (int i = 0; i < fragmentShaders.length; i++) {
			fragmentShaders2[i] = new FileInputStream(fragmentShaders[i]);
		}
		
		return createShader(vertexShaders2, fragmentShaders2);
	}
	
	public void drawTriangles(float[] vertices);
	public void drawTriangles(float[] vertices, int[] indices);
	public void drawTrianglesTextured(float[] data, int[] indices);
}
