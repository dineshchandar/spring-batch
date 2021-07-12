package com.spring.batch.BirthdayService;

import com.spring.batch.model.Birthday;
import com.spring.batch.repository.BirthdayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BirthdayServiceImpl implements BirthdayService {

    @Autowired
    BirthdayRepository birthdayRepository;


    @Override
    public void addBirthday(Birthday birthday) {

        birthdayRepository.save(birthday);

    }
}
