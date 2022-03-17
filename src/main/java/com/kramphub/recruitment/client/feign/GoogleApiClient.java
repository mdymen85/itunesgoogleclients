package com.kramphub.recruitment.client.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Deprecated
@FeignClient(value ="googleapi", url = "https://www.googleapis.com/books", configuration = GoogleApiClientConfig.class)
public interface GoogleApiClient {

    @RequestMapping(method = RequestMethod.GET, path="/v1/volumes?q={term}&maxResults={limit}", consumes = "application/json", produces = "application/json")
    GoogleApiDTO get(@PathVariable("term") String term, @PathVariable("limit") int limit);
	
}
