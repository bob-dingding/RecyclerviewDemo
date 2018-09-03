package com.hxzk.bj.demo;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * 作者：created by ${zjt} on 2018/8/29
 * 描述:itme实体类
 */
public class RecyclerViewBean implements Parcelable {

   String name;
   int age;
   String sex;

    public RecyclerViewBean(String name, int age, String sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    protected RecyclerViewBean(Parcel in) {
        name = in.readString();
        age = in.readInt();
        sex = in.readString();
    }

    /**反序列化**/
    public static final Creator<RecyclerViewBean> CREATOR = new Creator<RecyclerViewBean>() {

        //从序列化的对象中返回原始对象
        @Override
        public RecyclerViewBean createFromParcel(Parcel in) {
            return new RecyclerViewBean(in);
        }
//创建指定长度的原始对象数组
        @Override
        public RecyclerViewBean[] newArray(int size) {
            return new RecyclerViewBean[size];
        }
    };


/**内容描述接口，基本不用管**/
    @Override
    public int describeContents() {
        return 0;
    }


    /**
     * 序列化方法
     * 从Parcel中构造一个实现了Parcelable的类的实例处理
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
        dest.writeString(sex);
    }
}
