package com.deeep.spaceglad.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.deeep.spaceglad.components.*;

/**
 * Created by Andreas on 8/5/2015.
 */
public class AISystem extends EntitySystem  implements EntityListener{
    private ImmutableArray<Entity> entities;
    private Entity player;

    ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);

    @Override
    public void addedToEngine(Engine e){
        entities = e.getEntitiesFor(Family.all(ModelComponent.class, RotationComponent.class, PositionComponent.class, VelocityComponent.class, AIComponent.class, StatusComponent.class).get());
        e.addEntityListener(Family.one(PlayerComponent.class).get(),this);
    }

    public void update(float delta){
        for(Entity e: entities){
            StatusComponent sta =  e.getComponent(StatusComponent.class);
            //if(!sta.enabled) continue;
            RotationComponent rot =  e.getComponent(RotationComponent.class);
            VelocityComponent vel =  e.getComponent(VelocityComponent.class);
            ModelComponent mod =  e.getComponent(ModelComponent.class);
            AIComponent aic =  e.getComponent(AIComponent.class);

            PositionComponent playerPositionComponent = player.getComponent(PositionComponent.class);


            float dX = playerPositionComponent.position.x - pm.get(e).position.x;
            float dZ = playerPositionComponent.position.z - pm.get(e).position.z;

            rot.yaw = (float) (Math.atan2(dX, dZ));

            //mod.instance.transform.setFromEulerAngles((float) Math.toDegrees(rot.yaw), rot.pitch, rot.roll);

            if(aic.state != AIComponent.STATE.IDLE && !sta.frozen){
                float speedX =vel.velocity.x +  (float) Math.sin(rot.yaw) * 0.5f;
                speedX = (speedX < -10)? -10 : speedX;
                speedX = (speedX > 10)? 10 : speedX;
                float speedZ =vel.velocity.z +  (float) Math.cos(rot.yaw) * 0.5f;
                speedZ = (speedZ < -10)? -10 : speedZ;
                speedZ = (speedZ > 10)? 10 : speedZ;
                vel.velocity.x = speedX;
                vel.velocity.z = speedZ;
            }
        }
    }


    @Override
    public void entityAdded(Entity entity) {
        player = entity;
    }

    @Override
    public void entityRemoved(Entity entity) {

    }
}
