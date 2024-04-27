package com.backend.project.Elasticsearch.Posts;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    final PostRepository postRepository;

    @Autowired
    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @PostMapping("/")
    public Post addPost(@RequestBody Post post){
        return postRepository.save(post);
    }

    @GetMapping("/{id}")
    public Post getPost(@PathVariable String id){
        return postRepository.findPostByPostId(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id){
        postRepository.deleteById(id);
    }

    @GetMapping("/latest")
    public Post getLatestPost(){
        Sort sort=Sort.by(Sort.Direction.DESC,"timestamp");
        PageRequest pageRequest=PageRequest.of(0,1,sort);
        return postRepository.findAll(pageRequest).getContent().stream().findFirst().orElse(null);
    }
}
