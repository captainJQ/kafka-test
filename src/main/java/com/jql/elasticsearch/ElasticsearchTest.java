package com.jql.elasticsearch;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ElasticsearchTest {

    @Test
    public void insertOrUpdate() throws Exception {
        Map map = new HashMap<String,String>();
        map.put("name","zhumeiling");
        map.put("age",16);
        map.put("description","i determinded travel around the world until met you");
        Dog dog = new Dog();
        dog.setName("liyan de gou");
        dog.setAge(3);
        dog.setType("demu");
        map.put("pet",dog);

        People people = new People();

        people.setName("liyan");
        people.setAge(22);
        people.setPet(dog);

        ElasticsearchUtils.insertOrUpdate(map,"people");
/*        ElasticsearchUtils.insertOrUpdate(people,"people");
        ElasticsearchUtils.insertOrUpdate(dog,"dog");*/

    }

    @Test
    public void get() throws IOException {
        Map<String, Object> result = ElasticsearchUtils.get("people", "Ax1rzW0BdH6rvejHOJHU");

        System.out.println(result);

    }

    @Test
    public void search() throws Exception{
        List<Map<String, Object>> peoples = ElasticsearchUtils.search("people");
        peoples.stream().forEach(p->System.out.println(p));
        /*List<Map<String, Object>> dogs = ElasticsearchUtils.search("dog");
        dogs.stream().forEach(p->System.out.println(p));*/
    }

    /**
     * must 和 filter 区别  must 对评分有帮助
     * @throws Exception
     */
    @Test
    public void searchMust() throws Exception{
        ElasticSearchCondition condition = new ElasticSearchCondition();
        Map<String,String> map = new HashMap<>();
        map.put("name","liyan");
        condition.equals(map);
        List<Map<String, Object>> peoples = ElasticsearchUtils.search("people",condition);
        peoples.stream().forEach(p->System.out.println(p));
    }

    @Test
    public void searchFilter() throws Exception{
        ElasticSearchCondition condition = new ElasticSearchCondition();
        Map<String,Object> map = new HashMap<>();
        map.put("age",16);
        condition.filter(map);
        List<Map<String, Object>> peoples = ElasticsearchUtils.search("people",condition);
        peoples.stream().forEach(p->System.out.println(p));
    }

    /**
     *   范围查询   和   ids  查询
     * @throws IOException
     */
    @Test
    public void search1() throws IOException {
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.size(50);
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("age").gte(20).lte(24);
        builder.query(rangeQueryBuilder);


/*        IdsQueryBuilder idsQueryBuilder = QueryBuilders.idsQuery().addIds("Ax1rzW0BdH6rvejHOJHU");
        builder.query(idsQueryBuilder);*/

        List<Map<String, Object>> peoples = ElasticsearchUtils.search("people",builder);
        peoples.stream().forEach(p->System.out.println(p));

    }


    @Test
    public void search2() throws IOException {
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.size(50);

        ExistsQueryBuilder description = QueryBuilders.existsQuery("_id");
        builder.query(description);

        List<Map<String, Object>> peoples = ElasticsearchUtils.search("people",builder);
        peoples.stream().forEach(p->System.out.println(p));

    }

    @Test
    public void test() throws IOException {
        RestHighLevelClient client = ElasticsearchClientFactory.getClientInstance();
        IndexRequest indexRequest = new IndexRequest();
        client.index(indexRequest, RequestOptions.DEFAULT);
    }





}
