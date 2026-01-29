package com.university;

/**
 * @author محمد ساري، محمد عبدالسلام، اسامة القاسمي
 * @version 1.0
 * 
 * يمثل هذا الكلاس هيكل البيانات الأساسي للمشروع: القائمة المرتبطة المفردة (Singly Linked List).
 * يحتوي على الدوال الأساسية المطلوبة (Insert, Display, Search/Update, Delete).
 */
public class StudentList {
    // مؤشر إلى العقدة الأولى في القائمة (رأس القائمة)
    private Student head;

    /**
     * مُنشئ لإنشاء قائمة طلاب فارغة.
     */
    public StudentList() {
        this.head = null;
    }

    // -----------------------------------------------------------------
    // 1. تنفيذ دالة الإضافة (Insert)
    // -----------------------------------------------------------------
    /**
     * دالة لإضافة طالب جديد إلى نهاية القائمة المرتبطة.
     * @param id رقم الطالب الجامعي
     * @param name اسم الطالب
     * @param major التخصص
     */
    public void insert(int id, String name, String major) {
        Student newStudent = new Student(id, name, major);
        
        // التحقق من عدم تكرار رقم الطالب (ID)
        if (search(id) != null) {
            throw new IllegalArgumentException("الطالب برقم " + id + " موجود بالفعل.");
        }

        if (head == null) {
            // إذا كانت القائمة فارغة، تصبح العقدة الجديدة هي الرأس
            head = newStudent;
        } else {
            // التجوال حتى الوصول إلى آخر عقدة
            Student current = head;
            while (current.next != null) {
                current = current.next;
            }
            // ربط آخر عقدة بالعقدة الجديدة
            current.next = newStudent;
        }
    }

    // -----------------------------------------------------------------
    // 2. تنفيذ دالة العرض (Display)
    // -----------------------------------------------------------------
    /**
     * دالة لعرض جميع بيانات الطلاب المخزنة في القائمة.
     * @return قائمة نصية بجميع الطلاب، أو رسالة تفيد بأن القائمة فارغة.
     */
    public String display() {
        if (head == null) {
            return "القائمة فارغة. لا يوجد طلاب مسجلون.";
        }

        StringBuilder sb = new StringBuilder();
        Student current = head;
        int count = 1;
        
        // التجوال في القائمة وطباعة بيانات كل طالب
        while (current != null) {
            sb.append(count++).append(". ").append(current.toString()).append("\n");
            current = current.next;
        }
        return sb.toString();
    }

    // -----------------------------------------------------------------
    // 3. تنفيذ دالة البحث والتعديل (Search / Update)
    // -----------------------------------------------------------------
    /**
     * دالة للبحث عن طالب باستخدام رقم الطالب الجامعي (ID).
     * @param id رقم الطالب المراد البحث عنه.
     * @return كائن الطالب (Student) إذا تم العثور عليه، أو null إذا لم يتم العثور عليه.
     */
    public Student search(int id) {
        Student current = head;
        // التجوال في القائمة ومقارنة رقم الطالب
        while (current != null) {
            if (current.id == id) {
                return current; // تم العثور على الطالب
            }
            current = current.next;
        }
        return null; // لم يتم العثور على الطالب
    }
    
    /**
     * دالة لتعديل تخصص طالب موجود.
     * @param id رقم الطالب المراد تعديل بياناته.
     * @param newMajor التخصص الجديد.
     * @return true إذا تم التعديل بنجاح، false إذا لم يتم العثور على الطالب.
     */
    public boolean updateMajor(int id, String newMajor) {
        Student studentToUpdate = search(id);
        if (studentToUpdate != null) {
            studentToUpdate.major = newMajor;
            return true;
        }
        return false;
    }

    // -----------------------------------------------------------------
    // 4. تنفيذ دالة الحذف (Delete)
    // -----------------------------------------------------------------
    /**
     * دالة لحذف طالب من القائمة باستخدام رقم الطالب الجامعي (ID).
     * @param id رقم الطالب المراد حذفه.
     * @return true إذا تم الحذف بنجاح، false إذا لم يتم العثور على الطالب.
     */
    public boolean delete(int id) {
        if (head == null) {
            return false; // القائمة فارغة
        }

        // الحالة الأولى: حذف العقدة الرأسية (Head)
        if (head.id == id) {
            head = head.next; // نقل الرأس إلى العقدة التالية
            return true;
        }

        // الحالة الثانية: حذف عقدة في المنتصف أو النهاية
        Student current = head;
        Student previous = null;

        // التجوال حتى العثور على العقدة أو الوصول للنهاية
        while (current != null && current.id != id) {
            previous = current;
            current = current.next;
        }

        // إذا لم يتم العثور على الطالب
        if (current == null) {
            return false;
        }

        // ربط العقدة السابقة بالعقدة التالية للعقدة المحذوفة
        previous.next = current.next;
        return true;
    }
}
