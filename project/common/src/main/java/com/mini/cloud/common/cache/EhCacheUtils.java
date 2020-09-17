package com.mini.cloud.common.cache;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class EhCacheUtils implements CacheService{
	@Override
	public <T> T get(String key, Class<T> clazz) {
		return null;
	}

	@Override
	public String get(String key) {
		return null;
	}

	@Override
	public String get(String key, long expire) {
		return null;
	}

	@Override
	public List<String> getBatchList(Set<String> keys) {
		return null;
	}

	@Override
	public Map<String, String> getBatchMap(Set<String> keys) {
		return null;
	}

	@Override
	public void delete(String key) {
		
	}

	@Override
	public void delete(Collection<String> keys) {
		
	}

	@Override
	public Set<String> keys(String pattern) {
		return null;
	}

	@Override
	public void mput(String key, Map<String, String> vals) {

	}

	@Override
	public Object mget(String key, String hashKey) {
		return null;
	}

}
