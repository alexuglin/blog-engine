package com.skillbox.diplom.service;

import com.skillbox.diplom.model.api.response.InitResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GeneralService {

    @Autowired
    public  GeneralService(){

    }

    public ResponseEntity<InitResponse> getInfo(){
        return ResponseEntity.ok(new InitResponse());
    }
}
