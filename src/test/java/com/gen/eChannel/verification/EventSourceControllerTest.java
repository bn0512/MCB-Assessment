/*
package com.gen.eChannel.verification;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gen.eChannel.verification.dto.AssignRequestDto;
import com.gen.eChannel.verification.dto.EventSourceDto;
import com.gen.eChannel.verification.dto.EventSourceEchannelVerificationDto;
import com.gen.eChannel.verification.dto.EventSourceStatusDto;
import com.gen.eChannel.verification.services.EventSourceService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EventSourceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventSourceService eventSourceService;

    private EventSourceDto eventSourceDto;

    private EventSourceEchannelVerificationDto eventSourceEchannelVerificationDto;

    @BeforeEach
    void setUp() {
        this.eventSourceDto = new EventSourceDto();
        // add properties to the eventSourceDto

        Mockito.when(eventSourceService.createEventSource(Mockito.any(EventSourceDto.class), Mockito.anyLong(),
                Mockito.anyString(), Mockito.anyLong()))
                .thenReturn(eventSourceDto);
        Mockito.when(eventSourceService.getEventSourceById(Mockito.anyLong())).thenReturn(eventSourceDto);
        Mockito.when(eventSourceService.getAlleChannelData()).thenReturn(Collections.singletonList(eventSourceDto));
        Mockito.when(eventSourceService.getAllChannelDataByUnAssignedStatus(Mockito.anyString()))
                .thenReturn(Collections.singletonList(eventSourceEchannelVerificationDto));
    }

    @Test
    public void shouldCreateEventSource() throws Exception {

        EventSourceDto eventSourceDto = new EventSourceDto();
        eventSourceDto.setSourceBu("Sample Business Unit");
        eventSourceDto.setPriority("High");
        eventSourceDto.setBusinessKey("GD541");
        eventSourceDto.setDcpReference("Sample DCP Reference");
        eventSourceDto.setAccountName("Sample Account Name");
        eventSourceDto.setTransactionCurrency("USD");
        eventSourceDto.setLockedBy("John Doe");
        eventSourceDto.setDebitAccountNumber("Sample Debit Account Number");
        eventSourceDto.setAccountCurrency("USD");
        eventSourceDto.setBeneficiaryName("Sample Beneficiary Name");
        eventSourceDto.setCustInfoMkr("Sample Customer Info Marker");
        eventSourceDto.setAccountInfoMkr("Sample Account Info Marker");
        eventSourceDto.setExtension("Sample Extension");
        eventSourceDto.setContactPerson("Sample Contact Person");
        eventSourceDto.setTransactionAmount(3255.0);
        eventSourceDto.setCustomerCalledOn("2023-07-21");
        eventSourceDto.setComment("Sample Comment");

        String eventSourceDtoJson = new ObjectMapper().writeValueAsString(eventSourceDto);

        ResultActions response = mockMvc.perform(post("/outCome/{outComeId}/user/{userId}/eventSource/status/{statusName}",
                1, 2, "status")
                .contentType(MediaType.APPLICATION_JSON)
                .content(eventSourceDtoJson));

        response.andExpect(status().isCreated())
                .andExpect(content().json(eventSourceDtoJson));
    }

    @Test
    public void shouldGetEventSourceById() throws Exception {
        String eventSourceDtoJson = new ObjectMapper().writeValueAsString(eventSourceDto);

        ResultActions response = mockMvc.perform(get(
                "/eventSource/{eventSourceId}", 1));

        response.andExpect(status().isOk())
                .andExpect(content().json(eventSourceDtoJson));
    }

    @Test
    public void shouldUpdateEventSources() throws Exception {
        EventSourceDto eventSourceDto = new EventSourceDto();
        String eventSourceDtoJson = new ObjectMapper().writeValueAsString(eventSourceDto);

        ResultActions response = mockMvc.perform(post(
                "/outCome/{outComeId}/status/{statusName}/user/{userId}/eventSource",
                1, "status", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(eventSourceDtoJson));

        response.andExpect(status().isCreated())
                .andExpect(content().json(eventSourceDtoJson));
    }

    @Test
    public void shouldGetAllChannelData() throws Exception {
        String eventSourceDtoJson = new ObjectMapper().writeValueAsString(Collections.singletonList(eventSourceDto));

        ResultActions response = mockMvc.perform(get(
                "/eventSources"));

        response.andExpect(status().isOk())
                .andExpect(content().json(eventSourceDtoJson));
    }

    @Test
    public void shouldGetAllChannelDataByUnAssignedStatus() throws Exception {
        String eventSourceEchannelVerificationDtoJson = new ObjectMapper()
                .writeValueAsString(Collections.singletonList(eventSourceEchannelVerificationDto));

        ResultActions response = mockMvc.perform(get(
                "/by-status-name/{statusName}", "status"));

        response.andExpect(status().isOk())
                .andExpect(content().json(eventSourceEchannelVerificationDtoJson));
    }

    @Test
    public void shouldAssignRequestsToCurrentUser() throws Exception {
        AssignRequestDto assignRequestDto = new AssignRequestDto();
        // add properties to the assignRequestDto
        String assignRequestDtoJson = new ObjectMapper().writeValueAsString(assignRequestDto);

        ResultActions response = mockMvc.perform(post(
                "/requests/assign/user/{userId}/status/{statusName}", 1, "status")
                .contentType(MediaType.APPLICATION_JSON)
                .content(assignRequestDtoJson));

        response.andExpect(status().isOk())
                .andExpect(content().string("Requests assigned successfully."));
    }

    @Test
    public void shouldGetEventSourceStatusCount() throws Exception {
        EventSourceStatusDto eventSourceStatusDto = new EventSourceStatusDto();
        // add properties to the eventSourceStatusDto
        String eventSourceStatusDtoJson = new ObjectMapper().writeValueAsString(eventSourceStatusDto);

        Mockito.when(eventSourceService.getEventSourceStatusCount()).thenReturn(eventSourceStatusDto);

        ResultActions response = mockMvc.perform(get(
                "/eventSourceStatus/count"));

        response.andExpect(status().isOk())
                .andExpect(content().json(eventSourceStatusDtoJson));
    }

    @Test
    public void shouldGetAssignedEvents() throws Exception {


        String eventSourceDtoJson = new ObjectMapper().writeValueAsString(Collections.singletonList(eventSourceDto));

        String statusName = "status";
        Long userId = 1L;
        Mockito.when(eventSourceService.getAssignedEvents(statusName, userId)).thenReturn(Collections.singletonList(eventSourceDto));

        ResultActions response = mockMvc.perform(get(
                "/user/{userId}/status/{statusName}", 1, "status"));

        response.andExpect(status().isOk())
                .andExpect(content().json(eventSourceDtoJson));
    }

}
*/
