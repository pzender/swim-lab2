package com.example.zender.zenderswim1;

/**
 * Created by Przemek on 2017-03-14.
 */

public interface ICountBMI {
    public float countBMI(float mass, float height);
    public boolean isValidHeight(float height);
    public boolean isValidMass (float mass);
}
