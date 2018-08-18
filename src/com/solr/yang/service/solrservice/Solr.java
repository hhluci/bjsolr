package com.solr.yang.service.solrservice;

/**
 * @Author: yang
 * @ProjectName: solr
 * @Package: com.solr.yang.service.solrservice
 * @Description:
 * @Date: Created in 12:19 2018/8/15
 */
public interface Solr {

    /**
     * 检索
     * @param current
     * @param rowCount
     * @param info
     * @return
     */
    String getSolr(int current, int rowCount, String info) throws Exception;
    /**
     * 同步并创建索引
     */
    void commitSolr() throws Exception;

}
