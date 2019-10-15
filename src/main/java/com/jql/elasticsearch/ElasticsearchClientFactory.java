package com.jql.elasticsearch;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

public class ElasticsearchClientFactory {

    private final static String[] ips = {"47.100.77.66:9200","47.100.77.66:9300"};
    private final static HttpHost[] httpHosts = new HttpHost[ips.length];
    private final static RestClientBuilder builder;
    private final static RestHighLevelClient client;

    static {
        for(int i=0;i<ips.length;i++){
            httpHosts[i] = HttpHost.create(ips[i]);
        }
        builder = RestClient.builder(httpHosts);
        client = new RestHighLevelClient(builder);
    }

    public static RestHighLevelClient getClientInstance(){
        return client;
    }

}
