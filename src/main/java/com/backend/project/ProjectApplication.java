package com.backend.project;

import com.backend.project.StreamData.ReadFromCsv;
import com.backend.project.kafkaConsumer.ConsumeComments;
import com.backend.project.kafkaConsumer.ConsumePosts;
import com.backend.project.kafkaConsumer.KafkaConfigConsumer;
import com.backend.project.kafkaProducer.KafkaConfigProducer;
import com.backend.project.kafkaProducer.ProduceComments;
import com.backend.project.kafkaProducer.ProducePosts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableKafka
@EnableMongoRepositories( basePackages = "com.backend.project")
@EnableWebMvc
@EnableElasticsearchRepositories(basePackages = "com.backend.project")
public class ProjectApplication {

	@Autowired
	ConsumeComments consumeComments;
	@Autowired
	ConsumePosts consumePosts;

	@Autowired
	KafkaConfigConsumer kafkaConfigConsumer;
//	@Autowired
//	static RedisService redisService;
	public static void main(String[] args) {

//		writeStuff();

//		RedisService redisService=new RedisService(new RedisConfig());
//		for (int i=0;i<5;i++){
//			redisService.addToSet("id number"+i);
//		}
//
//		for (int i=0;i<10;i++){
//			System.out.println(redisService.isParentInteresting("id number"+i));
//		}


		SpringApplication.run(ProjectApplication.class, args);

		Thread readFromCsv=new Thread(new ReadFromCsv());
		readFromCsv.start();

	}


	public static void writeStuff(){
		KafkaConfigProducer kafkaConfigProducer =new KafkaConfigProducer();
//		StreamComments streamComments = new StreamComments(new ProduceComments(kafkaConfigProducer));
//
//		StreamPosts streamPosts=new StreamPosts(new ProducePosts(kafkaConfigProducer));
//
		Thread postThread = new Thread(new ConsumePosts());
		Thread commentThread = new Thread(new ConsumeComments());

		Thread streamData=new Thread(new ReadFromCsv());


//		streamPosts.streamOfPosts();
//		for (int i=0;i<5;i++){
//			System.out.println(i);
//			streamPosts.streamOfPosts();
//			streamComments.streamOfComments();
//		}
		postThread.start();
		commentThread.start();
	}

}
