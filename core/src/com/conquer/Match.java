package com.conquer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.conquer.entities.Cell;

import java.util.ArrayList;

public class Match {
    public Selector selector;
    public int uniqueID;
    private ArrayList<Cell> cells;

    public Match() {
        uniqueID = 0;

        cells = new ArrayList<Cell>();
        cells.add(createCell(200, 200, "GREEN"));

        selector = new Selector();
    }

    public void draw(ShapeRenderer renderer) {
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        for (Cell cell : cells) {
            renderer.setColor(Color.GREEN);
            renderer.circle(cell.x, cell.y, cell.size);
        }
        renderer.end();

        Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        if (selector.selected != -1) {
            renderer.setColor(1, 1, 1, 0.5F);
            Cell selected = getCell(selector.getSelected());
            renderer.circle(selected.x, selected.y, selector.getCharge());
        }
        renderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    public void update() {
        selector.handle();

        for (Cell cell : cells) {

            if (cell.hasTarget())
                cell.moveToTargetLoc();
        }
    }

    public Cell getCell(int id) {
        for (Cell cell : cells) {
            if (cell.id == id) {
                return cell;
            }
        }

        return null;
    }

    public Cell getCell(int x, int y) {
        for (Cell cell : cells) {
            double distance = Math.sqrt(Math.pow(y - cell.y, 2) + Math.pow(x - cell.x, 2));
            if (distance <= cell.size) {
                return cell;
            }
        }

        return null;
    }

    public Cell createCell(float x, float y, String faction) {
        uniqueID++;
        Cell cell = new Cell(uniqueID, x, y, faction);

        return cell;
    }

    public void addCell(Cell cell) {
        cells.add(cell);
    }

    class Selector {
        private int selected, charge;
        private boolean charging;

        public Selector() {
            selected = -1;
            charge = 0;
        }

        public void handle() {
            if (Gdx.input.isTouched()) {
                if (selected == -1) {
                    Cell cell = getCell(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
                    selected = cell != null ? cell.id : -1;
                    charging = true;
                } else if (selected != -1 && charging) {
                    Cell cell = getCell(selected);
                    charge = charge + 1 > cell.size ? cell.size : charge + 1;
                } else if (selected != -1 && !charging) {
                    Cell cell = getCell(selected);
                    cell.changeSize(-charge);

                    Cell projectile = createCell(cell.x, cell.y, cell.faction);
                    projectile.setSize(charge);
                    projectile.setTargetLoc(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
                    addCell(projectile);

                    selected = -1;
                    charge = 0;
                }
            } else {
                System.out.println(charge);
                charging = false;
            }
        }

        public int getSelected() {
            return selected;
        }

        public int getCharge() {
            return charge;
        }
    }
}