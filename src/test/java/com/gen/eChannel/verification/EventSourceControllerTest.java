
package com.gen.eChannel.verification;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gen.eChannel.verification.dto.*;
import com.gen.eChannel.verification.services.EventSourceService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EventSourceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventSourceService eventSourceService;

    private EventSourceDto eventSourceDto;

    @Autowired
    private ObjectMapper objectMapper;

    private EventSourceEchannelVerificationDto eventSourceEchannelVerificationDto;

    @Test
    @DisplayName("Create Event Source Test")
    public void shouldCreateEventSource() throws Exception {
        // given - precondition or setup
        EventSourceDto eventSourceDto = new EventSourceDto();

        long outComeId = 2L;

        // when - action and behaviour that we are going to test
        when(eventSourceService.createEventSource(any(EventSourceDto.class), anyLong(), anyString(), anyLong()))
                .thenReturn(eventSourceDto);

        // then - verify the result and output using assert statements
        mockMvc.perform(post("/outCome/{outComeId}/user/1/eventSource/status/{statusName}", outComeId, "Unassigned")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventSourceDto)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(eventSourceDto)));
    }

    @Test
    @DisplayName("Get Event Source By Id Test")
    public void shouldGetEventSourceById() throws Exception {
        // given - precondition or setup
        EventSourceDto eventSourceDto = new EventSourceDto();

        // when - action and behaviour that we are going to test
        when(eventSourceService.getEventSourceById(anyLong())).thenReturn(eventSourceDto);

        // then - verify the result and output using assert statements
        mockMvc.perform(get("/eventSource/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(eventSourceDto)));
    }

    @Test
    @DisplayName("Update Event Sources Test")
    public void shouldUpdateEventSources() throws Exception {
        // given - precondition or setup
        EventSourceDto eventSourceDto = new EventSourceDto();
        eventSourceDto.setBusinessKey("sampleBusinessKey");
        eventSourceDto.setPriority("samplePriority");
        eventSourceDto.setSourceBu("sampleSourceBu");
        eventSourceDto.setDcpReference("dcpReference");
        eventSourceDto.setAccountName("Account Name");
        eventSourceDto.setLockedBy("lockedBy");
        eventSourceDto.setAccountCurrency("account currency");
        eventSourceDto.setBeneficiaryName("benificiaryName");
        eventSourceDto.setCustInfoMkr("custInfo");
        eventSourceDto.setAccountInfoMkr("AccInfo");
        OutComeDto outComeDto = new OutComeDto();
        eventSourceDto.setOutCome(outComeDto);
        eventSourceDto.setExtension("eventExtension");
        eventSourceDto.setContactPerson("person");
        eventSourceDto.setCustomerCalledOn("called on");
        eventSourceDto.setComment("comment");
        eventSourceDto.setTransactionAmount(100.0);
        eventSourceDto.setTransactionCurrency("sampleTransactionCurrency");
        eventSourceDto.setDebitAccountNumber("sampleDebitAccountNumber");
        StatusDto status = new StatusDto();
        eventSourceDto.setStatus(status);
        UserDto user = new UserDto();
        eventSourceDto.setUser(user);
        eventSourceDto.setUpdatedOn(LocalDateTime.now());

        // when - action and behaviour that we are going to test
        when(eventSourceService.updateEventSources(any(EventSourceDto.class), anyLong(), anyLong(), anyLong(),
                anyString())).thenReturn(eventSourceDto);

        // then - verify the result and output using assert statements
        mockMvc.perform(put("/outCome/2/user/1/eventSource/3/status/{statusName}", outComeDto, "Proceed")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventSourceDto)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(eventSourceDto)));
    }

    @Test
    @DisplayName("Get All eChannel Verification")
    public void shouldGetAllChannelData() throws Exception {
        List<EventSourceDto> eventSourceDtos = new ArrayList<>();

        EventSourceDto eventSourceDto = new EventSourceDto();
        eventSourceDto.setId(1L);
        eventSourceDto.setName("Test Event Source");

        eventSourceDtos.add(eventSourceDto);

        when(eventSourceService.getAlleChannelData()).thenReturn(eventSourceDtos);
        mockMvc.perform(get("/eventSources"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(eventSourceDtos)));
    }

    @Test
    @DisplayName("Get All eChannel data by Unassigned Status")
    public void shouldGetAllChannelDataByUnAssignedStatus() throws Exception {
        List<EventSourceDto> eventSourceDtos = new ArrayList<>();

        when(eventSourceService.getAlleChannelData()).thenReturn(eventSourceDtos);
        mockMvc.perform(get("/by-status-name/Unassigned"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(eventSourceDtos)));
    }

    @Test
    @DisplayName("Open request to Current User")
    public void shouldAssignRequestsToCurrentUser() throws Exception {
        AssignRequestDto assignRequestDto = new AssignRequestDto();
        assignRequestDto.setEventSourceId(Arrays.asList(1L, 2L));
        // add properties to the assignRequestDto
        doNothing().when(eventSourceService).assignRequestsToCurrentUsers(anyList(), anyLong(), anyString());

        ResultActions response = mockMvc.perform(post(
                "/requests/assign/user/1/status/{statusName}", 1, "status")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(assignRequestDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Requests assigned successfully."));
    }

    @Test
    @DisplayName("Event Source Count")
    public void shouldGetEventSourceStatusCount() throws Exception {
        EventSourceStatusDto eventSourceStatusDto = new EventSourceStatusDto();
        // add properties to the eventSourceStatusDto

        when(eventSourceService.getEventSourceStatusCount()).thenReturn(eventSourceStatusDto);

        mockMvc.perform(get("/eventSourceStatus/count"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(eventSourceStatusDto)));
    }

    @Test
    @DisplayName("Get All Assigned Events")
    public void shouldGetAssignedEvents() throws Exception {


        List<EventSourceDto> eventSourceDtos = new ArrayList<>();

        when(eventSourceService.getAssignedEvents(anyString(), anyLong())).thenReturn(eventSourceDtos);

        ResultActions response = mockMvc.perform(get(
                "/user/{userId}/status/{statusName}", 1, "status"));

        response.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(eventSourceDtos)));
    }

}

