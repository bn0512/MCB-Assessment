package com.gen.eChannel.verification.controllers;

import com.gen.eChannel.verification.dto.OutComeDto;
import com.gen.eChannel.verification.services.OutComeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping
@CrossOrigin(origins = "http://localhost:4200")
public class OutComeController {

    @Autowired
    private OutComeService outComeService;

    @PostMapping("/createOutComes")
    public ResponseEntity<OutComeDto> CreateOutCome(@Valid @RequestBody OutComeDto outComeDto) {

        OutComeDto createOutComeDto = outComeService.CreateOutCome(outComeDto);
        return new ResponseEntity<>(createOutComeDto, HttpStatus.CREATED);
    }

    @GetMapping("/OutComes")
    public ResponseEntity<List<OutComeDto>> getAllOutComes() {

        List<OutComeDto> outComeDtoList = outComeService.getAllOutComes();
        return new ResponseEntity<>(outComeDtoList, HttpStatus.OK);
    }

    @GetMapping("/OutCome/{outComeId}")
    public ResponseEntity<OutComeDto> getOutComesById(@PathVariable Long outComeId) {

        OutComeDto outComeDto = outComeService.getOutComesById(outComeId);
        return new ResponseEntity<>(outComeDto, HttpStatus.OK);

    }
}
