package com.jp.daichi.ex4;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class CollidedObjects extends HashSet<AObject> {

    public static boolean contains(Collection<CollidedObjects> collection,AObject object) {
        for (CollidedObjects objects:collection) {
            if (objects.contains(object)) {
                return true;
            }
        }
        return false;
    }

    private boolean endLoop = false;

    public void setEndLoop(boolean endLoop) {
        this.endLoop = endLoop;
    }

    public boolean isEndLoop() {
        return endLoop;
    }
}
