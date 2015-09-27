package com.deeep.spaceglad.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.deeep.spaceglad.UI.GameUI;
import com.deeep.spaceglad.components.*;

/**
 * Created by Elmar on 8-8-2015.
 */
public class PlayerSystem extends EntitySystem implements EntityListener {
    private Entity player;
    private PlayerComponent playerComponent;
    private CharacterComponent characterComponent;
    private GameUI gameUI;
   // private final Camera camera;
   // private final Vector3 tempVector = new Vector3();
    private Engine engine;
    //private ComponentMapper<PositionComponent> positionComponentMapper = ComponentMapper.getFor(PositionComponent.class);
    //private ComponentMapper<VelocityComponent> velocityComponentMapper = ComponentMapper.getFor(VelocityComponent.class);
    //private ComponentMapper<CollisionComponent> collisionComponentComponentMapper = ComponentMapper.getFor(CollisionComponent.class);
    Vector3 characterDirection = new Vector3();
    Vector3 walkDirection = new Vector3();

    public PlayerSystem(){}

    public PlayerSystem(Camera camera, GameUI gameUI, Engine engine) {
        //this.camera = camera;
        this.engine = engine;
        this.gameUI = gameUI;
    }

    @Override
    public void addedToEngine(Engine engine) {
        engine.addEntityListener(Family.all(PlayerComponent.class).get(), this);
    }

    @Override
    public void update(float delta) {
        if (player == null) return;
        updateMovement(delta);
        //if (Gdx.input.justTouched()) fire();
        //updateStatus();
        //checkGameOver();
    }

    private void updateMovement(float delta) {
        /*
        float deltaX = -Gdx.input.getDeltaX() * 0.5f;
        float deltaY = -Gdx.input.getDeltaY() * 0.5f;
        camera.direction.rotate(camera.up, deltaX);
        tempVector.set(camera.direction).crs(camera.up).nor();
        camera.direction.rotate(tempVector, deltaY);
        */

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            characterComponent.ghostObject.setWorldTransform(player.getComponent(ModelComponent.class).transform.rotate(0, 1, 0, 5f));
            characterComponent.ghostObject.setWorldTransform(player.getComponent(ModelComponent.class).transform);

        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            characterComponent.ghostObject.setWorldTransform(player.getComponent(ModelComponent.class).transform.rotate(0, 1, 0, -5f));
            characterComponent.ghostObject.setWorldTransform(player.getComponent(ModelComponent.class).transform);
        }
        /** Fetch which direction the character is facing now */
        characterComponent.characterDirection.set(-1, 0, 0).rot(player.getComponent(ModelComponent.class).transform).nor();
        /** Set the walking direction accordingly (either forward or backward) */
        characterComponent.walkDirection.set(0, 0, 0);
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            characterComponent.walkDirection.add(characterComponent.characterDirection);
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            characterComponent.walkDirection.add(-characterComponent.characterDirection.x, -characterComponent.characterDirection.y, -characterComponent.characterDirection.z);

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            characterComponent.characterController.setJumpSpeed(15);
            characterComponent.characterController.jump();  //.body).applyCentralImpulse(new Vector3(0,5,0));
        }

        characterComponent.walkDirection.scl(4f * Gdx.graphics.getDeltaTime());
        characterComponent.characterController.setWalkDirection(characterComponent.walkDirection);
        characterComponent.ghostObject.getWorldTransform(player.getComponent(ModelComponent.class).transform);   //TODO export this
        player.getComponent(ModelComponent.class).instance.transform = player.getComponent(ModelComponent.class).transform;

       // camera.update(true);
    }

    private void updateStatus() {
        gameUI.healthWidget.setValue(playerComponent.health);
    }


    private void fire() {
        /*
        engine.addEntity(
                EntityFactory.createBullet(
                        positionComponentMapper.get(player).position.cpy(),
                        camera.direction.cpy().scl(150)
                )
        );*/
    }

    private void checkGameOver() {
        /*
        if (playerComponent.health <= 0 && !Settings.Paused) {
            Settings.Paused = true;
            gameUI.gameOverWidget.gameOver();
        }*/
    }

    @Override
    public void entityAdded(Entity entity) {
        player = entity;
        playerComponent = entity.getComponent(PlayerComponent.class);
        characterComponent = entity.getComponent(CharacterComponent.class);
        //gameUI.healthWidget.setValue(playerComponent.health);
    }

    @Override
    public void entityRemoved(Entity entity) {
    }
}