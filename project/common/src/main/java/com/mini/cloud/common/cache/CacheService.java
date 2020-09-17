package com.mini.cloud.common.cache;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface CacheService {

	public <T> T get(String key, Class<T> clazz);
	
	public String get(String key);
	
	public String get(String key, long expire);
	
	public List<String> getBatchList(Set<String> keys);
	
	public Map<String, String> getBatchMap(Set<String> keys);
	
	public void delete(String key);
	
	public void delete(Collection<String> keys);
	
	public Set<String> keys(String pattern);



	//
	public void mput(String key,Map<String, String> vals);
	public Object mget(String key,String hashKey);

}
