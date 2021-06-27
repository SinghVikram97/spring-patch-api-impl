package com.vikram.httppatchinspring.db;

import com.vikram.httppatchinspring.model.Address;
import com.vikram.httppatchinspring.model.Person;

import java.util.ArrayList;
import java.util.List;

public class PersonDb {
    Address address1=new Address("sriniwaspuri","Delhi","India");
    Person person1=new Person("1","vikram","bedi",address1);

    Address address2=new Address("dwarka","Delhi","India");
    Person person2=new Person("2","test","test",address2);

    List<Person> db=new ArrayList<>();

    public PersonDb() {
        db.add(person1);
        db.add(person2);
    }

    public List<Person> getDb(){
        return db;
    }
}
