package demo.ArrayList;

import java.io.Serializable;

/**
 * @program: javademo
 * @author: ouguoxin
 * @create: 2020-12-02 15:13
 **/

public class MyArrayList implements Serializable {

    //使用这个字段，来判断当前集合类是否被并发修改，即迭代器并发修改的fail-fast机制
    private transient int modCount = 0;

    //第一次扩容的容量
    private static final int DEFAULT_CAPACITY = 10;


    //用于初始化空的list
    private static final Object[] EMPTY_ELEMENT_DATA = {};


    //实际存储的元素
    transient Object[] elementData;


    //实际list集合大小，从0开始
    private int size;


    public MyArrayList(){

        this.elementData = EMPTY_ELEMENT_DATA;
    }



    public MyArrayList(int initialCapcity){

        if(initialCapcity>0){
            this.elementData = new Object[initialCapcity];

        }else if(initialCapcity == 0){
            this.elementData = EMPTY_ELEMENT_DATA;

        }else {
            throw new IllegalArgumentException("参数异常");
        }

    }


    public boolean add(Object e){

        //判断容量
        ensureCapacityInternal(size+1);

        //使用下标赋值，尾部插入
        elementData[size++] = e;

        return true;
    }


    //计算容量+确保容量
    private void ensureCapacityInternal(int minCapacity){

        //用于并发判断
        modCount++;

        //如果是初次扩容，则使用默认的容量
        if(elementData == EMPTY_ELEMENT_DATA){
            minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
        }

        //是否需要扩容，需要的最少容量大于现在数组的长度则要扩容
        if(minCapacity - elementData.length > 0){
            int oldCapacity = elementData.length;

            int newCapacity = oldCapacity + (oldCapacity>>1);

            //如果新容量 < 最小容量， 则讲最新的容量赋值给新的容量
            if(newCapacity - minCapacity < 0){
                newCapacity = minCapacity;
            }

            //创建新数组
            Object[] objects = new Object[newCapacity];

            //将旧的数组复制到新的数组里面
            System.arraycopy(elementData,0, objects,0,elementData.length);

            //修改引用
            elementData = objects;

        }

    }


    /**
     * 通过下标获取对象
     * @param index
     * @return
     */
    public Object get(int index){
        rangeCheck(index);
        return elementData[index];

    }

    private void rangeCheck(int index){
        if(index > size || size < 0){
            throw  new IndexOutOfBoundsException("数组越界");
        }
    }


    /**
     * 判断对象所在的位置
     * @param o
     * @return
     */
    public int indexOf(Object o){

        if(o == null){
            for(int i=0; i < size; i++){
                if(elementData[i] == null){
                    return i;
                }
            }
        }else {
            for(int i=0; i<size; i++){
                if(o.equals(elementData[i])){
                    return i;
                }
            }
        }

        return -1;
    }



    public Object set(int index, Object obj){
        rangeCheck(index);
        Object oldValue = elementData[index];
        elementData[index] = obj;
        return oldValue;
    }


    /**
     * 根据索引删除元素
     * @param index
     * @return
     */
    public Object remove(int index){

        rangeCheck(index);

        //用于并发判断
        modCount++;

        Object oldValue = elementData[index];

        //计算要删除的位置后面有几个元素
        int numMoved = size - index -1;

        if(numMoved>0){
            System.arraycopy(elementData,index+1,elementData,index,numMoved);
        }

        //将多出的位置为空，没有引用对象，垃圾收集器可以回收，如果不为空，将会保存一个引用，可能会造成内存泄露
        elementData[--size] = null;

        return oldValue;
    }


    //获取数组实际大小
    public int size(){
        return this.size;
    }
}

