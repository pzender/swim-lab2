package com.example.zender.zenderswim1;

/**
 * Created by pzend on 27.03.2017.
 */

public class CountBMIForImp implements ICountBMI {
    static final float MIN_HEIGHT = 2.0f;
    static final float MAX_HEIGHT = 15.0f;
    static final float MIN_MASS = 20;
    static final float MAX_MASS = 500;
    public float countBMI(float mass, float height){
        if (isValidHeight(height) && isValidMass(mass)){
            return (mass*0.4536f)/(height*height*0.0929f);
        }
        else throw new IllegalArgumentException("Incorrect input!");
    }
    public boolean isValidHeight(float height){
        return (height < MAX_HEIGHT && height > MIN_HEIGHT);
    }
    public boolean isValidMass (float mass){
        return (mass < MAX_MASS && mass > MIN_MASS);
    }
}
