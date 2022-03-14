package com.kramphub.recruitment.client.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value ="itunes", url = "http://itunes.apple.com")
public interface ITunesClient {
	
    @RequestMapping(method = RequestMethod.GET, path = "/search?term={term}&limit={limit}", consumes = "application/json", produces = "application/json")
    ResponsesFeign get(@PathVariable("term") String term, @PathVariable("limit") int limit);
	
}
