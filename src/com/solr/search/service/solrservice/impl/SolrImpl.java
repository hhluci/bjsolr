package com.solr.search.service.solrservice.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.solr.search.pagemodel.DataGrid;
import com.solr.search.po.Doc;
import com.solr.search.service.solrservice.Solr;

/**
 * @Author: search
 * @ProjectName: solr
 * @Package: com.solr.search.service.solrservice.impl
 * @Description:
 * @Date: Created in 12:22 2018/8/15
 */
@Service
public class SolrImpl implements Solr {
	String url = "http://127.0.0.1:8983/solr/search";
	
    SolrClient solrClient = new HttpSolrClient.Builder(url)
                                              .withConnectionTimeout(10000)
                                              .withSocketTimeout(60000)
                                              .build();
    /**
     * 检索
     *
     * @param current
     * @param rowCount
     * @param info
     * @return
     */
    @Override
    public String getSolr(int current, int rowCount, String info) throws IOException, SolrServerException {

        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setStart((current-1)*rowCount);
        //solrQuery.setStart(13);
        solrQuery.setRows(rowCount);
        solrQuery.setQuery(info);
        solrQuery.set("wt", "json");
        
        solrQuery.set("df","file_keywords");
        solrQuery.setHighlight(true);     
        solrQuery.setParam("hl.fl", "fileName,content");
        solrQuery.setHighlightSimplePre("<font color='red'>");
        solrQuery.setHighlightSimplePost("</font>");
        solrQuery.setHighlightSnippets(3);
        solrQuery.setHighlightFragsize(500);
  
        QueryResponse response = solrClient.query(solrQuery);
        Map<String,Map<String,List<String>>> highLight = response.getHighlighting();
        SolrDocumentList solrDocuments = response.getResults();
        List<Doc> list = new ArrayList<>();

        for (SolrDocument document: solrDocuments){

            String file = (String) document.get("id");
            String fileName = (String) document.get("fileName");
            Object content =  document.get("content");
            long size = (Long) document.get("size");
            Map<String,List<String>> map = highLight.get(document.get("id"));
            List<String> fileNameList = map.get("fileName");
            List<String> contentList = map.get("content");
    
            Doc doc = new Doc();
            doc.setFile(file.replaceAll("\\\\","\\\\\\\\"));
            if(content.toString().length()>100) {
            	doc.setContent(content.toString().substring(0,100));
            }else {
            	 doc.setContent(content.toString());
            }
           
            doc.setSize(size);
            doc.setFileName(fileName);
            doc.setHlName(fileName);
            if(fileNameList!=null&&fileNameList.size()>0){
            	doc.setFileName(fileNameList.get(0));
            }
            if(contentList!=null&&contentList.size()>0){
            	doc.setContent(contentList.get(0));
            }
            list.add(doc);  
         
        }

        DataGrid<Doc> grid = new DataGrid<>();
        grid.setCurrent(current);
        grid.setRowCount(rowCount);
        grid.setTotal(solrDocuments.getNumFound());
        
        grid.setRows(list);
        //System.out.println(grid.toString());
        //System.out.println(JSON.toJSONString(grid)); 

        return JSON.toJSONString(grid);
    }

    /**
     * 同步并创建索引
     */
    @Override
    public void commitSolr() throws IOException, SolrServerException {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url + "/dataimport?command=full-import&clean=false&commit=true");
        //System.out.println(httpClient.execute(httpGet));
        httpClient.close();
    }


}
