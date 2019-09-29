package com.jql.aviator;


import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorDouble;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.googlecode.aviator.runtime.type.AviatorString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class AviatorBaseDemo {

    @Test
    public void test1(){
        Long result = (Long)AviatorEvaluator.execute("1+2*3");
        log.info("result={}",result);


        Long result1 = (Long)AviatorEvaluator.exec(" a + b * c",1,2,3);
        log.info("result={}",result1);


        int a = 1;
        int b = 2;
        int c = 3;
        Long result2 = (Long)AviatorEvaluator.exec(" a + b * c",b,c,a);
        log.info("result={}",result2);


        Map<String,Object> map = new HashMap<>();
        map.put("a",a);
        map.put("b",b);
        map.put("c",c);
        Long result3 = (Long)AviatorEvaluator.execute("a + b * c",map);
        log.info("result={}",result3);




    }

    @Test
    public void test2(){

        Long length = (Long)AviatorEvaluator.execute(" string.length('nooneder') ");
        log.info("length={}",length);


        String subString = (String)AviatorEvaluator.execute(" string.substring('nooneder',4) ");
        log.info("subString={}",subString);

    }

    @Test
    public void test3(){

        AviatorEvaluator.addFunction(new AddFunction());

        Double result = (Double)AviatorEvaluator.execute("add(2,8)");

        log.info("result={}",result);

        AviatorEvaluator.addFunction(new Like());

        String message = (String)AviatorEvaluator.execute("like('who')");

        log.info("message={}",message);

    }


    class AddFunction extends AbstractFunction {
        @Override
        public AviatorObject call(Map<String, Object> env, AviatorObject arg1, AviatorObject arg2) {
            Number left = FunctionUtils.getNumberValue(arg1, env);
            Number right = FunctionUtils.getNumberValue(arg2, env);
            return new AviatorDouble(left.doubleValue() + right.doubleValue());
        }
        public String getName() {
            return "add";
        }
    }

    class Like extends AbstractFunction {
        @Override
        public AviatorObject call(Map<String, Object> env, AviatorObject name) {
            String stringName = FunctionUtils.getStringValue(name, env);
            return new AviatorString("like "+stringName);
        }
        public String getName() {
            return "like";
        }
    }

    @Test
    public void testStringUtils(){
        boolean contains = StringUtils.contains("code=='m=AAAAAAA|d=Y'", "||");
        System.out.println(contains);
    }

}
