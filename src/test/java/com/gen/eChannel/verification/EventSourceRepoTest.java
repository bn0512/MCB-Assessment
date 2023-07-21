/*
package com.gen.eChannel.verification;

import com.gen.eChannel.verification.entities.EventSource;
import com.gen.eChannel.verification.entities.Status;
import com.gen.eChannel.verification.entities.User;
import com.gen.eChannel.verification.repositories.EventSourceRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class EventSourceRepoTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EventSourceRepo eventSourceRepo;

    private EventSource eventSource;

    @BeforeEach
    public void setUp() {
        User user = new User();
        user.setUserName("testuser");
        user.setPassword("password123");

        Status status = new Status();
        status.setName("testStatus");

        eventSource = new EventSource();
        eventSource.setBusinessKey("businessKey1");
        eventSource.setPriority("High");
        eventSource.setSourceBu("BU1");
        eventSource.setDcpReference("dcpRef1");
        eventSource.setAccountName("accountName1");
        eventSource.setTransactionCurrency("USD");
        eventSource.setTransactionAmount(600.0);
        eventSource.setLockedBy("User1");
        eventSource.setDebitAccountNumber("1234567890");
        eventSource.setAccountCurrency("USD");
        eventSource.setBeneficiaryName("beneficiary1");
        eventSource.setCustInfoMkr("custInfo1");
        eventSource.setAccountInfoMkr("accountInfo1");
        eventSource.setExtension("extension1");
        eventSource.setContactPerson("contactPerson1");
        eventSource.setCustomerCalledOn("custCall1");
        eventSource.setComment("comment1");
        eventSource.setStatus(status);
        eventSource.setUser(user);

        entityManager.persist(user);
        entityManager.persist(status);
        entityManager.persist(eventSource);
        entityManager.flush();
    }

    @Test
    @DisplayName("Find by status test")
    public void shouldFindByStatus() {
        // when - action and behaviour that we are going to test
        List<EventSource> found = eventSourceRepo.findByStatus(eventSource.getStatus());

        // then - verify the result and output using assert statements
        assertThat(found.size()).isGreaterThan(0);
    }

    @Test
    @DisplayName("Count by status name test")
    public void shouldCountByStatusName() {
        // when - action and behaviour that we are going to test
        long count = eventSourceRepo.countByStatusName(eventSource.getStatus().getName());

        // then - verify the result and output using assert statements
        assertThat(count).isGreaterThan(0);
    }

    @Test
    @DisplayName("Find by user is not null test")
    public void shouldFindByUserIsNotNull() {
        // when - action and behaviour that we are going to test
        List<EventSource> found = eventSourceRepo.findByUserIsNotNull();

        // then - verify the result and output using assert statements
        assertThat(found.size()).isGreaterThan(0);
    }

    @Test
    @DisplayName("Find by status name test")
    public void shouldFindByStatusName() {
        // when - action and behaviour that we are going to test
        List<EventSource> found = eventSourceRepo.findByStatusName(eventSource.getStatus().getName());

        // then - verify the result and output using assert statements
        assertThat(found.size()).isGreaterThan(0);
    }
}
*/
