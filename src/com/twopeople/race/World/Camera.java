package com.twopeople.race.World;

public class Camera {
	private int width, height;
	private float targetX, targetY;
	private float x, y;
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

    public void centerOn(Entity entity) {
       targetX = entity.x - width/2 - entity.w/2;
        targetY = entity.y - height/2 - entity.h/2;
    }

	public void shake(int shakes) {
		shakeAmplitude += shakes;
	}
}