package com.kramphub.recruitment.watchdog;

import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CircuitBreakerWatchdog {

	@Value("${application.lockmanager.redis.cache-name:circuitBreaker}")
	private String cache;
	
//	private final RedissonClient redissonClient;
	
	/**
	 * This watchdog set a value on the cache every five seconds.
	 */
//	@Scheduled(fixedDelay = 5000)
//	public void circuitbreakerJob() {
//		RMap<String, Boolean> map = redissonClient.getMap(cache);
//		map.put(cache, true);
//	}
}
