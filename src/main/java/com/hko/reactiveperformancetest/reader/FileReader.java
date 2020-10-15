package com.hko.reactiveperformancetest.reader;

import com.hko.reactiveperformancetest.client.PersonWebClient;
import com.hko.reactiveperformancetest.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;

public class FileReader implements Runnable{
    private static Logger logger = LoggerFactory.getLogger(FileReader.class);

    Scanner scanner = null;
    File f = null;
    LinkedBlockingQueue<String> list = null;
    PersonWebClient client=null;

    public FileReader(String path) throws Exception {
        try {
            client = new PersonWebClient();
            f= new File(path);
            scanner=new Scanner(f);
            list = new LinkedBlockingQueue<>();
            while(scanner.hasNextLine()){
                list.add(scanner.nextLine());
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
        }
    }

    @Override
    public void run() {
        while(list.size()>0){
            String line= list.poll();
            String[] split = line.split("\\|");
            Person newPerson = new Person(split[0],split[1]);
            client.createPerson(newPerson);
        }

    }
}
