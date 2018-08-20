package com.solr.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.solr.search.service.solrservice.Solr;

@Controller
public class DocController {

	 	@Autowired
	    private Solr solr;
		
		@RequestMapping("/search.do")
	    String search(){
			return "search";
		}

	    @RequestMapping(value="/getdoclist.do",produces = {"application/json;charset=UTF-8"})
	    @ResponseBody
		public String getDoc(@RequestParam(value="current",defaultValue = "1") int current, 
				@RequestParam(value="rowCount",defaultValue = "5") int rowCount,
	            @RequestParam(value = "searchPhrase",defaultValue = "*:*") String info){

	        try {
	            return solr.getSolr(current, rowCount, info);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return "";
	        }

	    }
}
