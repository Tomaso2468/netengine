package io.github.tomaso2468.netengine.render;

import org.joml.Matrix2f;
import org.joml.Matrix3f;
import org.joml.Matrix3x2f;
import org.joml.Matrix4f;
import org.joml.Matrix4x3f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import io.github.tomaso2468.netengine.Color;

public interface Shader extends RenderResource {
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
	
	public default void setUniformTextureUnit(String name, int unit) {
		setUniform1i(name, unit);
	}
	
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
	
	public void setUniform1b(String name, boolean x);
	public void setUniform2b(String name, boolean x, boolean y);
	public void setUniform3b(String name, boolean x, boolean y, boolean z);
	public void setUniform4b(String name, boolean x, boolean y, boolean z, boolean w);
	
	public default void setUniformColor(String name, Color color) {
		setUniform4f(name, color.r, color.g, color.b, color.a);
	}
	
	public void setUniform1fO(String name, float x);
	public void setUniform2fO(String name, float x, float y);
	public default void setUniform2fO(String name, Vector2f v) {
		setUniform2f(name, v.get(0), v.get(1));
	}
	public void setUniform3fO(String name, float x, float y, float z);
	public default void setUniform3fO(String name, Vector3f v) {
		setUniform3f(name, v.get(0), v.get(1), v.get(2));
	}
	public void setUniform4fO(String name, float x, float y, float z, float w);
	public default void setUniform4fO(String name, Vector4f v) {
		setUniform4f(name, v.get(0), v.get(1), v.get(2), v.get(3));
	}
	
	public void setUniform1iO(String name, int x);
	public void setUniform2iO(String name, int x, int y);
	public void setUniform3iO(String name, int x, int y, int z);
	public void setUniform4iO(String name, int x, int y, int z, int w);
	
	public default void setUniformTextureUnitO(String name, int unit) {
		setUniform1iO(name, unit);
	}
	
	public void setUniform1uiO(String name, int x);
	public void setUniform2uiO(String name, int x, int y);
	public void setUniform3uiO(String name, int x, int y, int z);
	public void setUniform4uiO(String name, int x, int y, int z, int w);
	
	public default void setUniform1dO(String name, double x) {
		setUniform1f(name, (float) x);
	}
	public default void setUniform2dO(String name, double x, double y) {
		setUniform2f(name, (float) x, (float) y);
	}
	public default void setUniform3dO(String name, double x, double y, double z) {
		setUniform3f(name, (float) x, (float) y, (float) z);
	}
	public default void setUniform4dO(String name, double x, double y, double z, double w) {
		setUniform4f(name, (float) x, (float) y, (float) z, (float) w);
	}
	
	public void setUniformMatrix2O(String name, Matrix2f m);
	public void setUniformMatrix3O(String name, Matrix3f m);
	public void setUniformMatrix4O(String name, Matrix4f m);
	public void setUniformMatrix3x2O(String name, Matrix3x2f m);
	public void setUniformMatrix4x3O(String name, Matrix4x3f m);
	
	public void setUniform1bO(String name, boolean x);
	public void setUniform2bO(String name, boolean x, boolean y);
	public void setUniform3bO(String name, boolean x, boolean y, boolean z);
	public void setUniform4bO(String name, boolean x, boolean y, boolean z, boolean w);
	
	public default void setUniformColorO(String name, Color color) {
		setUniform4f(name, color.r, color.g, color.b, color.a);
	}
	
	public void startUse();
	public void endUse();
}
