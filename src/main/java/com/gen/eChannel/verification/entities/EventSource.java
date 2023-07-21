package com.gen.eChannel.verification.entities;

import com.gen.eChannel.verification.util.Auditable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventSource extends Auditable<String> {

    @NotBlank(message = "Please enter businessKey")
    private String businessKey;

    @NotBlank(message = "Please enter priority")
    private String priority;

    @NotBlank(message = "Please enter sourceBu")
    private String sourceBu;

    @NotBlank(message = "Please enter dcpReference")
    private String dcpReference;

    @NotBlank(message = "Please enter accountName")
    private String accountName;

    @NotBlank(message = "Please enter transactionCurrency")
    private String transactionCurrency;

    @NotNull(message = "Please enter transactionAmount")
    private Double transactionAmount;

    @NotBlank(message = "Please enter lockedBy")
    private String lockedBy;

    @NotBlank(message = "Please enter debitAccountNumber")
    private String debitAccountNumber;

    @NotBlank(message = "Please enter accountCurrency")
    private String accountCurrency;

    @NotBlank(message = "Please enter beneficiaryName")
    private String beneficiaryName;

    @NotBlank(message = "Please enter custInfoMkr")
    private String custInfoMkr;

    @NotBlank(message = "Please enter accountInfoMkr")
    private String accountInfoMkr;

    @ManyToOne(cascade = CascadeType.ALL)
    private OutCome outCome;

    @NotBlank(message = "Please enter extension")
    private String extension;

    @NotBlank(message = "Please enter contactPerson")
    private String contactPerson;

    @NotBlank(message = "Please enter customerCalledOn")
    private String customerCalledOn;

    @NotBlank(message = "Please enter your comments")
    private String comment;

    @ManyToOne(cascade = CascadeType.ALL)
    private Status status;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    private LocalDateTime updatedOn;

}
