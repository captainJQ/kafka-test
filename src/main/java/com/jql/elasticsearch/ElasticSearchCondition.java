package com.jql.elasticsearch;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.Map;

public class ElasticSearchCondition {

    private SearchSourceBuilder builder = new SearchSourceBuilder();

    private BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

    public void equals(Map<String,Object> kv){
        for (String key : kv.keySet()) {
            TermQueryBuilder term = QueryBuilders.termQuery(key, kv.get(key));
            boolQueryBuilder.must(term);
        }
    }

    public void filter(Map<String,Object> kv){
        for (String key : kv.keySet()) {
            TermQueryBuilder term = QueryBuilders.termQuery(key, kv.get(key));
            boolQueryBuilder.filter(term);
        }
    }

    public void notEquals(Map<String,Object> kv){
        for (String key : kv.keySet()) {
            TermQueryBuilder term = QueryBuilders.termQuery(key, kv.get(key));
            boolQueryBuilder.mustNot(term);
        }
    }


    public SearchSourceBuilder getSearchSourceBuilder(){
        return builder.query(boolQueryBuilder);
    }

}
