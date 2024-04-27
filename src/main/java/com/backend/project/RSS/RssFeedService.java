package com.backend.project.RSS;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.io.SyndFeedInput;
//import org.elasticsearch.common.inject.Singleton;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

@Service
public class RssFeedService {

    public List<SyndEntry> getRssFeedEntries(String url) {
        try {
            URI builtUrl=new URI(url);
            SyndFeedInput input = new SyndFeedInput();
            return input.build(new File(builtUrl)).getEntries();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}