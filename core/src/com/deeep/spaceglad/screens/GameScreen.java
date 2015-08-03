package com.deeep.spaceglad.screens;

import com.badlogic.gdx.Screen;
import com.deeep.spaceglad.Core;
import com.deeep.spaceglad.GameWorld;
import com.deeep.spaceglad.UI.GameScreenUI;

/**
 * Created by scanevaro on 31/07/2015.
 */
public class GameScreen implements Screen {

    private Core game;
    private GameScreenUI gameScreenUI;
    private GameWorld gameWorld;

    public GameScreen(Core game) {
        this.game = game;
        gameScreenUI = new GameScreenUI();
        gameWorld = new GameWorld();
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        /** Updates */
        gameWorld.update(delta);
        gameScreenUI.update();

        /** Draw */
        gameWorld.render();
        gameScreenUI.render();
    }

    @Override
    public void resize(int width, int height) {
        gameScreenUI.resize(width, height);
        gameWorld.resize(width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }
}