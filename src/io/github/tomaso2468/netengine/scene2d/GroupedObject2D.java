package io.github.tomaso2468.netengine.scene2d;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import io.github.tomaso2468.netengine.Game;
import io.github.tomaso2468.netengine.input.Input;
import io.github.tomaso2468.netengine.render.Renderer;

public class GroupedObject2D implements Object2D, List<Object2D> {
	private final List<Object2D> objects = new ArrayList<>();
	private Vector3f position = new Vector3f();
	private float rotation = 0;

	@Override
	public void draw(Game game, Renderer renderer, SceneParams params, Matrix4f transform) {
		transform = new Matrix4f(transform).mul(new Matrix4f(getTransform()));
		
		for (Object2D o : objects) {
			if (o.isTransparent()) {
				params.transparentObjects.add(new TransparentObject2D(o, new Matrix4f(transform)));
			} else {
				o.draw(game, renderer, params, transform);
			}
		}
	}
	public Vector3f getPosition() {
		return new Vector3f(position);
	}

	public void setPosition(Vector3f position) {
		this.position = new Vector3f(position);
	}

	public float getRotation() {
		return rotation;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}
	
	@Override
	public Iterator<Object2D> iterator() {
		return objects.iterator();
	}

	@Override
	public Object2D[] toArray() {
		return objects.toArray(new Object2D[size()]);
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return objects.toArray(a);
	}

	@Override
	public int lastIndexOf(Object o) {
		return objects.lastIndexOf(o);
	}

	@Override
	public ListIterator<Object2D> listIterator() {
		return objects.listIterator();
	}

	@Override
	public ListIterator<Object2D> listIterator(int index) {
		return objects.listIterator(index);
	}

	@Override
	public List<Object2D> subList(int fromIndex, int toIndex) {
		return objects.subList(fromIndex, toIndex);
	}

	@Override
	public int size() {
		return objects.size();
	}

	@Override
	public boolean isEmpty() {
		return objects.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return objects.contains(o);
	}

	@Override
	public boolean add(Object2D e) {
		return objects.add(e);
	}

	@Override
	public boolean remove(Object o) {
		return objects.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return objects.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends Object2D> c) {
		return objects.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends Object2D> c) {
		return objects.addAll(index, c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return objects.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return objects.retainAll(c);
	}

	@Override
	public void clear() {
		objects.clear();
	}

	@Override
	public Object2D get(int index) {
		return objects.get(index);
	}

	@Override
	public Object2D set(int index, Object2D element) {
		return objects.set(index, element);
	}

	@Override
	public void add(int index, Object2D element) {
		objects.add(index, element);
	}

	@Override
	public Object2D remove(int index) {
		return objects.remove(index);
	}

	@Override
	public int indexOf(Object o) {
		return objects.indexOf(o);
	}
	
	@Override
	public void update(Game game, Input input, float delta) {
		for (Object2D o : objects) {
			o.update(game, input, delta);
		}
	}
	
	@Override
	public void init(Game game, Renderer renderer) {
		for (Object2D o : objects) {
			o.init(game, renderer);
		}
	}
	@Override
	public void drawDepth(Game game, Renderer renderer, SceneParams params, Matrix4f transform) {
		transform = new Matrix4f(transform).mul(new Matrix4f(getTransform()));
		
		for (Object2D o : objects) {
			if (o.isTransparent()) {
				params.transparentObjects.add(new TransparentObject2D(o, new Matrix4f(transform)));
			} else {
				o.drawDepth(game, renderer, params, transform);
			}
		}
	}

}
