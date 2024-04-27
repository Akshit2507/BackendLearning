package com.backend.project.Elasticsearch.Posts;


import lombok.Data;
import org.json.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.text.SimpleDateFormat;

@Data
@Document(indexName = "post_index", createIndex = true)
public class Post {

    @Id
    @Field(type = FieldType.Keyword)
    public String postId;
    public JSONObject postObject;

    @Field(type=FieldType.Date)
    public SimpleDateFormat timestamp;

    public Post(String postId, JSONObject record, SimpleDateFormat timestamp) {
        this.postId=postId;
        this.postObject=record;
        this.timestamp=timestamp;
    }
}
