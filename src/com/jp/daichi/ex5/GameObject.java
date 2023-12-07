package com.jp.daichi.ex5;

/**
 * ゲーム内のオブジェクト
 */
public interface GameObject {

    /**
     * ティック処理を行う
     * @param deltaTime 前回からの経過時間
     */
    void tick(double deltaTime);

    /**
     * x座標を取得
     * @return x座標
     */
    double getX();

    /**
     * 新しいx座標としてマークされた値を返す
     * @return 新しいx座標
     */
    double getNewX();

    /**
     * tick処理を行う前のx座標を返す
     * @return tick処理を行う前のx座標
     */
    double getLastTickX();

    /**
     * x座標を設定する
     * @param x x座標
     */
    void setX(double x);

    /**
     * x座標を設定する
     * @param x x座標
     * @param teleported テレポートされたらtrue
     */
    void setX(double x,boolean teleported);

    /**
     * y座標を返す
     * @return y座標
     */
    double getY();

    /**
     * 新しいy座標としてマークされた値を返す
     * @return 新しいy座標
     */
    double getNewY();

    /**
     * tick処理を行う前のy座標を返す
     * @return tick処理を行う前のy座標
     */
    double getLastTickY();

    /**
     * y座標を指定
     * @param y y座標
     */
    void setY(double y);

    /**
     * y座標を指定
     * @param y y座標
     * @param teleported テレポートしたか
     */
    void setY(double y,boolean teleported);

    /**
     * 回転角をラジアンで取得
     * @return 回転角
     */
    double getRotation();

    /**
     * tick処理を行う前の回転角を返す
     * @return 回転角
     */
    double getLastTickRotation();

    /**
     * 新しい回転角としてマークされた値を返す
     * @return 新しい回転角
     */
    double getNewRotation();

    /**
     * 回転角を設定
     * @param rotation 回転角
     */
    void setRotation(double rotation);

    /**
     * 見えているかどうか
     * @return 見えるならtrue
     */
    boolean isVisible();

    /**
     * 可視性を設定
     * @param isVisible 可視性
     */
    void setVisible(boolean isVisible);

    /**
     * 1tick経過したか
     * @return 1tickでも経過していたらtrue
     */
    boolean lastTickExisted();

    /**
     * 前回のtickが行われた時のSystem.getTimeMsの値を返す
     * @return 前回のtickが行われた時のSystem.getTimeMsの値
     */
    long lastTickMs();

    /**
     * 前回のtickが行われた時に渡されたdeltaTimeを返す
     * @return 前回のtickが行われた時に渡されたdeltaTime
     */
    double lastTickDelta();

}
