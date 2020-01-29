package io.github.tomaso2468.netengine;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.IntBuffer;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

public class HelloWorld2 {

	// The window handle
	private long window;

	public void run() {
		System.out.println("Hello LWJGL " + Version.getVersion() + "!");

		init();
		
		GL.createCapabilities();
		
		String vertexShaderSource = "#version 330 core\n" + 
			    "layout (location = 0) in vec3 aPos;\n" + 
			    "void main()\n" + 
			    "{\n" + 
			    "   gl_Position = vec4(aPos.x, aPos.y, aPos.z, 1.0);\n" +
			    "}";
		String fragmentShaderSource = "#version 330 core\n" + 
			    "out vec4 FragColor;\n" + 
			    "void main()\n" + 
			    "{\n" + 
			    "   FragColor = vec4(1.0f, 0.5f, 0.2f, 1.0f);\n" + 
			    "}\n";
		
		// build and compile our shader program
	    // ------------------------------------
	    // vertex shader
	    int vertexShader = glCreateShader(GL_VERTEX_SHADER);
	    glShaderSource(vertexShader, vertexShaderSource);
	    glCompileShader(vertexShader);
	    
	    // fragment shader
	    int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
	    glShaderSource(fragmentShader, fragmentShaderSource);
	    glCompileShader(fragmentShader);
	    
	    // link shaders
	    int shaderProgram = glCreateProgram();
	    glAttachShader(shaderProgram, vertexShader);
	    glAttachShader(shaderProgram, fragmentShader);
	    glLinkProgram(shaderProgram);

	    glDeleteShader(vertexShader);
	    glDeleteShader(fragmentShader);

	    // set up vertex data (and buffer(s)) and configure vertex attributes
	    // ------------------------------------------------------------------
	    float vertices[] = {
	        -0.5f, -0.5f, 0.0f, // left  
	         0.5f, -0.5f, 0.0f, // right 
	         0.0f,  0.5f, 0.0f  // top   
	    }; 

	    int VBO, VAO;
	    VAO = glGenVertexArrays();
	    VBO = glGenBuffers();
	    // bind the Vertex Array Object first, then bind and set vertex buffer(s), and then configure vertex attributes(s).
	    glBindVertexArray(VAO);

	    glBindBuffer(GL_ARRAY_BUFFER, VBO);
	    glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

	    glVertexAttribPointer(0, 3, GL_FLOAT, false, 3 * 4, 0);
	    glEnableVertexAttribArray(0);

	    // note that this is allowed, the call to glVertexAttribPointer registered VBO as the vertex attribute's bound vertex buffer object so afterwards we can safely unbind
	    glBindBuffer(GL_ARRAY_BUFFER, 0); 

	    // You can unbind the VAO afterwards so other VAO calls won't accidentally modify this VAO, but this rarely happens. Modifying other
	    // VAOs requires a call to glBindVertexArray anyways so we generally don't unbind VAOs (nor VBOs) when it's not directly necessary.
	    glBindVertexArray(0); 


	    // uncomment this call to draw in wireframe polygons.
	    //glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);

	    // render loop
	    // -----------
	    while (!glfwWindowShouldClose(window))
	    {

	        // render
	        // ------
	        glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
	        glClear(GL_COLOR_BUFFER_BIT);

	        // draw our first triangle
	        glUseProgram(shaderProgram);
	        glBindVertexArray(VAO); // seeing as we only have a single VAO there's no need to bind it every time, but we'll do so to keep things a bit more organized
	        glDrawArrays(GL_TRIANGLES, 0, 3);
	        // glBindVertexArray(0); // no need to unbind it every time 
	 
	        // glfw: swap buffers and poll IO events (keys pressed/released, mouse moved etc.)
	        // -------------------------------------------------------------------------------
	        glfwSwapBuffers(window);
	        glfwPollEvents();
	    }

		// Free the window callbacks and destroy the window
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);

		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}

	private void init() {
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		GLFWErrorCallback.createPrint(System.err).set();

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if ( !glfwInit() )
			throw new IllegalStateException("Unable to initialize GLFW");

		// Configure GLFW
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

		// Create the window
		window = glfwCreateWindow(300, 300, "Hello World!", NULL, NULL);
		if ( window == NULL )
			throw new RuntimeException("Failed to create the GLFW window");

		// Setup a key callback. It will be called every time a key is pressed, repeated or released.
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
				glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
		});

		// Get the thread stack and push a new frame
		try ( MemoryStack stack = stackPush() ) {
			IntBuffer pWidth = stack.mallocInt(1); // int*
			IntBuffer pHeight = stack.mallocInt(1); // int*

			// Get the window size passed to glfwCreateWindow
			glfwGetWindowSize(window, pWidth, pHeight);

			// Get the resolution of the primary monitor
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			// Center the window
			glfwSetWindowPos(
				window,
				(vidmode.width() - pWidth.get(0)) / 2,
				(vidmode.height() - pHeight.get(0)) / 2
			);
		} // the stack frame is popped automatically

		// Make the OpenGL context current
		glfwMakeContextCurrent(window);
		// Enable v-sync
		glfwSwapInterval(1);

		// Make the window visible
		glfwShowWindow(window);
	}

	public static void main(String[] args) {
		new HelloWorld2().run();
	}
}
