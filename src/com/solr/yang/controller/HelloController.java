package com.solr.yang.controller;

import com.solr.yang.service.solrservice.Solr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/hello")
public class HelloController {

    @Autowired
    private Solr solr;

    @RequestMapping(value = "/hello")
    public ModelAndView hello(){
        return new ModelAndView("index");
    }

    @RequestMapping(value = "/test.do")
    public String test(){
        try {
            return solr.getSolr(1, 10, "*:*");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
