package com.backend.project.RSS;

import com.rometools.rome.feed.synd.SyndEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RssKafkaController {

    @Autowired
    private RssFeedService rssFeedService;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @GetMapping("/fetchAndSend")
    public void fetchAndSendRssFeeds() {
        List<SyndEntry> entries = rssFeedService.getRssFeedEntries("http://www.reddit.com/.rss");

        if (entries != null) {
            for (SyndEntry entry : entries) {
                kafkaProducerService.sendMessage("rssTopic", entry.getTitle() + " - " + entry.getLink());
            }
        }
    }
}