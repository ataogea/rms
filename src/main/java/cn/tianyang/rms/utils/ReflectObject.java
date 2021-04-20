package cn.tianyang.rms.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class ReflectObject {
    public static void objectByMap(Object object, Map<String, Object> map) throws NoSuchMethodException, SecurityException, IllegalAccessException, InvocationTargetException {
        Class clazz = object.getClass();

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            String name = field.getName();
            String methodName = getMethodName(name);
            String key = getOnlineName(name);
            String value = map.get(key)==null?"":map.get(key).toString();
            Method m = clazz.getMethod("set" + methodName,String.class);
            m.invoke(object, value);
        }
    }

    //下划线字符串转驼峰型
    public static String getMethodName(String str) {
        char[] cs = str.toCharArray();
        cs[0] = (char) (cs[0]-'a'+'A');
        for (int i = 0; i < cs.length; i++) {
            if(cs[i] == '_' && i < cs.length-1){
                if (cs[i+1] >= 'a' && cs[i+1] <= 'z') {
                    cs[i+1] = (char) (cs[i+1]-'a'+'A');
                }
            }
        }
        str = new String(cs);
        str = str.replace("_", "");
        return str;
    }

    //驼峰转为下划线类型
    public static String getOnlineName(String str){
        StringBuffer sb = new StringBuffer();
        char[] cs = str.toCharArray();
        for (int i = 0; i < cs.length; i++) {
            if (cs[i] >= 'A' && cs[i] <= 'Z') {
                cs[i] = (char) (cs[i] - 'A' + 'a');
                sb.append("_"+cs[i]);
            }else if(cs[i] >= '0' && cs[i] <= '9'){
                sb.append("_"+cs[i]);
            }else{
                sb.append(cs[i]);
            }
        }

        return sb.toString();
    }
}
