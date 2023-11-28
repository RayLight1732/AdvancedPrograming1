package com.jp.daichi.ex5shooting.render;

import com.jp.daichi.ex5shooting.GameEntity;
import com.jp.daichi.ex5shooting.OldRenderEntity;

import java.awt.*;

public class OldDefaultRender<T extends GameEntity  & OldRenderEntity> extends LinearCompletionRender<T> {

    @Override
    protected void doRendering(Graphics2D g, T entity, double x, double y, double rotation, double step) {
        g.setColor(entity.getRotationalObject().getColor());
        g.fill(entity.getRotationalObject().getArea(x,y,rotation));
    }

}
