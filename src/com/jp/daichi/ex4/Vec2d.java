package com.jp.daichi.ex4;

public class Vec2d {

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

    public void multiple(double k) {
        x *= k;
        y *= k;
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

    @Override
    public String toString() {
        return "["+x+","+y+"]";
    }
}
