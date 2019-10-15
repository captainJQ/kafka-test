package com.jql.elasticsearch;

import com.jql.utils.ObjectUtils;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ElasticsearchUtils {
    private static RestHighLevelClient client = ElasticsearchClientFactory.getClientInstance();

    public static void insertOrUpdate(Object o,String index) throws Exception {
        Map<String, Object> oMap = ObjectUtils.toMap(o);
        IndexRequest request = new IndexRequest(index);
        request.source(oMap);
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
    }

    public static Map<String, Object> get(String index, String id) throws IOException {
        GetRequest getRequest = new GetRequest();
        getRequest.index(index);
        getRequest.id(id);
        GetResponse documentFields = client.get(getRequest, RequestOptions.DEFAULT);
        return documentFields.getSource();
    }

    public static List<Map<String,Object>> search(String index) throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
/*        TermQueryBuilder nameTerm = QueryBuilders.termQuery("name", "liyan");//等值查询
        Integer[] channels = {20, 22};
        TermsQueryBuilder orderChannelTerm = QueryBuilders.termsQuery("age", channels);//in查询*/
/*        Integer[] activeType = {0, 8, 9, 7};
        TermsQueryBuilder activeTypeTerm = QueryBuilders.termsQuery("activeType", activeType);*/
/*        AggregationBuilder orderItemIdAgg =    .field("b2corditemid").size(Integer.MAX_VALUE);
        Date now = new Date();
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(now);//把当前时间赋给日历
        calendar.add(calendar.MONTH, -3); //设置为前3月
        Date dBefore = calendar.getTime(); //得到前3月的时间*/
/*        RangeQueryBuilder payTimeRange = QueryBuilders.rangeQuery("payTime").gt(dBefore).lt(now);//范围查询*/

        searchSourceBuilder.query();

        SearchRequest request = new SearchRequest();
        request.indices(index);
        request.source(searchSourceBuilder);

        SearchResponse search = client.search(request, RequestOptions.DEFAULT);
        SearchHit[] hits = search.getHits().getHits();
        List<Map<String,Object>> results = new ArrayList<>();
        for (SearchHit hit : hits) {
            results.add(hit.getSourceAsMap());
        }
        return results;
    }

    public static List<Map<String,Object>> search(String index,ElasticSearchCondition condition) throws IOException {
        SearchRequest request = new SearchRequest();
        request.indices(index);
        request.source(condition.getSearchSourceBuilder());

        SearchResponse search = client.search(request, RequestOptions.DEFAULT);
        SearchHit[] hits = search.getHits().getHits();
        List<Map<String,Object>> results = new ArrayList<>();
        for (SearchHit hit : hits) {
            results.add(hit.getSourceAsMap());
        }
        return results;
    }

    public static List<Map<String,Object>> search(String index,SearchSourceBuilder builder) throws IOException {
        SearchRequest request = new SearchRequest();
        request.indices(index);
        request.source(builder);

        SearchResponse search = client.search(request, RequestOptions.DEFAULT);
        SearchHit[] hits = search.getHits().getHits();
        List<Map<String,Object>> results = new ArrayList<>();
        for (SearchHit hit : hits) {
            results.add(hit.getSourceAsMap());
        }
        return results;
    }

}
