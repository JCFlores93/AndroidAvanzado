package pe.cibertec.unittestdemo;

/**
 * Created by Android on 03/06/2017.
 */

public class FizzBuzz {

    public static final String FIZZ_RESPONSE = "FIZZ";

    public static final String BUZZ_RESPONSE = "BUZZ";



    public String calculate(int i) {
//        if (isFizz(i) && isBuzz(i))return FIZZ_RESPONSE + BUZZ_RESPONSE;
//        if (isFizz(i))return FIZZ_RESPONSE;
//        if (isBuzz(i))return BUZZ_RESPONSE;
        String response = "";
        if (isFizz(i)){
            response += FIZZ_RESPONSE;
        }
        if (isBuzz(i)){
            response += BUZZ_RESPONSE;
        }
        if (response.isEmpty()){
            response = String.valueOf(i);
        }
        return response;
    }

    public boolean isFizz(int i){
        return i % 3 == 0 ;
    }

    public boolean isBuzz(int i){
        return i % 5 == 0 ;
    }
}
