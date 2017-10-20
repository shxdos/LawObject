package com.shx.law.utils;

/**
 * Created by 邵鸿轩 on 2017/7/31.
 */

public class StringUtils {
    public static int indexOfArr(String[] arr,String value2){
        for(int i=0;i<arr.length;i++){
            if(arr[i].equals(value2)){
                return i;
            }else{//做了容错,不是完全匹配
                if(value2.startsWith(arr[i])){
                    return i;
                }
            }
        }
        return -1;
    }

}
