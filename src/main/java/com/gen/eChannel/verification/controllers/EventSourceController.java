package com.gen.eChannel.verification.controllers;

import com.gen.eChannel.verification.dto.AssignRequestDto;
import com.gen.eChannel.verification.dto.EventSourceDto;
import com.gen.eChannel.verification.dto.EventSourceEchannelVerificationDto;
import com.gen.eChannel.verification.dto.EventSourceStatusDto;
import com.gen.eChannel.verification.services.EventSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping
@CrossOrigin(origins = "http://localhost:4200")
public class EventSourceController {

    @Autowired
    EventSourceService eventSourceService;

    @Autowired
    private HttpSession httpSession;

    @PostMapping("/outCome/{outComeId}/user/{userId}/eventSource/status/{statusName}")
    public ResponseEntity<EventSourceDto> createEventSource(@PathVariable Long outComeId, @PathVariable String statusName, @PathVariable Long userId, @RequestBody EventSourceDto eventSourceDto) {

        EventSourceDto eventSourceDto1 = eventSourceService.createEventSource(eventSourceDto, outComeId, statusName, userId);
        return new ResponseEntity<>(eventSourceDto1, HttpStatus.CREATED);
    }


    @GetMapping("/eventSource/{eventSourceId}")
    public ResponseEntity<EventSourceDto> getEventSourceById(@PathVariable Long eventSourceId) {

        EventSourceDto eventSourceDto = eventSourceService.getEventSourceById(eventSourceId);
        return new ResponseEntity<>(eventSourceDto, HttpStatus.OK);
    }

    @PutMapping("/outCome/{outComeId}/user/{userId}/eventSource/{eventSourceId}/status/{statusName}")
    public ResponseEntity<EventSourceDto> updateEventSources(@Valid @RequestBody EventSourceDto eventSourceDto, @PathVariable Long userId, @PathVariable Long outComeId, @PathVariable Long eventSourceId, @PathVariable String statusName) {

        EventSourceDto updatedEventSource = eventSourceService.updateEventSources(eventSourceDto, userId, outComeId, eventSourceId, statusName);
        return new ResponseEntity<>(updatedEventSource, HttpStatus.CREATED);
    }

    @GetMapping("/eventSources")
    public ResponseEntity<List<EventSourceDto>> getAlleChannelData() {

        List<EventSourceDto> eventSourceDtoList1 = eventSourceService.getAlleChannelData();
        return new ResponseEntity<>(eventSourceDtoList1, HttpStatus.OK);
    }

    @GetMapping("/by-status-name/{statusName}")
    public ResponseEntity<List<EventSourceEchannelVerificationDto>> getAllChannelDataByUnAssignedStatus(@PathVariable String statusName) {

        List<EventSourceEchannelVerificationDto> eventSourceEchannelVerificationDtos = eventSourceService.getAllChannelDataByUnAssignedStatus(statusName);
        return new ResponseEntity<>(eventSourceEchannelVerificationDtos, HttpStatus.OK);

    }

    @PostMapping("/requests/assign/user/{userId}/status/{statusName}")
    public ResponseEntity<String> assignRequestsToCurrentUser(@RequestBody AssignRequestDto assignRequestDto, @PathVariable Long userId, @PathVariable String statusName) {

        // Assign the selected requests to the current user
        eventSourceService.assignRequestsToCurrentUsers(assignRequestDto.getEventSourceId(), userId, statusName);

        return ResponseEntity.ok("Requests assigned successfully.");
    }

   /* private Long getCurrentUserId() {

        // Retrieve the current user ID from the session
        Long currentUserId = (Long) httpSession.getAttribute("userId");

        // Validate and return the user ID
        if (currentUserId == null) {
            throw new IllegalStateException("Current user ID not found.");
        }
        return currentUserId;
    }*/

    @GetMapping("/eventSourceStatus/count")
    public ResponseEntity<EventSourceStatusDto> getEventSourceStatusCount() {

        EventSourceStatusDto eventSourceStatusDto = eventSourceService.getEventSourceStatusCount();

        return new ResponseEntity<>(eventSourceStatusDto, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/status/{statusName}")
    public ResponseEntity<List<EventSourceDto>> getAssignedEvents(@PathVariable Long userId, @PathVariable String statusName) {
        List<EventSourceDto> assignedEvents = eventSourceService.getAssignedEvents(statusName, userId);
        return ResponseEntity.ok(assignedEvents);
    }

}
