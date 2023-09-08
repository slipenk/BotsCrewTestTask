package com.slipenk.bc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StatisticsDTO {

    private Long assistantsCount;
    private Long associateProfessorsCount;
    private Long professorsCount;

}
