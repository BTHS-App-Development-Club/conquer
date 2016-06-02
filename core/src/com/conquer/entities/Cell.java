package com.conquer.entities;

public class Cell {
    public int id, size;
    public float x, y, targetX, targetY, speed;
    public String faction;
    public boolean remove;
    public int immune, period;

    public Cell(int id, float x, float y, String faction) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.faction = faction;

        this.targetX = -1;
        this.targetY = -1;

        this.size = 100;
        this.speed = 5;
    }

    public void setImmuneTo(int immune, int period) {
        this.immune = immune;
        this.period = period;
    }

    public boolean isImmuneTo(int cell) {
        return this.period > 0 && this.immune == cell;
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

    public void collideWith(Cell cell) {
        if (this.remove || cell.remove) {
            return;
        }

        if (this.isImmuneTo(cell.id) || cell.isImmuneTo(this.id)) {
            return;
        }

        if (cell.id == this.id) {
            return;
        }

        float distance = (float) Math.abs(Math.sqrt(Math.pow(cell.y - this.y, 2) + Math.pow(cell.x - this.x, 2)));
        if (distance > this.size / 2) {
            return;
        }

        if (cell.faction.equals(this.faction)) {
            if (cell.size > this.size) {
                cell.changeSize(this.size);
                this.remove();
            } else {
                this.changeSize(cell.size);
                cell.remove();
            }
        } else {
            int diff = Math.abs(cell.size - this.size);
            if (diff < 5) {
                return;
            }

            if (cell.size > this.size) {
                cell.changeSize(-this.size);
                this.remove();
            } else if (cell.size < this.size) {
                this.changeSize(-cell.size);
                cell.remove();
            }
        }
    }

    public void moveToTargetLoc() {
        float distance = (float) Math.sqrt(Math.pow(this.targetY - this.y, 2) + Math.pow(this.targetX - this.x, 2));
        distance = distance < 0 ? 0 : distance;
        if (distance == 0) {
            setTargetLoc(-1, -1);
            return;
        }

        float speed = distance <= this.speed ? distance : this.speed;
        if (period > 0) {
            period -= speed;
        }

        float deltaX = this.targetX - this.x;
        float deltaY = this.targetY - this.y;
        double angle = deltaX == 0 ? 0 : Math.atan(deltaY / deltaX);

        float moveX = (float) Math.abs(speed * Math.cos(angle));
        float moveY = (float) Math.abs(speed * Math.sin(angle));

        moveX = this.targetX < this.x ? -moveX : moveX;
        moveY = this.targetY < this.y ? -moveY : moveY;

        setLoc(this.x + moveX, this.y + moveY);
    }

    public Cell setSize(int size) {
        this.size = size;
        return this;
    }

    public void changeSize(int offset) {
        size += offset;
    }

    public void remove() {
        this.remove = true;
    }

}