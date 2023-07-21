package com.gen.eChannel.verification.services;

import com.gen.eChannel.verification.dto.EventSourceDto;
import com.gen.eChannel.verification.dto.EventSourceEchannelVerificationDto;
import com.gen.eChannel.verification.dto.EventSourceStatusDto;

import java.util.ArrayList;
import java.util.List;

public interface EventSourceService {

    EventSourceDto createEventSource(EventSourceDto eventSourceDto, Long outComeId, String statusName, Long userId);

    EventSourceDto getEventSourceById(Long eventSourceId);

    EventSourceDto updateEventSources(EventSourceDto eventSourceDto, Long userId, Long outComeId, Long eventSourceId, String statusName);

    List<EventSourceDto> getAlleChannelData();

    List<EventSourceEchannelVerificationDto> getAllChannelDataByUnAssignedStatus(String statusName);

    List<EventSourceDto> getAssignedEvents(String statusName,Long userId);

    //void assignRequestsToCurrentUser(List<Long> eventSourceId, Long userId, Long statusId);

    EventSourceStatusDto getEventSourceStatusCount();

    void assignRequestsToCurrentUsers(List<Long> eventSourceId, Long userId, String statusName);

}
