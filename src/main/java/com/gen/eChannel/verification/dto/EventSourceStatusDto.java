package com.gen.eChannel.verification.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventSourceStatusDto {

    private long notYetCalled;

    private long callBackSuccessful;

    private long callBackNotSuccessful;

}
