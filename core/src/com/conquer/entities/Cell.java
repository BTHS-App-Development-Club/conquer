package com.conquer.entities;

public class Cell {
    public int id, size;
    public float x, y, targetX, targetY, speed;
    public String faction;

    public Cell(int id, float x, float y, String faction) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.faction = faction;

        this.targetX = -1;
        this.targetY = -1;

        this.size = 10;
        this.speed = 3;
    }

    public void setLoc(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setTargetLoc(float x, float y) {
        targetX = x;
        targetY = y;
    }

    public boolean hasTarget() {
        return this.targetX != -1 && this.targetY != -1;
    }

    public void moveToTargetLoc() {
        float distance = (float) Math.sqrt(Math.pow(this.targetY - this.y, 2) + Math.pow(this.targetX - this.x, 2));
        distance = distance < 0 ? 0 : distance;
        if (distance == 0) {
            setTargetLoc(-1, -1);
            return;
        }

        float speed = distance <= this.speed ? distance : this.speed;

        float deltaX = this.targetX - this.x;
        float deltaY = this.targetY - this.y;
        double angle = deltaX == 0 ? 0 : Math.atan(deltaY / deltaX);

        float moveX = (float) Math.abs(speed * Math.cos(angle));
        float moveY = (float) Math.abs(speed * Math.sin(angle));

        moveX = this.targetX < this.x ? -moveX : moveX;
        moveY = this.targetY < this.y ? -moveY : moveY;

        setLoc(this.x + moveX, this.y + moveY);
    }

}