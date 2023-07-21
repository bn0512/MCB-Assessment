package com.gen.eChannel.verification.dto;

import com.gen.eChannel.verification.util.Auditable;
import lombok.*;

import javax.validation.Valid;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatusDto extends Auditable<String> {

    @Valid
    private String name;

}
