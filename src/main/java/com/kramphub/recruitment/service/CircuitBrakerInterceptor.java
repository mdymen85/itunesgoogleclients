package com.kramphub.recruitment.service;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.redisson.Redisson;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CircuitBrakerInterceptor /**implements HandlerInterceptor**/ {

//	@Value("${application.lockmanager.redis.cache-name:circuitBreaker}")
//	private String cache;
//	
//	private final RedissonClient redissonClient;
//
//	@Override
//	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {					
//		
//		RMap<String, Boolean> map = redissonClient.getMap(cache);
//		
//		return map.get(cache) || map.get(cache) == null ? true : false;
//	}
}
