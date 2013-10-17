package com.aimco.domain;

public class AimcoDomain implements java.io.Serializable {

    private int aimcoVar = 20;

    public String toString() {
        return "aimcoVar: "+aimcoVar;
    }

    public int getAimcoVar() {
        return aimcoVar;
    }
    public void setAimcoVar(int x) {
        aimcoVar = x;
    }
}
