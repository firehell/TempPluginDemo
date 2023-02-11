package com.zz.tempplugindemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;
import okio.BufferedSink;
import okio.Okio;
import okio.Sink;
import okio.Source;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //读取apk到内存中
        File apk = new File(getCacheDir() + "/plugin.apk");
        try (Source source = Okio.source(getAssets().open("apk/plugin.apk"));
             BufferedSink sink = Okio.buffer(Okio.sink(apk))) {
            sink.writeAll(source);
        } catch (Exception e) {
            e.printStackTrace();
        }

        DexClassLoader classLoader = new DexClassLoader(apk.getPath(), getCacheDir().getPath(), null, null);
        try {
            Class hhClass = classLoader.loadClass("com.zz.plugin.Test" );
            Constructor constructor = hhClass.getDeclaredConstructors()[0];
            constructor.setAccessible(true);
            Object hh = constructor.newInstance();
            Method method = hhClass.getDeclaredMethod("hh");
            method.setAccessible(true);
            method.invoke(hh);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}