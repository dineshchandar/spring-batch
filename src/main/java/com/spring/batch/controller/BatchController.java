package com.spring.batch.controller;

import com.spring.batch.BirthdayService.BirthdayService;
import com.spring.batch.model.Birthday;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;

@Component
@RestController
@RequestMapping("/v1/batch")
public class BatchController {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job BirthdayJob;

    @Autowired
    BirthdayService birthdayService;

    //Healthcheck
    @GetMapping("/healthcheck")
    public ResponseEntity healthCheck() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS Z");
        log.info("Healthcheck");
        return new ResponseEntity("Server Time: " + dateFormat
                .format(System.currentTimeMillis()), HttpStatus.OK);
    }

    @PostMapping("/addbirthday")
    public ResponseEntity<?> addBirthday(@Valid @RequestBody Birthday birthday) throws Exception {

        birthdayService.addBirthday(birthday);

        return(new ResponseEntity("Birthday added",HttpStatus.OK));

    }

    @RequestMapping("/invokejob")
    public void handle() throws Exception {

        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis()).toJobParameters();

        jobLauncher.run(BirthdayJob, jobParameters);
    }


}
