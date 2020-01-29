package io.github.tomaso2468.netengine.render;

import org.joml.Matrix2f;
import org.joml.Matrix3f;
import org.joml.Matrix3x2f;
import org.joml.Matrix4f;
import org.joml.Matrix4x3f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public interface Shader {
	public void setUniform1f(String name, float x);
	public void setUniform2f(String name, float x, float y);
	public default void setUniform2f(String name, Vector2f v) {
		setUniform2f(name, v.get(0), v.get(1));
	}
	public void setUniform3f(String name, float x, float y, float z);
	public default void setUniform3f(String name, Vector3f v) {
		setUniform3f(name, v.get(0), v.get(1), v.get(2));
	}
	public void setUniform4f(String name, float x, float y, float z, float w);
	public default void setUniform4f(String name, Vector4f v) {
		setUniform4f(name, v.get(0), v.get(1), v.get(2), v.get(3));
	}
	
	public void setUniform1i(String name, int x);
	public void setUniform2i(String name, int x, int y);
	public void setUniform3i(String name, int x, int y, int z);
	public void setUniform4i(String name, int x, int y, int z, int w);
	
	public void setUniform1ui(String name, int x);
	public void setUniform2ui(String name, int x, int y);
	public void setUniform3ui(String name, int x, int y, int z);
	public void setUniform4ui(String name, int x, int y, int z, int w);
	
	public default void setUniform1d(String name, double x) {
		setUniform1f(name, (float) x);
	}
	public default void setUniform2d(String name, double x, double y) {
		setUniform2f(name, (float) x, (float) y);
	}
	public default void setUniform3d(String name, double x, double y, double z) {
		setUniform3f(name, (float) x, (float) y, (float) z);
	}
	public default void setUniform4d(String name, double x, double y, double z, double w) {
		setUniform4f(name, (float) x, (float) y, (float) z, (float) w);
	}
	
	public void setUniformMatrix2(String name, Matrix2f m);
	public void setUniformMatrix3(String name, Matrix3f m);
	public void setUniformMatrix4(String name, Matrix4f m);
	public void setUniformMatrix3x2(String name, Matrix3x2f m);
	public void setUniformMatrix4x3(String name, Matrix4x3f m);
	
	public void startUse();
	public void endUse();
}