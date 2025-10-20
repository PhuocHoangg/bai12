package com.example.demo.AOP;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SoccerCout {
    private static int count=0;
    private static int countList=0;
@After("execution(* com.example.demo.service.CauThuService.addCauThu(..))")
    public void countAddCauThu(){
        count++;
        System.out.println("=========Tong so lan them cau thu"+ count +"============");
    }
    @After("execution(* com.example.demo.service.CauThuService.findAllCauThu(..))")
    public void countList(){
        countList++;
        System.out.println("===========Tong so lan xem list"+ countList +"============");
    }
}
