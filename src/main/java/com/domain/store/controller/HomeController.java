package com.domain.store.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Data
@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}")
public class HomeController {

    @GetMapping("/csrf")
    public CsrfToken csrf(HttpServletRequest request){
        return  (CsrfToken) request.getAttribute("_csrf");
    }


    @GetMapping("/session")
    public String getSession(HttpServletRequest request){
        return request.getSession().getId();
    }
}
