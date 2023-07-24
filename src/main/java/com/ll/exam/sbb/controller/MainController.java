package com.ll.exam.sbb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
    @RequestMapping("/sbb")
    // 아래 함수의 리턴값을 브라우저에 표시
    // 아래 함수의 리턴값을 문자열화 해서 브라우저 응답을 Body에 담는다.
    @ResponseBody
     public String index(){
        return "안녕하세요!!" ;
    }
    /*public void index(){
        System.out.println("첫 시작~!");
    }*/
}
