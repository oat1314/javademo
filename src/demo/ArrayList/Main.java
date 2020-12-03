package demo.ArrayList;

/**
 * @program: javademo
 * @author: ouguoxin
 * @create: 2020-12-02 15:45
 **/

public class Main {

    public static void main(String[] args) {
        MyArrayList list = new MyArrayList();

        for (int i = 0; i<12;i++) {
            list.add(""+i);
        }

        for (int i = 0; i<list.size();i++) {
            System.out.println(list.get(i));
        }
    }


}

