package com.abhi.SpringSecEx.Controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloCOntroller {

    @GetMapping("/")
    public ResponseEntity<String > sayHello(HttpServletRequest request){
        return new ResponseEntity<>("this is a Hello from controller, eeewwaaaaa i am not secure , but " + "\n this is the session id: " + request.getRequestedSessionId(), HttpStatus.OK);


    }


    @GetMapping("/csrf")
    public CsrfToken csrfToken(HttpServletRequest req){
        return (CsrfToken) req.getAttribute("_csrf");
//        CsrfToken t1 =  (CsrfToken) req.getAttribute("_csrf");
//
//        System.out.println("csrf token details: "+ t1.getToken());
//        return t1;

    }

}
