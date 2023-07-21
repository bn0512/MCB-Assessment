package com.gen.eChannel.verification.entities;

import com.gen.eChannel.verification.util.Auditable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OutCome extends Auditable<String> {

    @NotBlank(message = "out come name must not be null")
    private String name;

}
