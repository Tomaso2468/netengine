package io.github.tomaso2468.netengine.scene3d;

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

public class GroupedObject3D implements Object3D, List<Object3D> {
	private final List<Object3D> objects = new ArrayList<>();
	private Vector3f position = new Vector3f();
	private Vector3f rotation = new Vector3f();

	@Override
	public void draw(Game game, Renderer renderer, SceneParams params, Matrix4f transform) {
		transform = new Matrix4f(transform).mul(new Matrix4f(getTransform()));
		
		for (Object3D o : objects) {
			o.draw(game, renderer, params, transform);
		}
	}
	public Vector3f getPosition() {
		return new Vector3f(position);
	}

	public void setPosition(Vector3f position) {
		this.position = new Vector3f(position);
	}

	public Vector3f getRotation() {
		return new Vector3f(rotation);
	}

	public void setRotation(Vector3f rotation) {
		this.rotation = new Vector3f(rotation);
	}
	
	@Override
	public Iterator<Object3D> iterator() {
		return objects.iterator();
	}

	@Override
	public Object3D[] toArray() {
		return objects.toArray(new Object3D[size()]);
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
	public ListIterator<Object3D> listIterator() {
		return objects.listIterator();
	}

	@Override
	public ListIterator<Object3D> listIterator(int index) {
		return objects.listIterator(index);
	}

	@Override
	public List<Object3D> subList(int fromIndex, int toIndex) {
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
	public boolean add(Object3D e) {
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
	public boolean addAll(Collection<? extends Object3D> c) {
		return objects.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends Object3D> c) {
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
	public Object3D get(int index) {
		return objects.get(index);
	}

	@Override
	public Object3D set(int index, Object3D element) {
		return objects.set(index, element);
	}

	@Override
	public void add(int index, Object3D element) {
		objects.add(index, element);
	}

	@Override
	public Object3D remove(int index) {
		return objects.remove(index);
	}

	@Override
	public int indexOf(Object o) {
		return objects.indexOf(o);
	}
	
	@Override
	public void update(Game game, Input input, float delta) {
		for (Object3D o : objects) {
			o.update(game, input, delta);
		}
	}

}
