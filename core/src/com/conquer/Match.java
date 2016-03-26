package com.conquer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.conquer.entities.Cell;

import java.util.ArrayList;

public class Match {
    public int uniqueID, selected;
    private ArrayList<Cell> cells;

    public Match() {
        cells = new ArrayList<Cell>();
        cells.add(createCell(100, 100, "GREEN"));

        uniqueID = 0;
        selected = -1;
    }

    public void draw(ShapeRenderer renderer) {
        renderer.setColor(Color.BLUE);
        for (Cell cell : cells) {
            renderer.circle(cell.x, cell.y, cell.size);
        }
    }

    public void update() {
        for (Cell cell : cells) {
            if (Gdx.input.justTouched()) {
                cell.setTargetLoc(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
            }

            if (cell.hasTarget())
                cell.moveToTargetLoc();
        }
    }

    public Cell createCell(int x, int y, String faction) {
        Cell cell = new Cell(uniqueID, x, y, faction);
        uniqueID++;

        return cell;
    }

}