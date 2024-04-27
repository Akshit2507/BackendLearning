package com.backend.project.Elasticsearch.Conversations;


import co.elastic.clients.elasticsearch.indices.CreateIndexRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/conversations")
public class ConversationController {

    final ConversationRepository conversationRepository;

    @Autowired
    public ConversationController(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    @GetMapping("/{id}")
    public List<Conversation> getConversation(@PathVariable String id){
//
//        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
//                .withQuery(QueryBuilders.termQuery("yourSearchField", searchTerm))
//                .withPageable(PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "yourSortField")))
//                .build();
//
//        // Execute the search query
//        SearchHits<Conversation> searchHits = elasticsearchOperations.search(searchQuery, YourDocument.class);
//
//        // Extract the documents from search hits
//        return searchHits.get().map(SearchHit::getContent).collect(Collectors.toList());
        Sort sort=Sort.by(Sort.Direction.DESC,"timestamp");
        PageRequest pageRequest=PageRequest.of(0,20,sort);
        return conversationRepository.findAllByPostId(id,pageRequest).stream().toList();

    }

    @PostMapping("/")
    public Conversation addConversation(@RequestBody Conversation conversation){
        return conversationRepository.save(conversation);
    }

}
