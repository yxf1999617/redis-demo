package demo;

import com.alibaba.fastjson.JSON;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

public class zy {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1",6379);
        System.out.println("连接成功");
        Artick artick =new Artick();
        artick.setTitle("fastjson");
        artick.setAuthor("author");
        artick.setContent("cc");
        artick.setTime("2019");
        Long id=save(artick,jedis);
        System.out.println(id);
        System.out.println("保存成功");
        Artick artick1=get(jedis,id);
        System.out.println(artick1);
        System.out.println("查找成功");
        artick.setTime("2017");
        Long pid=upd(artick,jedis,id);
        Artick artick2=get(jedis,pid);
        System.out.println(artick2);
        Long a=del(jedis,id);
        System.out.println(a);
    }
    public static Long save(Artick artick,Jedis jedis){
        Long id=jedis.incr("posts");
        Map<String,String> map=new HashMap<String, String>();
        map.put("title",artick.getTitle());
        map.put("author",artick.getAuthor());
        map.put("content",artick.getContent());
        map.put("time",artick.getTime());
        jedis.hmset("post:"+id+":data",map);
        return id;
    }
    public static Artick get(Jedis jedis,Long id){
        Map<String,String> map = jedis.hgetAll("post:"+id+":data");
        Artick artick=new Artick();
        artick.setTitle(map.get("title"));
        artick.setAuthor(map.get("author"));
        artick.setContent(map.get("content"));
        artick.setTime(map.get("time"));
        return artick;
    }
    public static Long del(Jedis jedis,Long id){
        long a=jedis.hdel("post:"+id+":data");
        return a;
    }
    public static Long upd(Artick artick,Jedis jedis,Long id){
        Long pid=id;
        Map<String,String> map=new HashMap<String, String>();
        map.put("title",artick.getTitle());
        map.put("author",artick.getAuthor());
        map.put("content",artick.getContent());
        map.put("time",artick.getTime());
        jedis.hmset("post:"+id+":data",map);
        return pid;
    }
}
