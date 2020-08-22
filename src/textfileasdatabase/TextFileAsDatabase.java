package textfileasdatabase;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class TextFileAsDatabase {
    static ArrayList<String> readData(File file) throws IOException{
        // the list that we goona store our data in
        List<String> dataList = new ArrayList<>();
        FileReader reader = new FileReader (file);
        // reading the file character by character;        
        String text = "";
        int ch = reader.read();
        while(ch != -1){
            text += (char)ch;
            ch = reader.read();
        }
        // firing data to our list
        String data = "";
        for(int i = 0 ; i<text.length(); i++){
            if(text.charAt(i)!=';'){
                data+=text.charAt(i);
            }else {
                dataList.add(data);
                data = "";
            }
        }
        return (ArrayList<String>) dataList ;
    }
    static void writeData (File file,String data) throws IOException{
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(data);
            writer.close();
        }
    }
    static void updateData(ArrayList<String> list, String oldData, String newData){
        Iterator i = list.iterator();
        int index = list.indexOf(oldData);
        if(index != -1) list.set(index, newData);
        else System.out.println("Data doesn't exist");
    }
    static void updateFile (ArrayList<String> list,File file) throws IOException{
        Iterator i = list.iterator();
        String wrapedData ="";
        while (i.hasNext()){
            wrapedData += i.next()+";";
        }
        writeData(file ,wrapedData);
    }
    static void displayListOfData(ArrayList<String> list){
        if(list.isEmpty())System.out.println("database is empty !!");
        else {
            Iterator i = list.iterator();
            while(i.hasNext()){
                System.out.println(i.next());
            }
        }
    }
    public static void main(String[] args) throws IOException {
        Scanner read = new Scanner (System.in);
        Scanner sc = new Scanner (System.in);
        // specifyng the file
        String fileName = "database.txt";
        File file = new File (fileName);
        // creating the file
        if(!file.exists())file.createNewFile();
        boolean exit = false ;
        while (!exit){
            System.out.println("1. write data into database (database.txt) \n"
                         + "2. Read database \n"
                         + "3. update data \n"
                         + "0. exit");
            int choice = read.nextInt();
            switch (choice){
                case 1 :
                System.out.println("seperate your data with a ';' e.g:\n"
                        + "data1;data2;data3;data4;");
                String data = sc.nextLine();
                // writing "data" into our file
                writeData(file,data);
                break ;
                case 2 :
                // read data
                ArrayList<String> list = readData(file);
                displayListOfData(list);
                break;
                case 3:
                // update data
                System.out.print("Old data:");
                String oldData = sc.nextLine();
                System.out.print("New data:");
                String newData = sc.nextLine();
                ArrayList<String> List = readData(file);
                updateData(List, oldData, newData);
                // update file with the new list
                updateFile(List,file);
                displayListOfData(List);
                break;
                case 0 : exit = true ; 
                break ;
                default : System.out.println("invalid choice !!");
            }
        }
    }
}