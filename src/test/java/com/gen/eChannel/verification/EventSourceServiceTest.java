/*
package com.gen.eChannel.verification;

import com.gen.eChannel.verification.services.impl.EventSourceServiceImpl;
import com.gen.eChannel.verification.entities.*;
import com.gen.eChannel.verification.dto.*;
import com.gen.eChannel.verification.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventSourceServiceTest {

    @Mock
    private EventSourceRepo eventSourceRepo;

    @Mock
    private UserRepo userRepo;

    @Mock
    private StatusRepo statusRepo;

    @Mock
    private OutComeRepo outComeRepo;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private EventSourceServiceImpl eventSourceService;

    private EventSourceDto eventSourceDto;
    private EventSource eventSource;
    private Status status;
    private User user;
    private OutCome outCome;

    @BeforeEach
    public void setUp() {
        status = new Status();
        status.setName("testStatus");

        user = new User();
        user.setUserName("testUser");
        user.setPassword("testPassword");

        outCome = new OutCome();
        outCome.setName("testOutCome");

        eventSourceDto = new EventSourceDto();
        eventSourceDto.setBusinessKey("businessKey1");
        eventSourceDto.setPriority("High");
        eventSourceDto.setSourceBu("BU1");
        eventSourceDto.setDcpReference("dcpRef1");
        eventSourceDto.setAccountName("accountName1");
        eventSourceDto.setTransactionCurrency("USD");
        eventSourceDto.setTransactionAmount(600.0);
        eventSourceDto.setLockedBy("User1");
        eventSourceDto.setDebitAccountNumber("1234567890");
        eventSourceDto.setAccountCurrency("USD");
        eventSourceDto.setBeneficiaryName("beneficiary1");
        eventSourceDto.setCustInfoMkr("custInfo1");
        eventSourceDto.setAccountInfoMkr("accountInfo1");
        eventSourceDto.setExtension("extension1");
        eventSourceDto.setContactPerson("contactPerson1");
        eventSourceDto.setCustomerCalledOn("custCall1");
        eventSourceDto.setComment("comment1");

        eventSource = new EventSource();
        eventSource.setBusinessKey(eventSourceDto.getBusinessKey());
        eventSource.setPriority(eventSourceDto.getPriority());
        eventSource.setSourceBu(eventSourceDto.getSourceBu());
        eventSource.setDcpReference(eventSourceDto.getDcpReference());
        eventSource.setAccountName(eventSourceDto.getAccountName());
        eventSource.setTransactionCurrency(eventSourceDto.getTransactionCurrency());
        eventSource.setTransactionAmount(eventSourceDto.getTransactionAmount());
        eventSource.setLockedBy(eventSourceDto.getLockedBy());
        eventSource.setDebitAccountNumber(eventSourceDto.getDebitAccountNumber());
        eventSource.setAccountCurrency(eventSourceDto.getAccountCurrency());
        eventSource.setBeneficiaryName(eventSourceDto.getBeneficiaryName());
        eventSource.setCustInfoMkr(eventSourceDto.getCustInfoMkr());
        eventSource.setAccountInfoMkr(eventSourceDto.getAccountInfoMkr());
        eventSource.setExtension(eventSourceDto.getExtension());
        eventSource.setContactPerson(eventSourceDto.getContactPerson());
        eventSource.setCustomerCalledOn(eventSourceDto.getCustomerCalledOn());
        eventSource.setComment(eventSourceDto.getComment());
        eventSource.setStatus(status);
        eventSource.setUser(user);
        eventSource.setOutCome(outCome);

    }

    @Test
    @DisplayName("Create Event Source test")
    public void shouldCreateEventSource() {
        // when - action and behaviour that we are going to test
        when(statusRepo.findByName(anyString())).thenReturn(Optional.of(status));
        when(userRepo.findById(anyLong())).thenReturn(Optional.of(user));
        when(outComeRepo.findById(anyLong())).thenReturn(Optional.of(outCome));
        when(eventSourceRepo.save(any(EventSource.class))).thenReturn(eventSource);

        EventSourceDto returnedEventSource = eventSourceService.createEventSource(eventSourceDto, 1L, "testStatus", 1L);
       
        // then - verify the result and output using assert statements
        assertThat(returnedEventSource).isNotNull();
        assertThat(returnedEventSource.getBusinessKey()).isEqualTo(eventSourceDto.getBusinessKey());
        assertThat(returnedEventSource.getPriority()).isEqualTo(eventSourceDto.getPriority());

        verify(statusRepo, times(1)).findByName(anyString());
        verify(userRepo, times(1)).findById(anyLong());
        verify(outComeRepo, times(1)).findById(anyLong());
        verify(eventSourceRepo, times(1)).save(any(EventSource.class));
    }

    @Test
    @DisplayName("Get Event Source by Id test")
    public void shouldGetEventSourceById() {
        // when - action and behaviour that we are going to test
        when(eventSourceRepo.findById(anyLong())).thenReturn(Optional.of(eventSource));
        EventSourceDto expectedEventSourceDto = new EventSourceDto();  
        when(modelMapper.map(any(EventSource.class), eq(EventSourceDto.class))).thenReturn(expectedEventSourceDto);

        EventSourceDto returnedEventSourceDto = eventSourceService.getEventSourceById(1L);

        // then - verify the result and output using assert statements
        assertThat(returnedEventSourceDto).isNotNull();
        assertThat(returnedEventSourceDto).isEqualTo(expectedEventSourceDto);
        verify(eventSourceRepo, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("Update Event Source test")
    public void shouldUpdateEventSource() {
        // when - action and behaviour that we are going to test
        when(eventSourceRepo.findById(anyLong())).thenReturn(Optional.of(eventSource));
        when(userRepo.findById(anyLong())).thenReturn(Optional.of(user));
        when(outComeRepo.findById(anyLong())).thenReturn(Optional.of(outCome));
        when(statusRepo.findByName(anyString())).thenReturn(Optional.of(status));

        EventSourceDto returnedEventSourceDto = eventSourceService.updateEventSources(eventSourceDto, 1L, 1L, 1L, "testStatus");
        
        // then - verify the result and output using assert statements
        assertThat(returnedEventSourceDto).isNotNull();

        verify(eventSourceRepo, times(1)).findById(anyLong());
        verify(userRepo, times(1)).findById(anyLong());
        verify(outComeRepo, times(1)).findById(anyLong());
        verify(statusRepo, times(1)).findByName(anyString());
        verify(eventSourceRepo, times(1)).save(any(EventSource.class));
    }

    @Test
    @DisplayName("Get All Event Source data test")
    public void shouldGetAllEventSourceData() {
        // when - action and behaviour that we are going to test
        when(eventSourceRepo.findAll()).thenReturn(Collections.singletonList(eventSource));
        List<EventSourceDto> returnedEventSourceDtoList = eventSourceService.getAlleChannelData();

        // then - verify the result and output using assert statements
        assertThat(returnedEventSourceDtoList).isNotNull();
        assertThat(returnedEventSourceDtoList).isNotEmpty();
        verify(eventSourceRepo, times(1)).findAll();
    }

    @Test
    @DisplayName("Get all Event Source data by unassigned status test")
    public void shouldGetAllEventSourceDataByUnassignedStatus() {
        // when - action and behaviour that we are going to test
        when(eventSourceRepo.findByStatusName(anyString())).thenReturn(Collections.singletonList(eventSource));
        List<EventSourceEchannelVerificationDto> returnedEventSourceEchannelVerificationDtoList = eventSourceService.getAllChannelDataByUnAssignedStatus("Unassigned");

        // then - verify the result and output using assert statements
        assertThat(returnedEventSourceEchannelVerificationDtoList).isNotNull();
        assertThat(returnedEventSourceEchannelVerificationDtoList).isNotEmpty();
        verify(eventSourceRepo, times(1)).findByStatusName(anyString());
    }

    @Test
    @DisplayName("Get Assigned Events test")
    public void shouldGetAssignedEvents() {
        // when - action and behaviour that we are going to test
        when(eventSourceRepo.findByUserIsNotNull()).thenReturn(Collections.singletonList(eventSource));
        when(statusRepo.findByName("Assign")).thenReturn(Optional.of(status));
        
        List<EventSourceDto> returnedEventSourceDtoList = eventSourceService.getAssignedEvents();

        // then - verify the result and output using assert statements
        assertThat(returnedEventSourceDtoList).isNotNull();
        assertThat(returnedEventSourceDtoList).isNotEmpty();
        verify(eventSourceRepo, times(1)).findByUserIsNotNull();
        verify(statusRepo, times(1)).findByName("Assign");
    }

    @Test
    @DisplayName("Get Event Source Status Count test")
    public void shouldGetEventSourceStatusCount() {
        // when - action and behaviour that we are going to test
        when(eventSourceRepo.countByStatusName("Unassigned")).thenReturn(10L);
        when(eventSourceRepo.countByStatusName("Proceed")).thenReturn(20L);
        when(eventSourceRepo.countByStatusName("Reject")).thenReturn(30L);
        
        EventSourceStatusDto returnedEventSourceStatusDto = eventSourceService.getEventSourceStatusCount();

        // then - verify the result and output using assert statements
        assertThat(returnedEventSourceStatusDto).isNotNull();
        assertThat(returnedEventSourceStatusDto.getNotYetCalled()).isEqualTo(10L);
        assertThat(returnedEventSourceStatusDto.getCallBackSuccessful()).isEqualTo(20L);
        assertThat(returnedEventSourceStatusDto.getCallBackNotSuccessful()).isEqualTo(30L);
    }

    @Test
    @DisplayName("Assign Requests to Current Users test")
    public void shouldAssignRequestsToCurrentUsers() {
        // when - action and behaviour that we are going to test
        when(userRepo.findById(anyLong())).thenReturn(Optional.of(user));
        when(statusRepo.findByName(anyString())).thenReturn(Optional.of(status));
        when(eventSourceRepo.findById(anyLong())).thenReturn(Optional.of(eventSource));

        eventSourceService.assignRequestsToCurrentUsers(Arrays.asList(1L, 2L, 3L), 1L, "Assigned");

        // then - verify the result and output using assert statements
        verify(userRepo, times(1)).findById(anyLong());
        verify(statusRepo, times(1)).findByName(anyString());
        verify(eventSourceRepo, times(3)).findById(anyLong());
        verify(eventSourceRepo, times(3)).save(any(EventSource.class));
    }

}
*/
