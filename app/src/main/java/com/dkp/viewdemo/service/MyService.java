package com.dkp.viewdemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.dkp.viewdemo.ipc.Book;
import com.dkp.viewdemo.ipc.IBookManager;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 服务端
 * Created by Administrator on 2019/5/22.
 */

public class MyService extends Service {
    private CopyOnWriteArrayList<Book> mList = new CopyOnWriteArrayList<Book>();

    @Override
    public void onCreate() {
        super.onCreate();
        mList.add(new Book(1,"三锅演绎"));
        mList.add(new Book(2,"雪山飞狐"));
        mList.add(new Book(3,"神雕侠侣"));
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private Binder mBinder = new IBookManager.Stub(){

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public List<Book> getBookList() throws RemoteException {
            return mList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mList.add(book);

        }
    };


}
