/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;

/**
 *
 * @author deray.wang
 */
public class CommonUtils {
     /** 
     * 
     * @param list 所有元素的平级集合，map包含id和pid 
     * @param pid 顶级节点的pid，可以为null 
     * @param idName id位的名称，一般为id或者code 
     * @return 树 
     */  
    public static List<Map<String, Object>> getTree(List<Map<String, Object>> list, String pid, String idName) {  
        List<Map<String, Object>> res = new ArrayList<Map<String,Object>>();  
        if (CollectionUtils.isNotEmpty(list)) {
            for (Map<String, Object> map : list) {
                //System.out.println(map);
                if (pid == null && map.get("p" + idName) == null || map.get("p" + idName) != null && map.get("p" + idName).equals(pid)) {
                    String id = (String) map.get(idName);
                    map.put("children", getTree(list, id, idName));
                    res.add(map);
                }
            }
        }
        return res;  
    }  
    
    public static <K, V> Map<K, V> list2Map3(List<V> list, String keyMethodName,Class<V> c) {  
        Map<K, V> map = new HashMap<K, V>();  
        if (list != null) {  
            try {  
                Method methodGetKey = c.getMethod(keyMethodName);  
                for (int i = 0; i < list.size(); i++) {  
                    V value = list.get(i);  
                    @SuppressWarnings("unchecked")  
                    K key = (K) methodGetKey.invoke(list.get(i));  
                    map.put(key, value);  
                }  
            } catch (Exception e) {  
                throw new IllegalArgumentException("field can't match the key!");  
            }  
        }  
  
        return map;  
    } 
}
