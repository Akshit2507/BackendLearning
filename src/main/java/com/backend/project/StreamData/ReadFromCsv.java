package com.backend.project.StreamData;


import com.backend.project.Elasticsearch.Conversations.Conversation;
import com.backend.project.Elasticsearch.Posts.Post;
import com.backend.project.kafkaProducer.ProduceComments;
import com.backend.project.kafkaProducer.ProducePosts;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ReadFromCsv implements Runnable {


    @Autowired
    ProducePosts producePosts;

    @Autowired
    ProduceComments produceComments;

    @Override
    public void run() {
        String csvFilePath="/Users/akshit.rewliya/Desktop/redditData.csv";

        try (CSVReader reader = new CSVReader(new FileReader(csvFilePath))) {
            String[] headers = reader.readNext(); // Read the headers

            String[] row;
            while ((row = reader.readNext()) != null) {
                Thread.currentThread().sleep(10000);
                JSONObject jsonObject = new JSONObject();

                for (int i = 0; i < row.length; i++) {
                    // Add each entry to the JSONObject with header as key
                    jsonObject.put(headers[i], row[i]);
                }

                sendDataToProducer(jsonObject);
                // Process your JSONObject here
                System.out.println(jsonObject.toString());
            }
        } catch (Exception e) {
            System.out.println("Csv file padhne m error");
            e.printStackTrace();
        }
    }


    private void sendDataToProducer(JSONObject record){
        Post post;
        Conversation conversation;
        if (record.get("comment_id")!=null) {

            // comment-> add in comment topic
            conversation=new Conversation(record.get("register_index").toString(),record.get("post_id").toString(),record);
            produceComments.writeComments(conversation);

        }else {

            // post -> add in post topic - es m dono m add kar denge
            SimpleDateFormat timestamp=new SimpleDateFormat(record.get("datetime").toString());
            post=new Post(record.get("post_id").toString(),record,timestamp);

            producePosts.writePost(post);
        }
    }
}
