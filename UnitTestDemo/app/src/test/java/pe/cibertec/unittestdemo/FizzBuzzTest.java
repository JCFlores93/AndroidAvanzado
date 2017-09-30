package pe.cibertec.unittestdemo;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Android on 03/06/2017.
 */
public class FizzBuzzTest {

    @Test
    public void testShouldReturnFiizwhenValueIsThree(){

        FizzBuzz fizzBuzz = new FizzBuzz();
        String value = fizzBuzz.calculate(3);

        assertEquals("FIZZ",value);

    }

    @Test
    public void testShouldReturnFiizwhenValueIsFive(){

        FizzBuzz fizzBuzz = new FizzBuzz();
        String value = fizzBuzz.calculate(5);

        assertEquals("BUZZ",value);

    }

    @Test
    public void testShouldReturnFiizwhenValueIsMutiplyThree(){

        FizzBuzz fizzBuzz = new FizzBuzz();
        String value = fizzBuzz.calculate(33);

        assertEquals("FIZZ",value);

    }

    @Test
    public void testShouldReturnFiizwhenValueIsMutiplyFive(){

        FizzBuzz fizzBuzz = new FizzBuzz();
        String value = fizzBuzz.calculate(35);

        assertEquals("BUZZ",value);

    }

    @Test
    public void testShouldReturnFiizBuzzForMultiplyThreeAndFive(){

        FizzBuzz fizzBuzz = new FizzBuzz();
        String value = fizzBuzz.calculate(15);

        assertEquals("FIZZBUZZ",value);

    }

}