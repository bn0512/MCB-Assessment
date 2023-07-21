package com.gen.eChannel.verification.services;

import com.gen.eChannel.verification.dto.StatusDto;

import java.util.List;

public interface StatusService {

    StatusDto CreateStatus(StatusDto statusDto);

    List<StatusDto> getAllStatus();

    StatusDto getStatusById(Long statusId);


}
