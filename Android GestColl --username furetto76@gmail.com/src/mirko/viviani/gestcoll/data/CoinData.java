/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package mirko.viviani.gestcoll.data;


import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import mirko.viviani.xmlData.coins.Info;
import mirko.viviani.xmlData.coins.Moneta;


public class CoinData {
	
	private static SortedMap<String, Moneta> data;
	private static Info info;

	static {
		data = new TreeMap<String, Moneta>();
		info = new Info();
	}

	

	/**
	 * @return the info
	 */
	public static Info getInfo() {
		return info;
	}

	/**
	 * @param info the info to set
	 */
	public static void setInfo(Info info) {
		CoinData.info = info;
	}

	/**
	 * Load coin data reading from xml
	 */
	public static void addCoin(Moneta m) {
		data.put(m.getId(), m);
	}
	
	public static void Clear() {
		data.clear();
	}
	
	
	public static SortedMap<String, Moneta> getData() {
		return data;
	}

    /**
     * Get coin data using id
     * @param id
     * @return
     * @throws Exception 
     */
    public static Moneta getData(String id) throws Exception {
    	if (data.containsKey(id)) {
    		return data.get(id);
    	} else {
    		throw new Exception("Unable to find coin id: "+id);
    	}
    }
    
    /**
     * Get all available ids
     * @return
     */
    public static Set<String> getIds() {
    	return data.keySet();
    }
    
    public String[] getArrayIds() {
    	String[] ret = getIds().toArray(new String[0]); 
    	return ret;
    }
    
    
}
