package com.IO;

import java.io.*;
import java.util.*;

public class Library {
    public static List<Book> list = new ArrayList<>();
    public static final File file = new File("./src/com/IO/图书信息.txt");

    public static void main(String[] args) throws IOException {
        InputStream inputStream = new FileInputStream(file);
        Scanner scanner = new Scanner(file);
        scanner.useDelimiter("\n");
        for (; scanner.hasNext(); ) {
            String[] msg = scanner.next().split("\t");
            Book book = new Book(msg[0], msg[1], msg[2], msg[3]);
            list.add(book);
        }


        boolean start = true;
        while (start) {
            Scanner input = new Scanner(System.in);
            System.out.println("-------欢迎来到图书管理系统-------");
            System.out.println("输入0:退出图书管理系统");
            System.out.println("输入1:列出所有图书");
            System.out.println("输入2:搜索图书");
            System.out.println("输入3:添加图书");
            System.out.println("输入4:删除图书");
            System.out.print("你想输入的数字为:");
            String regex = "[0-9]{1}";
            String inputnum = input.next();
            if (!inputnum.matches(regex)) {
                System.out.println("输入0-9而不是其他字符！");
            }
            switch (inputnum) {
                case "0":
                    start = false;
                    System.out.println("退出成功！");
                    break;
                case "1":
                    Iterator<Book> iterator = list.iterator();
                    for (; iterator.hasNext(); ) {
                        System.out.println(iterator.next());
                    }
                    break;
                case "2":
                    findBook();
                    break;
                case "3":
                    addBook();
                    break;
                case "4":
                    deleteBook();
                    break;
            }
        }

    }

    public static void findBook() {
        boolean search=false;
        System.out.print("输入你想搜寻的编号,书名或作者:");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();
        Iterator<Book> iterator = list.iterator();
        for (; iterator.hasNext(); ) {
            Book book;
            book = iterator.next();
            if (book.getBookName().equals(input) || book.getName().equals(input) || book.getNumber().equals(input)) {
                search=true;
                System.out.println(list.get(0));
                System.out.println(book);
            }
        }
        if(!search){
            System.out.println("未查询到该书籍！");
        }
    }

    public static void addBook() throws IOException {
        boolean test = true;
        Scanner input = new Scanner(System.in);
        System.out.println("输入需要添加书籍的编号 书名 作者 价格。\t如0011-斗破苍穹-天蚕土豆-120 \t编号不能重复且为4位数，价格必须为数，且不能为负数");
        String regex = "\\d{4}-\\w+-\\w+-\\d+";
        String msg = input.next();
        if (msg.matches(regex)) {
            String[] message = msg.split("-");
            Iterator<Book> iterator1 = list.iterator();
            Book temp;
            for (; iterator1.hasNext(); ) {
                temp = iterator1.next();
                if (temp.getNumber().equals(message[0])) {
                    System.out.println("与原有书籍编号重复！添加失败！");
                    test = false;
                    break;
                }
            }
            if (test) {
                list.add(new Book(message[0], message[1], message[2], message[3]));
                System.out.println("添加成功！");
                refresh();
            }
        } else {
            System.out.println("输入格式错误，添加失败！");
        }


    }

    public static void deleteBook() throws IOException {
        boolean test = true;
        System.out.println("输入想要删除的书籍编号");
        Scanner scanner = new Scanner(System.in);
        String regex = "\\d{1,}";
        String input = scanner.next();
        if (input.matches(regex)) {
            Book book;
            Iterator<Book> iterator = list.iterator();
            for (; iterator.hasNext(); ) {
                book = iterator.next();
                if (book.getNumber().equals(input)) {
                    iterator.remove();
                    test = false;
                    System.out.println("删除成功！");
                    refresh();
                    break;
                }
            }
            if (test) {
                System.out.println("为查询到该书籍编号,删除失败！");
            }
        } else {
            System.out.println("输入编号格式错误，编号均为数字！");
        }
    }
    public static void refresh() throws IOException {
        Iterator<Book> iterator = list.iterator();
        Writer writer = new FileWriter(file);
        for (; iterator.hasNext(); ) {
            Book msginput = iterator.next();
            writer.write(msginput.getNumber() + "\t" + msginput.getBookName() + "\t" + msginput.getName() + "\t" + msginput.getPrice() + "\n");
        }
        writer.close();
    }
}

class Book {
    private String number;
    private String bookName;
    private String name;
    private String price;
    private Book nextBook;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Book(String number, String bookName, String name, String price) {
        this.number = number;
        this.bookName = bookName;
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return this.number + "\t" + this.bookName + "\t" + this.name + "\t" + this.price;
    }
//    public void Next(Book  book){
//        this.nextBook=book;
//    }
}
