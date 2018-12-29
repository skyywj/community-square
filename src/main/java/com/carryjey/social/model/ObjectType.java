package com.carryjey.social.model;

/**
 * @author CarryJey
 * @since 2018/12/27
 */
public enum ObjectType {
    AVATAR("avatar");

    private final String objType;

    ObjectType(String objType) {
        this.objType = objType;
    }

    @Override
    public String toString() {
        return objType;
    }
}
