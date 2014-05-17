package com.zorbit.utils.eImporter.vo;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;

/**
 * sheet数据集
 * @since 2013-6-26 上午10:04:24
 * @author biller zou
 */
public class SheetList implements List<RowMap> {
    
    private List<RowMap> list = new Vector<RowMap>();
    
    private String name;
    
    /**
     * sheet id
     */
    private String id;
    
    
    /**
     * 获取 sheet id
     * @return id
     */
    public String getId() {
        return id;
    }

    
    /**
     * 设置 sheet id
     * @param id sheet id
     */
    public void setId(String id) {
        this.id = id;
    }

    private int index;
    
    /** 对应的java类 */
    private String classz;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getIndex() {
        return index;
    }
    
    public void setIndex(int index) {
        this.index = index;
    }
    
    public RowMap get(Integer index) {
        return list.get(index);
    }
    
    public List<RowMap> getList() {
        return list;
    }
    
    public void setList(List<RowMap> list) {
        this.list = list;
    }
    
    public String getClassz() {
        return classz;
    }
    
    public void setClassz(String classz) {
        this.classz = classz;
    }
    
    @Override
    public int size() {
        return list.size();
    }
    
    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }
    
    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }
    
    @Override
    public Iterator<RowMap> iterator() {
        return list.iterator();
    }
    
    @Override
    public Object[] toArray() {
        return list.toArray();
    }
    
    @Override
    public <T> T[] toArray(T[] a) {
        return list.toArray(a);
    }
    
    @Override
    public boolean add(RowMap e) {
        return list.add(e);
    }
    
    @Override
    public boolean remove(Object o) {
        return list.remove(o);
    }
    
    @Override
    public boolean containsAll(Collection<?> c) {
        return list.containsAll(c);
    }
    
    @Override
    public boolean addAll(Collection<? extends RowMap> c) {
        return list.addAll(c);
    }
    
    @Override
    public boolean addAll(int index, Collection<? extends RowMap> c) {
        return list.addAll(index, c);
    }
    
    @Override
    public boolean removeAll(Collection<?> c) {
        return list.removeAll(c);
    }
    
    @Override
    public boolean retainAll(Collection<?> c) {
        return list.retainAll(c);
    }
    
    @Override
    public void clear() {
        list.clear();
    }
    
    @Override
    public RowMap get(int index) {
        return list.get(index);
    }
    
    @Override
    public RowMap set(int index, RowMap element) {
        return list.set(index, element);
    }
    
    @Override
    public void add(int index, RowMap element) {
        list.add(index, element);
    }
    
    @Override
    public RowMap remove(int index) {
        return list.remove(index);
    }
    
    @Override
    public int indexOf(Object o) {
        return list.indexOf(o);
    }
    
    @Override
    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }
    
    @Override
    public ListIterator<RowMap> listIterator() {
        return list.listIterator();
    }
    
    @Override
    public ListIterator<RowMap> listIterator(int index) {
        return list.listIterator(index);
    }
    
    @Override
    public List<RowMap> subList(int fromIndex, int toIndex) {
        return list.subList(fromIndex, toIndex);
    }
    
}
