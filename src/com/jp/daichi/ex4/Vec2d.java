package com.jp.daichi.ex4;

import java.util.ArrayList;

public class Vec2d implements Cloneable {

    public double x;
    public double y;

    public Vec2d(double x,double y) {
        this.x = x;
        this.y = y;
    }

    public Vec2d(Vec2d vec2d) {
        this(vec2d.x, vec2d.y);
    }

    public Vec2d() {
        this(0,0);
    }

    public Vec2d multiple(double k) {
        x *= k;
        y *= k;
        return this;
    }

    public boolean normalize() {
        double length = getLength();
        if (length != 0) {
            multiple(1/length);
            return true;
        } else {
            return false;
        }
    }

    public double getLength() {
        return Math.sqrt(x*x+y*y);
    }

    public double getSqLength() {
        return x*x+y*y;
    }

    public Vec2d add(Vec2d vec2d) {
        x += vec2d.x;
        y += vec2d.y;
        return this;
    }

    public Vec2d subtract(Vec2d vec2d) {
        x -= vec2d.x;
        y -= vec2d.y;
        return this;
    }

    public double dot(Vec2d vec2d) {
        return this.x*vec2d.x + this.y*vec2d.y;
    }
    @Override
    public String toString() {
        return "["+x+","+y+"]";
    }

    public Vec2d copy() {
        try {
            return (Vec2d) this.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
