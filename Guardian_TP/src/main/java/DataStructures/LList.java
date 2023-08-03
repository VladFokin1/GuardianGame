/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataStructures;

import java.util.Iterator;


/**
 *
 * @author fokin
 * @param <T>
 */
public class LList<T> implements Iterable<T>{

    @Override
    public Iterator<T> iterator() {
        return new LListIterator();
    }
    
    private class Item {
        T data;
        Item next;
        public Item(T data, Item next) {
            this.data = data;
            this.next = next;
        }
    }
    
    private Item head, tail;
    
    public void add(T data){
        if ((head != null) && (tail!= null)) {
            tail.next = new Item(data, null);
            tail =  tail.next;
        }
        else {
            head = new Item(data, null);
            tail = head;
        }
    }
    
    public void add(int index, T data) {  
        Item temp = head;
        
        if (index == 0) {
            head = new Item(data, temp);
            return;
        }
        for (int i = 0; i < index-1; i++) {
            temp = temp.next;
        }
        Item tailPart = temp.next;
        temp.next = new Item(data, tailPart);
    }
    
    public void remove(T data) {
        Item temp = new Item(null, this.head);
        while (temp.next != null) {
            if ((temp.next.data).equals(data)) {
                if(temp.next == this.head) {         //удаление первого элемента
                    this.head = this.head.next;
                } else if (temp.next == this.tail) { //удаление последнего элемента
                    this.tail = temp;
                    this.tail.next = null;
                } else {                             //удаление элемента внутри списка
                    temp.next = temp.next.next;
                }
                break;
            }
            temp = temp.next;
        }
    }
    
    public boolean isEmpty() {
        if (tail==null && head == null) return true;
        return false;
    }
    
    public T get(int index) {
        Item temp = head;
        int i = 0;
        while (i < index) {
            temp = temp.next;
            i++;
        }
        return temp.data;
    }
    
    public boolean contains(T data) {
        Item temp = head;
        while (temp != tail.next) {
            if (temp.data.equals(data)) return true;
            temp = temp.next;
        }
        return false;
    }
    
    public int indexOf(T data) {
        Item temp = head;
        int index = 0;
        while (temp != null) {
            if (temp.data.equals(data)) return index;
            temp = temp.next;
            index++;
        }
        return -1;
    }
    
    public int getSize() {
        int size = 0;
        Item temp = head;
        while(temp != null) {
            size++;
            temp = temp.next;
        }
        return size;
    }

    
    class LListIterator implements Iterator<T> {

        private Item next;
        public LListIterator() {
            this.next = head;
        }
        
        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public T next() {
            T result = next.data;
            
            if (next != null) {
                next = next.next;
            }
            return result;
        }
        
    }
}
