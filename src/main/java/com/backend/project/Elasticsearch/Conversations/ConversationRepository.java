package com.backend.project.Elasticsearch.Conversations;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationRepository extends ElasticsearchRepository<Conversation,String> {
//
//    public List<Conversation> findAllByPostId(String postId, PageRequest pageRequest);

    public Page<Conversation> findAllByPostId(String postId,PageRequest pageRequest);
}
