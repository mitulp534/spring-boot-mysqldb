package com.techprimers.db.resource;

import com.techprimers.db.model.OneTimeAuth;
import com.techprimers.db.model.Users;
import com.techprimers.db.repository.AadharRepository;
import com.techprimers.db.repository.OtpRepository;
import com.techprimers.db.repository.UsersRepository;
import com.techprimers.db.uitls.MailClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/rest/users")
public class UsersResource {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    AadharRepository aadharRepository;

    @Autowired
    OtpRepository otpRepository;

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
    public Users authenticate(@PathVariable String uid, @PathVariable String pwd) {

        Users users = usersRepository.findByEmailAndPassword(uid,pwd);
        System.out.print(users);
        return users;
    }

    @PostMapping(value = "/otp")
    public Users sendOTP(@RequestBody final Users users) {
        MailClient mailClient = new MailClient();
        System.out.print(users.getEmail());
        OneTimeAuth oneTimeAuth = new OneTimeAuth();
        try {
            oneTimeAuth.setEmail(users.getEmail());
            oneTimeAuth.setOtp(mailClient.sendMessage(users.getEmail()));
            OneTimeAuth otpObj = otpRepository.findByEmail(users.getEmail());
            if( otpObj == null) {
                otpRepository.save(oneTimeAuth);
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    @PostMapping(value = "/validate/{otp}")
    public OneTimeAuth validateOtp(@PathVariable String otp) {

        OneTimeAuth oneTimeAuth = otpRepository.findByOtp(otp);
        if(oneTimeAuth != null) {
            otpRepository.delete(oneTimeAuth.getId());
        }
        System.out.print(oneTimeAuth);
        return oneTimeAuth;
    }
    }


