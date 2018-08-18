package com.solr.yang.service.solrservice.impl;

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
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.solr.yang.pagemodel.DataGrid;
import com.solr.yang.po.Doc;
import com.solr.yang.service.solrservice.Solr;

/**
 * @Author: yang
 * @ProjectName: solr
 * @Package: com.solr.yang.service.solrservice.impl
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
        solrQuery.set("fl", "id,size,content,url");
        
        //solrQuery.setParam("hl", "true");
        //solrQuery.setParam("hl.q", info);
        solrQuery.setHighlight(true);     
        solrQuery.setParam("hl.fl", "url,content");
        //solrQuery.setParam("hl.preserveMulti", "true");
        solrQuery.setHighlightSimplePre("<font color='red'>");
        solrQuery.setHighlightSimplePost("</font>");
        solrQuery.setHighlightSnippets(3);
        solrQuery.setHighlightFragsize(500);
  
        QueryResponse response = solrClient.query(solrQuery);
        Map<String,Map<String,List<String>>> highLight = response.getHighlighting();
        SolrDocumentList solrDocuments = response.getResults();
        //System.out.println(solrDocuments);
        List<Doc> list = new ArrayList<>();

        for (SolrDocument document: solrDocuments){
        	//System.out.println(solrDocuments);
            String file = (String) document.get("id");
            String url = (String) document.get("url");
            Object content =  document.get("content");
            long size = (Long) document.get("size");
            Map<String,List<String>> map = highLight.get(document.get("id"));
            List<String> urlList = map.get("url");
            List<String> contentList = map.get("content");
            System.out.println(urlList);
            Doc doc = new Doc();
            doc.setFile(file.replaceAll("\\\\","\\\\\\\\"));
            doc.setContent(content.toString());
            doc.setSize(size);
            doc.setUrl(url);
            if(urlList!=null&&urlList.size()>0){
            	doc.setUrl(urlList.get(0).replaceAll("\\\\","\\\\\\\\"));
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
        System.out.println(JSON.toJSONString(grid)); 

        return JSON.toJSONString(grid);
        //return solrDocuments.toString();
    }

    /**
     * 同步并创建索引
     */
    @Override
    public void commitSolr() throws IOException, SolrServerException {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url + "/dataimport?command=full-import&clean=false&commit=true");
       System.out.println(httpClient.execute(httpGet));
        httpClient.close();
    }

}
