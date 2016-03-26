package com.conquer;

import com.badlogic.gdx.Game;
import com.conquer.screens.MatchScreen;

public class Conquer extends Game {

    Match match;

    @Override
    public void create() {
        match = new Match();

        setScreen(new MatchScreen(match));
    }

}
