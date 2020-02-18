package com.techprimers.db.resource;

import com.techprimers.db.model.Users;
import com.techprimers.db.repository.AadharRepository;
import com.techprimers.db.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/rest/users")
public class UsersResource {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    AadharRepository aadharRepository;

    @GetMapping(value = "/all")
    public List<Users> getAll() {
        return usersRepository.findAll();
    }

    @PostMapping(value = "/load")
    public Users persist(@RequestBody final Users users) {
        Users users1 = users;
        if(!(aadharRepository.findByaadharNumber(users.getAadharNumber()) == null)) {
            if(usersRepository.findByaadharNumber(users.getAadharNumber()) != null) {
                users1.setAadharNumber("Already registered");
            } else {
                usersRepository.save(users);
            }
        } else {
            users1 = users;
            users1.setAadharNumber("Not found");

        }
        System.out.print(users1.getAadharNumber());
        return users1;
    }

    @PostMapping(value = "/auth/{uid}/{pwd}")
    public Users authenticate(@PathVariable Integer uid, @PathVariable String pwd) {
        return null;
    }


}
