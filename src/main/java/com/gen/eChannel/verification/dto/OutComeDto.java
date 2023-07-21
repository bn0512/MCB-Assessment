package com.gen.eChannel.verification.dto;

import com.gen.eChannel.verification.util.Auditable;
import lombok.*;

import javax.validation.Valid;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class OutComeDto extends Auditable<String> {

    @Valid
    private String name;

}
