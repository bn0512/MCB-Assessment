package com.gen.eChannel.verification.services.impl;

import com.gen.eChannel.verification.dto.OutComeDto;
import com.gen.eChannel.verification.entities.OutCome;
import com.gen.eChannel.verification.entities.User;
import com.gen.eChannel.verification.exceptions.ResourceNotFoundException;
import com.gen.eChannel.verification.repositories.OutComeRepo;
import com.gen.eChannel.verification.repositories.UserRepo;
import com.gen.eChannel.verification.services.OutComeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OutComeServiceImpl implements OutComeService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OutComeRepo outComeRepo;

    @Autowired
    private UserRepo userRepo;

    public OutComeServiceImpl(OutComeRepo outComeRepo, ModelMapper modelMapper) {
        this.outComeRepo = outComeRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public OutComeDto CreateOutCome(OutComeDto outComeDto) {

        OutCome outCome = modelMapper.map(outComeDto, OutCome.class);

        OutCome savedOutComes = outComeRepo.save(outCome);
        return  modelMapper.map(savedOutComes, OutComeDto.class);
    }

    @Override
    public List<OutComeDto> getAllOutComes() {

        List<OutCome> outComes = outComeRepo.findAll();
        List<OutComeDto> outComeDtoList = outComes.stream().map(outCome ->
                modelMapper.map(outCome, OutComeDto.class)).collect(Collectors.toList());
        return outComeDtoList;
    }

    @Override
    public OutComeDto getOutComesById(Long outComeId) {

        OutCome outCome = outComeRepo.findById(outComeId).orElseThrow(() -> new ResourceNotFoundException("OutCome", " Id ", outComeId));

        return modelMapper.map(outCome, OutComeDto.class);
    }
}
