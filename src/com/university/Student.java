package com.university;

/**
 * @author محمد ساري، محمد عبدالسلام، اسامة القاسمي ، محمد زيد ، علي عبدالله المؤيد 
 * @version 1.0
 * 
 * يمثل هذا الكلاس العقدة (Node) في القائمة المرتبطة المفردة (Singly Linked List).
 * كل عقدة تحتوي على بيانات الطالب ومؤشر للعقدة التالية.
 */
public class Student {
    // بيانات الطالب
    int id;
    String name;
    String major;
    
    // المؤشر للعقدة التالية
    Student next; 

    /**
     * مُنشئ لإنشاء عقدة طالب جديدة.
     * @param id رقم الطالب الجامعي
     * @param name اسم الطالب
     * @param major التخصص
     */
    public Student(int id, String name, String major) {
        this.id = id;
        this.name = name;
        this.major = major;
        this.next = null;
    }

    /**
     * دالة لعرض معلومات الطالب.
     * @return سلسلة نصية تحتوي على بيانات الطالب.
     */
    @Override
    public String toString() {
        return "ID: " + id + ", الاسم: " + name + ", التخصص: " + major;
    }
}
