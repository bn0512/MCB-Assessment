package com.gen.eChannel.verification.dto;

import com.gen.eChannel.verification.util.Auditable;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventSourceEchannelVerificationDto extends Auditable<String> {

    private String priority;

    private String sourceBu;

    private String businessKey;

    private String dcpReference;

    private String accountName;

    private String transactionCurrency;

    private double transactionAmount;

    private String lockedBy;

    @OneToOne(cascade = CascadeType.ALL)
    private UserDto user;

    @ManyToOne(cascade = CascadeType.ALL)
    private StatusDto status;

    @ManyToOne(cascade = CascadeType.ALL)
    private OutComeDto outCome;
}
