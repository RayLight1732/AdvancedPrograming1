package com.jp.daichi.ex5shooting;

public interface GameObject {

    void tick(double deltaTime);

    double getX();

    // double getNewX();

    double getNewX();
    double getLastTickX();


    void setX(double x);

    void setX(double x,boolean teleported);

    double getY();


    double getNewY();
    double getLastTickY();

    void setY(double y);

    void setY(double y,boolean teleported);

    double getRotation();


    double getLastTickRotation();

    double getNewRotation();

    void setRotation(double rotation);

    boolean isVisible();

    void setVisible(boolean isVisible);

    /**
     * 1tick経過したか
     * @return 1tickでも経過していたらtrue
     */
    boolean lastTickExisted();

    long lastTickMs();

    double lastTickDelta();

}
