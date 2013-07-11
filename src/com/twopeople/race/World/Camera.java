package com.twopeople.race.World;

import com.twopeople.race.entity.Entity;

public class Camera {
	private int width, height;
	private float targetX, targetY;
	private float x, y;
	private int mapWidth = -1, mapHeight = -1;
	private float shakeAnimationState, shakeAmplitude;

	public Camera(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public void update(int delta) {
		x += (targetX - x) * delta * 0.01f;
		y += (targetY - y) * delta * 0.01f;

		shakeAnimationState += delta * .001f;
		shakeAmplitude += -shakeAmplitude * delta * .0099f * 2.5f;
	}

	public void setWorldSize(int w, int h) {
		this.mapWidth = w;
		this.mapHeight = h;
	}

	public float getX() {
		return (float) (Math.floor(x) + Math.cos(shakeAnimationState * Math.PI * 30) * shakeAmplitude);
	}

	public float getY() {
		return (float) (Math.floor(y) + Math.sin(shakeAnimationState * Math.PI * 13) * shakeAmplitude * .7f);
	}

	public float getScreenX(float xx) {
		return xx - getX();
	}

	public float getScreenY(float yy) {
		return yy - getY();
	}

	public int getScreenWidth() {
		return width;
	}

	public int getScreenHeight() {
		return height;
	}

	public void setTargetX(float x) {
		this.targetX = x;
		if (mapWidth != -1) {
			if (targetX < 0) targetX = 0;
			if (targetX + width > mapWidth) targetX = mapWidth - width;
		}
	}

	public void setTargetY(float y) {
		this.targetY = y;
		if (mapHeight != -1) {
			if (targetY < 0) targetY = 0;
			if (targetY + height > mapHeight) targetY = mapHeight - height;
		}
	}

	public float getAbsoluteX(float x) {
		return this.x + x;
	}

	public float getAbsoluteY(float y) {
		return this.y + y;
	}
	
	public void centerOn(Entity entity) {
		setTargetX(entity.x - entity.w / 2 - getScreenWidth() * .25f);
		setTargetY(entity.y - entity.h / 2 - getScreenHeight() * .3f);
	}

	public boolean isVisible(Entity entity) {
		return entity.x >= getX() - entity.w && entity.y >= getY() - entity.h && entity.x <= getX() + getScreenWidth()
				&& entity.y <= getY() + getScreenHeight();
	}

	public void shake(int shakes) {
		shakeAmplitude += shakes;
	}
}