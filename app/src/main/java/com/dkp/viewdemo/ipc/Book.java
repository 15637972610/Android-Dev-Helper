package com.dkp.viewdemo.ipc;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2019/5/22.
 */

public class Book implements Parcelable {
    public int bookId;
    public String bookName;

    public Book(int bookId, String bookName ){
        this.bookId = bookId;
        this.bookName = bookName;
    }
    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
    //内容描述
    @Override
    public int describeContents() {
        return 0;
    }
    //1.序列化
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(bookId);
        dest.writeString(bookName);

    }
    //反序列化
    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel source) {
            return new Book(source);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public Book(Parcel in){
        bookId = in.readInt();
        bookName = in.readString();
    }


}
