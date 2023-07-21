package com.gen.eChannel.verification.services;

import com.gen.eChannel.verification.dto.OutComeDto;

import java.util.List;

public interface OutComeService {

    OutComeDto CreateOutCome(OutComeDto outComeDto);

    List<OutComeDto> getAllOutComes();

    OutComeDto getOutComesById(Long outComeId);


}
