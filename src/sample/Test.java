package sample;

import java.math.BigDecimal;

public class Test {
    public static void main(String[] args){
        BigDecimal d = BigDecimal.valueOf(012d);
        BigDecimal e = BigDecimal.valueOf(0.7d);
        BigDecimal i = new BigDecimal("14");
        System.out.println(d.add(e));
    }
}
