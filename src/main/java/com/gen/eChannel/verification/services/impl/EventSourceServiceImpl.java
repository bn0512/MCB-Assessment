package com.gen.eChannel.verification.services.impl;

import com.gen.eChannel.verification.dto.EventSourceDto;
import com.gen.eChannel.verification.dto.EventSourceEchannelVerificationDto;
import com.gen.eChannel.verification.dto.EventSourceStatusDto;
import com.gen.eChannel.verification.entities.EventSource;
import com.gen.eChannel.verification.entities.OutCome;
import com.gen.eChannel.verification.entities.Status;
import com.gen.eChannel.verification.entities.User;
import com.gen.eChannel.verification.exceptions.ResourceNotFoundException;
import com.gen.eChannel.verification.repositories.EventSourceRepo;
import com.gen.eChannel.verification.repositories.OutComeRepo;
import com.gen.eChannel.verification.repositories.StatusRepo;
import com.gen.eChannel.verification.repositories.UserRepo;
import com.gen.eChannel.verification.services.EventSourceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class EventSourceServiceImpl implements EventSourceService {

    @Autowired
    private EventSourceRepo eventSourceRepo;

    @Autowired
    private StatusRepo statusRepo;

    @Autowired
    private OutComeRepo outComeRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    public EventSourceServiceImpl(EventSourceRepo eventSourceRepo, StatusRepo statusRepo, OutComeRepo outComeRepo, UserRepo userRepo, ModelMapper modelMapper) {

        this.eventSourceRepo = eventSourceRepo;
        this.statusRepo = statusRepo;
        this.outComeRepo = outComeRepo;
        this.userRepo = userRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public EventSourceDto createEventSource(EventSourceDto eventSourceDto, Long outComeId, String statusName, Long userId) {

        Optional<Status> statusOptional = statusRepo.findByName(statusName);
        if (statusOptional.isPresent()) {
            Status status = statusOptional.get();

            OutCome outCome = outComeRepo.findById(outComeId)
                    .orElseThrow(() -> new ResourceNotFoundException("OutCome", "OutCome Id", outComeId));

            User user = userRepo.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));

            EventSource eventSource = modelMapper.map(eventSourceDto, EventSource.class);
            eventSource.setOutCome(outCome);
            eventSource.setStatus(status);
            eventSource.setUser(user);

            EventSource savedEventSource = eventSourceRepo.save(eventSource);
            return modelMapper.map(savedEventSource, EventSourceDto.class);
        } else {
            throw new ResourceNotFoundException("Status not found");
        }
    }


    @Override
    public EventSourceDto getEventSourceById(Long eventSourceId) {

        EventSource eventSource = eventSourceRepo.findById(eventSourceId)
                .orElseThrow(() -> new ResourceNotFoundException("EventSource", "EventSource Id", eventSourceId));

        return modelMapper.map(eventSource, EventSourceDto.class);
    }


    @Override
    public EventSourceDto updateEventSources(EventSourceDto eventSourceDto, Long userId, Long outComeId, Long eventSourceId, String statusName) {

        Optional<EventSource> eventSourceOptional = eventSourceRepo.findById(eventSourceId);
        if (eventSourceOptional.isPresent()) {
            EventSource eventSource = eventSourceOptional.get();
            OutCome outCome = outComeRepo.findById(outComeId)
                    .orElseThrow(() -> new ResourceNotFoundException("OutCome", "OutCome Id", outComeId));

            User user = userRepo.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));

            Optional<Status> statusOptional = statusRepo.findByName(statusName);
            if (statusOptional.isPresent()) {
                Status status = statusOptional.get();
                eventSource.setStatus(status);
            } else {
                throw new ResourceNotFoundException("Status not found");
            }

            // Update the properties of the existing eventSource entity with the new values
            eventSource.setBusinessKey(eventSourceDto.getBusinessKey());
            eventSource.setPriority(eventSourceDto.getPriority());
            eventSource.setSourceBu(eventSourceDto.getSourceBu());
            eventSource.setDcpReference(eventSourceDto.getDcpReference());
            eventSource.setAccountName(eventSourceDto.getAccountName());
            eventSource.setTransactionCurrency(eventSourceDto.getTransactionCurrency());
            eventSource.setLockedBy(eventSourceDto.getLockedBy());
            eventSource.setDebitAccountNumber(eventSourceDto.getDebitAccountNumber());
            eventSource.setAccountCurrency(eventSourceDto.getAccountCurrency());
            eventSource.setBeneficiaryName(eventSourceDto.getBeneficiaryName());
            eventSource.setCustInfoMkr(eventSourceDto.getCustInfoMkr());
            eventSource.setAccountInfoMkr(eventSourceDto.getAccountInfoMkr());
            eventSource.setOutCome(outCome);
            eventSource.setExtension(eventSourceDto.getExtension());
            eventSource.setContactPerson(eventSourceDto.getContactPerson());
            eventSource.setTransactionAmount(eventSourceDto.getTransactionAmount());
            eventSource.setCustomerCalledOn(eventSourceDto.getCustomerCalledOn());
            eventSource.setComment(eventSourceDto.getComment());
            eventSource.setUser(user);
            LocalDateTime now = LocalDateTime.now();
            eventSource.setUpdatedOn(now);

            // Save the updated eventSource entity back to the database
            EventSource updatedEventSource = eventSourceRepo.save(eventSource);
            return modelMapper.map(updatedEventSource, EventSourceDto.class);
        }

        throw new ResourceNotFoundException("EventSource", "EventSource Id", eventSourceId);

    }

    @Override
    public List<EventSourceDto> getAlleChannelData() {

        List<EventSource> eventSources =eventSourceRepo.findAll();
        List<EventSourceDto> eventSourceDtoList = eventSources.stream().map(eventSource ->
                modelMapper.map(eventSource, EventSourceDto.class)).collect(Collectors.toList());
        return eventSourceDtoList;

    }

    @Override
    public List<EventSourceEchannelVerificationDto> getAllChannelDataByUnAssignedStatus(String statusName) {

        List<EventSource> eventSourceList = eventSourceRepo.findByStatusName(statusName);
        List<EventSourceEchannelVerificationDto> eventSourceEchannelVerificationDtos = new ArrayList<>();

        for (EventSource eventSource : eventSourceList) {
            EventSourceEchannelVerificationDto eventSourceEchannelVerificationDto = new EventSourceEchannelVerificationDto();
            eventSourceEchannelVerificationDto.setId(eventSource.getId());
            eventSourceEchannelVerificationDto.setCreatedOn(eventSource.getCreatedOn());
            eventSourceEchannelVerificationDto.setPriority(eventSource.getPriority());
            eventSourceEchannelVerificationDto.setSourceBu(eventSource.getSourceBu());
            eventSourceEchannelVerificationDto.setBusinessKey(eventSource.getBusinessKey());
            eventSourceEchannelVerificationDto.setDcpReference(eventSource.getDcpReference());
            eventSourceEchannelVerificationDto.setAccountName(eventSource.getAccountName());
            eventSourceEchannelVerificationDto.setTransactionCurrency(eventSource.getTransactionCurrency());
            eventSourceEchannelVerificationDto.setTransactionAmount(eventSource.getTransactionAmount());
            eventSourceEchannelVerificationDto.setLockedBy(eventSource.getLockedBy());

            eventSourceEchannelVerificationDtos.add(eventSourceEchannelVerificationDto);
        }

        return eventSourceEchannelVerificationDtos;
    }

    @Override
    public List<EventSourceDto> getAssignedEvents(String statusName, Long userId) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));
        Optional<Status> statusOptional = statusRepo.findByName(statusName);
        if (statusOptional.isPresent()) {
            Status status = statusOptional.get();
            List<EventSource> eventSources = eventSourceRepo.findByStatus(status);

            return eventSources.stream()
                    .map(eventSource -> {
                        EventSourceDto dto = modelMapper.map(eventSource, EventSourceDto.class);
                        dto.setUserName(user.getUserName());
                        return dto;
                    })
                    .collect(Collectors.toList());
        } else {
            // Return an empty list or handle the case when the status is not found
            return new ArrayList<>();
        }
    }

   /* @Override
    public List<EventSourceDto> getAssignedEvents() {

        List<EventSource> selectRequests = eventSourceRepo.findByUserIsNotNull();
        List<EventSourceDto> eventSourceDtoList = new ArrayList<>();


        for (EventSource request : selectRequests) {

            Status assignedStatus = statusRepo.findByName("Assign")
                    .orElseThrow(() -> new ResourceNotFoundException("Assign Not Found"));
           request.setStatus(assignedStatus);
            eventSourceRepo.save(request);

            EventSourceDto eventSourceDto = new EventSourceDto();
            eventSourceDto.setId(request.getId());
            eventSourceDto.setCreatedOn(request.getCreatedOn());
            eventSourceDto.setPriority(request.getPriority());
            eventSourceDto.setSourceBu(request.getSourceBu());
            eventSourceDto.setBusinessKey(request.getBusinessKey());
            eventSourceDto.setDcpReference(request.getDcpReference());
            eventSourceDto.setAccountName(request.getAccountName());
            eventSourceDto.setTransactionCurrency(request.getTransactionCurrency());
            eventSourceDto.setTransactionAmount(request.getTransactionAmount());
            eventSourceDto.setLockedBy(request.getLockedBy());

            eventSourceDtoList.add(eventSourceDto);
        }
        return eventSourceDtoList;
    }*/

        @Override
        public EventSourceStatusDto getEventSourceStatusCount () {

            long countEventSourceUnassigned = eventSourceRepo.countByStatusName("Unassigned");

            long countEventSourceProceed = eventSourceRepo.countByStatusName("Proceed");

            long countEventSourceReject = eventSourceRepo.countByStatusName("Reject");

            EventSourceStatusDto eventSourceStatusDto = new EventSourceStatusDto();
            eventSourceStatusDto.setNotYetCalled(countEventSourceUnassigned);
            eventSourceStatusDto.setCallBackSuccessful(countEventSourceProceed);
            eventSourceStatusDto.setCallBackNotSuccessful(countEventSourceReject);
            return eventSourceStatusDto;
        }

    @Override
    public void assignRequestsToCurrentUsers(List<Long> eventSourceIds, Long userId, String statusName) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));

        Optional<Status> statusOptional = statusRepo.findByName(statusName);
        if (statusOptional.isPresent()) {
            Status status = statusOptional.get();

        }

        eventSourceIds.forEach(eventSourceId->{

            EventSource eventSource = eventSourceRepo.findById(eventSourceId).get();

            eventSource.setUser(user);
            eventSource.setStatus(statusOptional.get());

            eventSourceRepo.save(eventSource);
        });


    }

}
