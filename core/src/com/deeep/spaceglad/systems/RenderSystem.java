package com.deeep.spaceglad.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.deeep.spaceglad.components.*;

/**
 * Created by Andreas on 8/4/2015.
 */
public class RenderSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;
    private ModelBatch batch;
    private Environment environment;

    public RenderSystem(ModelBatch batch, Environment environment) {
        this.batch = batch;
        this.environment = environment;
    }

    /// Event called when an entity is added to the engine
    public void addedToEngine(Engine e) {
        // Grabs all entities with desired components
        entities = e.getEntitiesFor(Family.all(RotationComponent.class, PositionComponent.class,
                RenderableComponent.class, ModelComponent.class, StatusComponent.class).get());
    }

    public void update(float delta) {
        for (Entity e: entities) {
            StatusComponent sta = e.getComponent(StatusComponent.class);
            //if(!sta.enabled || !sta.visible)
            //    continue;
            //}
            PositionComponent pos = e.getComponent(PositionComponent.class);
            RotationComponent rot = e.getComponent(RotationComponent.class);
            ModelComponent mod = e.getComponent(ModelComponent.class);

            mod.instance.transform.setFromEulerAngles(rot.yaw, rot.pitch, rot.roll);
            mod.instance.transform.setTranslation(pos.position.x, pos.position.y, pos.position.z);
            batch.render(mod.instance, environment);
        }
    }
}
