package com.dkp.viewdemo.test;

import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import com.dkp.viewdemo.ipc.Book;

import java.util.ArrayList;
import java.util.List;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/6/13.
 * 用于分析aidl 生成的class 文件
 */

public interface IBookManager extends IInterface {
    void basicTypes(int var1, long var2, boolean var4, float var5, double var6, String var8) throws RemoteException;

    List<Book> getBookList() throws RemoteException;

    void addBook(Book var1) throws RemoteException;

    public abstract static class Stub extends Binder implements com.dkp.viewdemo.ipc.IBookManager {
        private static final String DESCRIPTOR = "com.dkp.viewdemo.ipc.IBookManager";//给binder添加一个标记，方便用的时候查找对应的binder
        static final int TRANSACTION_basicTypes = 1;
        static final int TRANSACTION_getBookList = 2;
        static final int TRANSACTION_addBook = 3;

        public Stub() {
            this.attachInterface(this, "com.dkp.viewdemo.ipc.IBookManager");
        }

        public static com.dkp.viewdemo.ipc.IBookManager asInterface(IBinder obj) {
            if(obj == null) {
                return null;
            } else {
                IInterface iin = obj.queryLocalInterface("com.dkp.viewdemo.ipc.IBookManager");
                return (IBookManager)(iin != null && iin instanceof IBookManager ?(IBookManager)iin:new com.dkp.viewdemo.ipc.IBookManager.Stub.Proxy(obj));
            }
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch(code) {
                case 1:
                    data.enforceInterface("com.dkp.viewdemo.ipc.IBookManager");
                    int _arg0 = data.readInt();
                    long _arg1 = data.readLong();
                    boolean _arg2 = 0 != data.readInt();
                    float _arg3 = data.readFloat();
                    double _arg4 = data.readDouble();
                    String _arg5 = data.readString();
                    this.basicTypes(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5);
                    reply.writeNoException();
                    return true;
                case 2:
                    data.enforceInterface("com.dkp.viewdemo.ipc.IBookManager");
                    List<Book> _result = this.getBookList();
                    reply.writeNoException();
                    reply.writeTypedList(_result);
                    return true;
                case 3:
                    data.enforceInterface("com.dkp.viewdemo.ipc.IBookManager");
                    Book _arg0;
                    if(0 != data.readInt()) {
                        _arg0 = (Book)Book.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }

                    this.addBook(_arg0);
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    reply.writeString("com.dkp.viewdemo.ipc.IBookManager");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements com.dkp.viewdemo.ipc.IBookManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "com.dkp.viewdemo.ipc.IBookManager";
            }

            public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();

                try {
                    _data.writeInterfaceToken("com.dkp.viewdemo.ipc.IBookManager");
                    _data.writeInt(anInt);
                    _data.writeLong(aLong);
                    _data.writeInt(aBoolean?1:0);
                    _data.writeFloat(aFloat);
                    _data.writeDouble(aDouble);
                    _data.writeString(aString);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }

            }

            public List<Book> getBookList() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();

                ArrayList _result;
                try {
                    _data.writeInterfaceToken("com.dkp.viewdemo.ipc.IBookManager");
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    _result = _reply.createTypedArrayList(Book.CREATOR);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }

                return _result;
            }

            public void addBook(Book book) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();

                try {
                    _data.writeInterfaceToken("com.dkp.viewdemo.ipc.IBookManager");
                    if(book != null) {
                        _data.writeInt(1);
                        book.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }

                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }

                new Handler().removeCallbacksAndMessages();

            }
        }
    }
}

