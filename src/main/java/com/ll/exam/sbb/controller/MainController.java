package com.ll.exam.sbb.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class MainController {

    public int increaseNo = -1  ;


    @RequestMapping("/sbb")
    // 아래 함수의 리턴값을 브라우저에 표시
    // 아래 함수의 리턴값을 문자열화 해서 브라우저 응답을 Body에 담는다.
    @ResponseBody
     public String index(){
        System.out.println("얍얍 되나 ?");
        return "안녕하세요!! " ;
    }

    @GetMapping("/page1")
    @ResponseBody
    public String showGet() {
        return """
           <form method="POST" action="/page2" />
              <input type="number" name="age" placeholder="나이 입력" />
              <input type="submit" value="page2로 POST 방식으로 이동" />
           </form>
           """;
    }


    @PostMapping("/page2")
    @ResponseBody
    public String showPage2Post(@RequestParam(defaultValue = "0") int age) {
        return """
           <h1>입력된 나이 : %d</h1>
           <h1>POST 방식 사용 </h1>
           """.formatted(age);
    }

    @GetMapping("/page2")
    @ResponseBody
    public String showPost(@RequestParam(defaultValue = "0") int age) {
        return """
           <h1>입력된 나이 : %d</h1>
           <h1> GET 방식 사용 </h1>           
           """.formatted(age);
    }



    @GetMapping("/plus")
    @ResponseBody
    //http://localhost:8080/plus?a=1&b=5
    public String showPlus(int a, int b) {
        return """
           <h1> a+b : %d</h1>
           <h1>GetMapping : 주소창에 입력 받은 Int 값을 sum하기  </h1>        
           """.formatted(a+ b) ;
    }
    @GetMapping("/plusJsp")
    @ResponseBody
    //http://localhost:8080/plusJsp?a=1&b=2
    public void showPlusJsp(HttpServletRequest req, HttpServletResponse resp ) throws IOException {
        System.out.println("14강 서블릿 방식으로 요청&응답하기  ::::: "  ) ;
        int a= Integer.parseInt(req.getParameter("a")) ;
        int b= Integer.parseInt(req.getParameter("b")) ;
        resp.getWriter().append(a+b+ "") ;
    }

    @GetMapping("/minus")
    @ResponseBody
    public String showMinus(int a, int b) {
        if (a > b)
            //http://localhost:8080/minus?a=4&b=1
        return """
           <h1> a+b : %d</h1>
           <h1>GetMapping : 주소창에 입력 받은 Int 값을 minus하기   </h1>        
           """.formatted(a - b) ;
        else
            //http://localhost:8080/minus?a=5&b=1
            return """
           <h1> a+b : -%d</h1>
           <h1>GetMapping : 주소창에 입력 받은 Int 값을 minus하기  </h1>        
           """.formatted(b - a) ;
    }

    @GetMapping("/increase")
    @ResponseBody
    public int showIncrease() {
        System.out.println("호출 시점의 increaseNo 값  ::::: " + increaseNo ) ;
        increaseNo++ ;
        System.out.println(" increaseNo++의 값  :::: "+ increaseNo ) ;
        return increaseNo ;
        // 크롬에스는 새로고침하면 increaseNo이 증가하면서 찍히는데 엣지는 안됨.
    }

    @GetMapping("/gugudan")
    @ResponseBody
    public String showgugudan(int dan, int limit ) {
        System.out.println("13강 구구단 응답, 스트림 방식으로 구현  ::::: "  ) ;
        //http://localhost:8080/gugudan?dan=5&limit=5
        String rs = "" ;
        for(int i = 1 ; i<= limit ; i++){
            rs += "%d * %d = %d<br>\n".formatted(dan,i,dan * i ) ;
        }
        return rs;
    }

    @GetMapping("/gugudanInteger")
    @ResponseBody
    public String showgugudanInteger(Integer dan, Integer limit ) {
        System.out.println("구구단을 스트림 방식으로 구현해보자 ! ::::: "  ) ;
        if( dan == null) {
            dan = 9;
            http://localhost:8080/gugudanInteger?dan=&limit=10
            System.out.println("dan 값이 null 이라서 초기값으로 9를 줬음 ::::: "  ) ;
        }
        if( limit == null) {
            limit = 9;
            //http://localhost:8080/gugudanInteger?dan=5&limit=
            System.out.println("limit 값이 null 이라서 초기값으로 9를 줬음 ::::: "  ) ;
        }
        Integer finalDan = dan ;
        //http://localhost:8080/gugudanInteger?dan=5&limit=10
        return IntStream.rangeClosed(1,limit)
                .mapToObj(i -> "%d * %d = %d".formatted(finalDan,i,finalDan * i ))
                .collect(Collectors.joining("<br>"));
    }


    @GetMapping("/mbtitest/{name}")
    @ResponseBody
    public String showMbtiTets(@PathVariable String name) {
        //http://localhost:8080/mbti/{name}  >> @PathVariable
        System.out.println("15강, MBTI 만들어보자  ::::: " + name ) ;
        String rs = switch (name) {
            case "홍길동" -> "INFP" ;
            case "홍길순" -> "ENFP" ;
            case "임꺽정" -> "AAAA" ;
            case "가나다" -> "BBBB" ;
            case "마바사" -> "CCCC" ;
            case "아자차" -> "DDDD" ;
            default ->  "모름";
        } ;
        return rs ;
    }

    @GetMapping("/mbti/{name}")
    @ResponseBody
    public String showMbti(@PathVariable String name) {
        //http://localhost:8080/mbti/{name}  >> @PathVariable
        System.out.println("15강, MBTI 만들어보자  ::::: " + name ) ;
        String rs = switch (name) {
            case "홍길동" -> {
                char j = 'J' ;
                yield  "INF" + j  +"___" +  name   ;  // switch 안에서 return 을 쓰면 헷갈릴까봐 yield로 사용
            }
            case "홍길순" -> "ENFP___" + name  ;
            case "임꺽정", "아이유" -> "ISTP___" + name ;
            default ->  "모름";
        } ;
        return rs ;
    }

    @GetMapping("/saveSession/{name}/{value}")
    @ResponseBody
    public String saveSession(@PathVariable String name, @PathVariable String value, HttpServletRequest req) {
        //http://localhost:8080/saveSession/age/10
        System.out.println("17강~18강,  세션으로 나이 저장하기 :::: name : " + name + "value : " + value) ;
        HttpSession session = req.getSession() ;
        session.setAttribute(name, value) ;
        System.out.println("세션변수의 값 확인 : " + session ) ;
        return "세션변수 %s의 값이 %s(으)로 설정되었습니다!!".formatted(name, value);
    }

    @GetMapping("/getSessionJsessionid/{name}/{value}")
    @ResponseBody
    //HttpServletRequest req를 할 때 쿠키가 들어옴, 쿠키 안에는 JSESSIONID이 들어있고 거기서 세션을 얻을 수 있다.
    // 해당 방법은 쿠키를 통해서 세션을 가져오는 방법
    public String getSessionJsessionid(@PathVariable String name, @PathVariable String value, HttpServletRequest req) {
        //http://localhost:8080/getSessionJsessionid/age/10
        System.out.println("17강~18강,  세션으로 나이 저장하기 :::: name : " + name + "value : " + value) ;
        HttpSession session = req.getSession() ;
        session.setAttribute(name, value) ;
        System.out.println("세션변수의 값 확인 : " + session ) ;
        return "세션변수 %s의 값이 %s(으)로 설정되었습니다!!".formatted(name, value);
    }

    @GetMapping("/getSession/{value}")
    @ResponseBody
    // session을 통해서 바로 받아오는 방법
    public String getSession(@PathVariable String value, HttpSession session) {
        //http://localhost:8080/getSession/age
        System.out.println("17강~18강,  세션으로 나이 저장하기 :::: value : " + value ) ;
        String valueTmp = (String) session.getAttribute(value);
        System.out.println("세션변수의 값 확인 : " + valueTmp ) ;
        return "세션변수 %s의 값이 %s입니다!!! !".formatted(value, valueTmp);
    }

    @GetMapping("/addArticle/{title}/{body}")
    @ResponseBody
    public String addArticle(@PathVariable String title, @PathVariable String body) {
        //http://localhost:8080/addArticle/title/body
        System.out.println("19강,  addArticle action  :::: title : " + title ) ;
        int id = 1;
        Article article = new Article(id, title, body) ;
        System.out.println("값 체크 해보자  :::: id : " + id + " title : " +title + " body : " + body ) ;
        return "%d번 게시물이 생성 되었습니다!!!".formatted(id, title, body) ;
    }

    @AllArgsConstructor
    class Article {
        private int id ;
        private String title ;
        private String body ;
    }









}