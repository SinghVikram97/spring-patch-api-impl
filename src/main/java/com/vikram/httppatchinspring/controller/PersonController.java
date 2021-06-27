package com.vikram.httppatchinspring.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import com.vikram.httppatchinspring.db.PersonDb;
import com.vikram.httppatchinspring.model.Address;
import com.vikram.httppatchinspring.model.Person;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    PersonDb personDb;
    public PersonController() {
        personDb=new PersonDb();
    }

    @GetMapping("/{id}")
    public Person getPerson(@PathVariable("id") int id){

       List<Person> db=personDb.getDb();
       try{
           return db.get(id-1);
       }catch (IndexOutOfBoundsException ie){
           return null;
       }
    }

    /* Need to send full person object in req, as put used to replace the state of given resource

       IMP: PUT payload must be new representation of the resource, it's not meant for partial updates.

       *If we send just the lastName in the req, all other field will be set to null, as while deserializing the req payload into java object, fields which aren't present will be set to null, and we are just overriding existing representation of the person object

     */
    @PutMapping("/{id}")
    public Person putPerson(@PathVariable("id") int id,@RequestBody Person personReqObj){

        List<Person> db=personDb.getDb();
        try{
            db.set(id-1,personReqObj);
            return db.get(id-1);
        }catch (IndexOutOfBoundsException ie){
            return null;
        }
    }

    /* Specifying only a part of Person object to update */
    @PatchMapping("/{id}")
    public Person patchPerson(@PathVariable("id") int id,@RequestBody Person personReqObj) throws JsonProcessingException, JsonPatchException {

        List<Person> db=personDb.getDb();
        Person person;
        try{
           person = db.get(id - 1);
        }catch (IndexOutOfBoundsException ie){
            return null;
        }

        ObjectMapper objectMapper=new ObjectMapper();
        // include only non-nulls otherwise missing properties will have null which means delete them (according to Json merge spec)
        // Remove the line below and see!
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        JsonNode patch=objectMapper.valueToTree(personReqObj);
        JsonNode original=objectMapper.valueToTree(person);

        JsonMergePatch jsonMergePatch=JsonMergePatch.fromJson(patch);
        JsonNode result = jsonMergePatch.apply(original);

        Person updatedPerson= objectMapper.treeToValue(result, Person.class);

        db.set(id-1,updatedPerson);
        return db.get(id-1);
    }



}
