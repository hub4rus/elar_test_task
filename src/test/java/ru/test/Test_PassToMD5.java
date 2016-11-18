package ru.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.test.service.Impl.PassServiceImpl;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by rrv on 16.11.16.
 */
public class Test_PassToMD5
{
    private Map<Integer,Map<String,String>> map=new HashMap();

    @Before
    public void st() {
        Map<String,String> x=new HashMap();
        x.put("black","1ffd9e753c8054cc61456ac7fac1ac89");
        map.put(1,x);

        x=new HashMap();
        x.put("white","d508fe45cecaf653904a0e774084bb5c");
        map.put(2,x);
    }

    @Test
    public void testPass() {
        final PassServiceImpl md5=new PassServiceImpl();

        for(Map.Entry<Integer,Map<String,String>> e : map.entrySet()) {
            System.out.println(e.getKey()+":");
            for(Map.Entry<String,String> x : e.getValue().entrySet()) {
                System.out.printf(" [ %s -> %s ]",x.getKey(),x.getValue());
                System.out.println();
                assertEquals(x.getValue(), md5.to_MD5(x.getKey()));
            }
        }

        assertNotNull(md5.getMap());
        assertEquals(map.size(),md5.getMap().size());

        System.out.println();
        System.out.println("pass.txt :");

        for(Map.Entry<Integer,String> e : md5.getMap().entrySet()) {
            System.out.printf(" [ %s -> %s ]",e.getKey(),e.getValue());
            System.out.println();

            for(Map.Entry<String,String> x : map.get(e.getKey()).entrySet()) {
                assertEquals(x.getValue(), e.getValue());
            }
        }
    }

    @After
    public void ed() {
        for(Map.Entry<Integer,Map<String,String>> e : map.entrySet()) {
            e.getValue().clear();
        }
        map.clear();
        map=null;
    }

}
