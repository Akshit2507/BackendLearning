package com.backend.project.Elasticsearch.Conversations;

import lombok.Data;
import org.json.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName="conversations_index", createIndex = true)
@Data
public class Conversation {

    @Id
    public String id;

    @Field(type = FieldType.Keyword)
    public String postId;

    public JSONObject convoObject;

    public Conversation(String registerIndex, String postId, JSONObject record) {
        id=registerIndex;
        this.postId=postId;
        convoObject=record;
    }
}
