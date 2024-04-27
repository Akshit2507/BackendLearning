package com.backend.project.Elasticsearch.Posts;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends ElasticsearchRepository<Post,String> {

    public Post findPostByPostId(String postId);
}
