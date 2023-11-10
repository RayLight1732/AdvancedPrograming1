package com.jp.daichi.ex4;

import java.util.*;

public class Pair<T>{

    public static <T> Pair<T> getPair(Collection<Pair<T>> collection,T obj) {
        for (Pair<T> pair:collection) {
            if (pair.first == obj || pair.second == obj) {
                return pair;
            }
        }
        return null;
    }

    public static <T> List<Pair<T>> getAllPairs(Collection<Pair<T>> collection,T obj) {
        List<Pair<T>> result = new ArrayList<>();
        for (Pair<T> pair:collection) {
            if (pair.first == obj || pair.second == obj) {
                result.add(pair);
            }
        }
        return result;
    }

    public static <T> boolean contains(Collection<Pair<T>> collection,T obj) {
        return getPair(collection,obj) != null;
    }


    private final T first;
    private final T second;

    public Pair(T first,T second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Pair<?>) {
            Pair<?> casted = (Pair<?>) obj;
            return (casted.first == this.first && casted.second == this.second) || (casted.first == this.second && casted.second == this.first);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }
}
