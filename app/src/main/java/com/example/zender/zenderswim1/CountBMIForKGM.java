package com.example.zender.zenderswim1;

/**
 * Created by Przemek on 2017-03-14.
 */

public class CountBMIForKGM implements ICountBMI {
    static final float MIN_HEIGHT = 0.5f;
    static final float MAX_HEIGHT = 2.5f;
    static final float MIN_MASS = 10;
    static final float MAX_MASS = 250;
    public float countBMI(float mass, float height){
        if (isValidHeight(height) && isValidMass(mass)){
            return mass/(height*height);
        }
        else throw new IllegalArgumentException(isValidHeight(height) ? "Incorrect mass!" : "Incorrect height!");

    }
    public boolean isValidHeight(float height){
        return (height < MAX_HEIGHT && height > MIN_HEIGHT);
    }
    public boolean isValidMass (float mass){
        return (mass < MAX_MASS && mass > MIN_MASS);
    }
}
