package ru.test.service.Impl;

import org.springframework.stereotype.Service;
import ru.test.service.PassService;

import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rrv on 16.11.16.
 */
@Service("PassService")
public class PassServiceImpl implements PassService
{
    public String to_MD5(String yourString) {
        String rez=null;
        try {
            byte[] bytesOfMessage = yourString.getBytes("UTF-8");

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] thedigest = md.digest(bytesOfMessage);

            rez = new BigInteger(1, thedigest).toString(16);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException ea) {
            ea.printStackTrace();
        }
        return rez;
    }

    public Map<Integer, String> getMap() {
        return map;
    }

    final private Map<Integer,String> map=new HashMap();

    public PassServiceImpl()
    {
        InputStream is = getClass().getClassLoader().getResourceAsStream("pass.txt");
        if (is!=null) {
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            String line = null;
            String[] sx;
            try {
                while((line = in.readLine()) != null) {
                    if (line!=null) {
                        sx=line.split("   ");
                        //sx=line.split("\t");
                        if (sx!=null && sx.length>1)
                        {
                            map.put(Integer.parseInt(sx[0]),sx[1]);
                        }
                    }
                    //System.out.println("line="+line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
