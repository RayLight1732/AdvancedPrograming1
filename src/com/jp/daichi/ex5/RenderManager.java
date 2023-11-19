package com.jp.daichi.ex5;

import com.jp.daichi.ex5.render.Render;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class RenderManager {

    private static final Map<Class<?  extends GameEntity>,RenderFactory<?>> factoryMap = new HashMap<>();
    private static final Map<Class<? extends GameEntity>,Render<?>> entityRenderMap = new HashMap<>();

    public static  <T extends GameEntity> void registerRender(Class<T> clazz,RenderFactory<T> factory) {
        factoryMap.put(clazz,factory);
    }

    public static <T extends GameEntity,U extends Render<T>> Render<GameEntity> getRender(Class<T> clazz) {
        Render<?> render = entityRenderMap.get(clazz);
        if (render == null) {
            render = getRenderFactory(clazz).createRender();
            entityRenderMap.put(clazz,render);
        }
        return (Render<GameEntity>) render;
    }


    private static <T extends GameEntity> RenderFactory<?> getRenderFactory(Class<T> clazz) {
        System.out.println(clazz.getName()+","+ Arrays.stream(clazz.getInterfaces()).map(Class::getName).toList());
        RenderFactory<?> factory = factoryMap.get(clazz);
        if (factory != null) {
            return factory;
        } else if (!Arrays.asList(clazz.getInterfaces()).contains(GameEntity.class) ) {
            return getRenderFactory((Class<? extends GameEntity>) clazz.getSuperclass());
        } else {
            throw new IllegalArgumentException("Render factory not found.");
        }
    }

    public interface RenderFactory<T extends GameEntity> {
        Render<T> createRender();
    }

}
