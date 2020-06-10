package com.hrt.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * ParamPropUtils
 * <p>
 * This is description
 * </p>
 *
 * @author xuegangliu 2020/04/27
 * @since 1.8
 **/
public final class ParamPropUtils {
    private static final String PARAM_PATH="param-test.properties";
    public static Properties props;

    static {
        props = new Properties();
        InputStream in = null;
        try {
            in = ParamPropUtils.class.getClassLoader().getResourceAsStream(PARAM_PATH);
            BufferedReader bf = new BufferedReader(new InputStreamReader(in));
            props.load(bf);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}