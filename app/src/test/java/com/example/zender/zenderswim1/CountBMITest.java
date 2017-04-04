package com.example.zender.zenderswim1;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Przemek on 2017-03-14.
 */

public class CountBMITest {
    @Test
    public void massUnderZeroIsIncorrect(){
        //GIVEN
        float testMass = -1.0f;
        //WHEN
        ICountBMI countBMI = new CountBMIForKGM();
        //THEN
        assertFalse(countBMI.isValidMass(testMass));
    }
    @Test
    public void massEqualToZeroIsIncorrect(){
        //GIVEN
        float testMass = 0.0f;
        //WHEN
        ICountBMI countBMI = new CountBMIForKGM();
        //THEN
        assertFalse(countBMI.isValidMass(testMass));
    }
    @Test
    public void massOverLimitIsIncorrect(){
        //GIVEN
        float testMass = 300.0f;
        //WHEN
        ICountBMI countBMI = new CountBMIForKGM();
        //THEN
        assertFalse(countBMI.isValidMass(testMass));
    }
    @Test
    public void massUnderLimitIsIncorrect(){
        //GIVEN
        float testMass = 3.0f;
        //WHEN
        ICountBMI countBMI = new CountBMIForKGM();
        //THEN
        assertFalse(countBMI.isValidMass(testMass));
    }
    @Test
    public void massWithinLimitIsCorrect(){
        //GIVEN
        float testMass = 90.0f;
        //WHEN
        ICountBMI countBMI = new CountBMIForKGM();
        //THEN
        assertTrue(countBMI.isValidMass(testMass));
    }
    @Test
    public void heightUnderZeroIsIncorrect(){
        //GIVEN
        float testHeight = -1.0f;
        //WHEN
        ICountBMI countBMI = new CountBMIForKGM();
        //THEN
        assertFalse(countBMI.isValidHeight(testHeight));
    }
    @Test
    public void heightEqualZeroIsIncorrect(){
        //GIVEN
        float testHeight = 0.0f;
        //WHEN
        ICountBMI countBMI = new CountBMIForKGM();
        //THEN
        assertFalse(countBMI.isValidHeight(testHeight));
    }
    @Test
    public void heightUnderLimitIsIncorrect(){
        //GIVEN
        float testHeight = 0.2f;
        //WHEN
        ICountBMI countBMI = new CountBMIForKGM();
        //THEN
        assertFalse(countBMI.isValidHeight(testHeight));
    }
    @Test
    public void heightOverLimitIsIncorrect(){
        //GIVEN
        float testHeight = 3.0f;
        //WHEN
        ICountBMI countBMI = new CountBMIForKGM();
        //THEN
        assertFalse(countBMI.isValidHeight(testHeight));
    }
    @Test
    public void heightWithinLimitIsCorrect(){
        //GIVEN
        float testHeight = 1.8f;
        //WHEN
        ICountBMI countBMI = new CountBMIForKGM();
        //THEN
        assertTrue(countBMI.isValidHeight(testHeight));
    }
    @Test(expected = IllegalArgumentException.class)
    public void wrongHeightThrowsException(){
        //GIVEN
        float testHeight = 3.0f;
        float testMass = 90.0f;
        //WHEN
        ICountBMI countBMI = new CountBMIForKGM();
        //THEN
        countBMI.countBMI(testMass,testHeight);
    }
    @Test(expected = IllegalArgumentException.class)
    public void wrongMassThrowsException(){
        //GIVEN
        float testHeight = 1.8f;
        float testMass = 400.0f;
        //WHEN
        ICountBMI countBMI = new CountBMIForKGM();
        //THEN
        countBMI.countBMI(testMass,testHeight);
    }
    @Test
    public void calculatedBmiIsCorrect(){
        //GIVEN
        float testHeight = 2.0f;
        float testMass = 90f;
        //WHEN
        ICountBMI countBMI = new CountBMIForKGM();
        //THEN
        assertEquals(22.5f,countBMI.countBMI(testMass, testHeight),0.005);
    }
}
