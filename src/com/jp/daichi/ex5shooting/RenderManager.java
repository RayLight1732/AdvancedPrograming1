package com.jp.daichi.ex5shooting;

import com.jp.daichi.ex5shooting.render.Render;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RenderManager {

    private static final Map<Class<?  extends GameObject>,RenderFactory<?>> factoryMap = new HashMap<>();
    private static final Map<Class<? extends GameObject>,Render<?>> entityRenderMap = new HashMap<>();

    public static  <T extends GameObject> void registerRender(Class<T> clazz,RenderFactory<T> factory) {
        factoryMap.put(clazz,factory);
        factory.createRender().loadImages();
    }

    public static <T extends GameObject> Render<GameObject> getRender(Class<T> clazz) {
        Render<?> render = entityRenderMap.get(clazz);
        if (render == null) {
            render = getRenderFactory(clazz).createRender();
            entityRenderMap.put(clazz,render);
        }
        return (Render<GameObject>) render;
    }


    private static <T extends GameObject> RenderFactory<?> getRenderFactory(Class<T> clazz) {
        System.out.println(clazz.getName()+","+ Arrays.stream(clazz.getInterfaces()).map(Class::getName).toList());
        RenderFactory<?> factory = factoryMap.get(clazz);
        if (factory != null) {
            return factory;
        } else if (!Arrays.asList(clazz.getInterfaces()).contains(GameObject.class) ) {
            return getRenderFactory((Class<? extends GameObject>) clazz.getSuperclass());
        } else {
            throw new IllegalArgumentException("Render factory not found.");
        }
    }

    public interface RenderFactory<T extends GameObject> {
        Render<T> createRender();
    }

}
