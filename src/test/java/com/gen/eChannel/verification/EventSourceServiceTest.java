package com.gen.eChannel.verification;

import com.gen.eChannel.verification.dto.EventSourceDto;
import com.gen.eChannel.verification.dto.EventSourceEchannelVerificationDto;
import com.gen.eChannel.verification.dto.EventSourceStatusDto;
import com.gen.eChannel.verification.entities.EventSource;
import com.gen.eChannel.verification.entities.OutCome;
import com.gen.eChannel.verification.entities.Status;
import com.gen.eChannel.verification.entities.User;
import com.gen.eChannel.verification.repositories.EventSourceRepo;
import com.gen.eChannel.verification.repositories.OutComeRepo;
import com.gen.eChannel.verification.repositories.StatusRepo;
import com.gen.eChannel.verification.repositories.UserRepo;
import com.gen.eChannel.verification.services.impl.EventSourceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest()
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EventSourceServiceTest {

    private static final Long EVENT_SOURCE_ID = 1L;
    private static final Long USER_ID = 1L;

    private static final Long OUT_COME_ID = 2L;
    private static final String STATUS_NAME = "STATUS_NAME";

    @InjectMocks
    private EventSourceServiceImpl eventSourceService;

    @Mock
    private EventSourceRepo eventSourceRepo;

    @Mock
    private StatusRepo statusRepo;

    @Mock
    private UserRepo userRepo;

    @Mock
    private OutComeRepo outComeRepo;

    @Mock
    private ModelMapper modelMapper;

    private User user;
    private Status status;
    private EventSourceDto eventSourceDto;
    private EventSource eventSource;

    private OutCome outCome;

    @BeforeEach
    public void setUp() {

        user = new User();
        user.setId(USER_ID);

        status = new Status();
        status.setName(STATUS_NAME);

        eventSource = new EventSource();
        eventSource.setId(EVENT_SOURCE_ID);

        outCome = new OutCome();
        outCome.setId(OUT_COME_ID);

        when(modelMapper.map(any(EventSourceDto.class), eq(EventSource.class))).thenReturn(eventSource);
        when(modelMapper.map(any(EventSource.class), eq(EventSourceDto.class))).thenReturn(eventSourceDto);
    }

        @Test
        @DisplayName("Create Event Source Test")
        public void shouldCreateEventSource() {

            EventSourceDto eventSourceDto = new EventSourceDto();
            eventSourceDto.setId(1L); // Set some ID for testing
            eventSourceDto.setName("Test Event Source");

            // given - precondition or setup
            when(statusRepo.findByName(STATUS_NAME)).thenReturn(Optional.of(status));
            when(userRepo.findById(USER_ID)).thenReturn(Optional.of(user));
            when(outComeRepo.findById(OUT_COME_ID)).thenReturn(Optional.of(outCome));
            when(eventSourceRepo.save(any(EventSource.class))).thenAnswer(i -> i.getArgument(0));
            when(modelMapper.map(any(EventSource.class), eq(EventSourceDto.class))).thenReturn(eventSourceDto);

            // when - action and behaviour that we are going to test
            EventSourceDto returnedDto = eventSourceService.createEventSource(eventSourceDto, OUT_COME_ID, STATUS_NAME, USER_ID);

            // then - verify the result and output using assert statements
            assertNotNull(returnedDto);
            assertEquals(eventSourceDto.getId(), returnedDto.getId());
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

        EventSourceDto eventSourceDto = new EventSourceDto();
        eventSourceDto.setId(1L);
        eventSourceDto.setBusinessKey("TestBusinessKey");

        // when - action and behaviour that we are going to test
        when(eventSourceRepo.findById(EVENT_SOURCE_ID)).thenReturn(Optional.of(eventSource));
        when(userRepo.findById(USER_ID)).thenReturn(Optional.of(user));
        when(statusRepo.findByName(STATUS_NAME)).thenReturn(Optional.of(status));
        when(outComeRepo.findById(OUT_COME_ID)).thenReturn(Optional.of(outCome));
        when(eventSourceRepo.save(any(EventSource.class))).thenAnswer(i -> i.getArgument(0));
        when(modelMapper.map(any(EventSource.class), eq(EventSourceDto.class))).thenReturn(eventSourceDto);


        EventSourceDto returnedEventSourceDto = eventSourceService.updateEventSources(eventSourceDto, 1L, 2L, EVENT_SOURCE_ID, STATUS_NAME);
        
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

        List<EventSource> eventSourceList = new ArrayList<>();
        EventSource mockEventSource = mock(EventSource.class);
        when(mockEventSource.getTransactionAmount()).thenReturn(100.00);
        eventSourceList.add(mockEventSource);

        when(eventSourceRepo.findByStatusName(anyString())).thenReturn(eventSourceList);
        List<EventSourceEchannelVerificationDto> eventSourceDtoList = eventSourceService
                .getAllChannelDataByUnAssignedStatus(STATUS_NAME);

        // then - verify the result and output using assert statements
        assertNotNull(eventSourceDtoList);
        assertEquals(1, eventSourceDtoList.size());

    }

    @Test
    @DisplayName("Get Assigned Events test")
    public void shouldGetAssignedEvents() {
        List<EventSource> selectRequests = Collections.singletonList(eventSource);
        when(eventSourceRepo.findByUserIsNotNull()).thenReturn(selectRequests);
        when(statusRepo.findByName("Assign")).thenReturn(Optional.of(status));
        when(userRepo.findById(USER_ID)).thenReturn(Optional.of(user));

        List<EventSourceDto> eventSourceDtoList = eventSourceService.getAssignedEvents("Assign", 1L);

        // then - verify the result and output using assert statements
        assertNotNull(eventSourceDtoList);
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
