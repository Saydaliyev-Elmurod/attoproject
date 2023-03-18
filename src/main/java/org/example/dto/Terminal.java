package org.example.dto;

import org.example.enums.TerminalStatus;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Terminal {
    private Integer id;
    private String number;
    private String address;
    private LocalDateTime localDateTime;
    private TerminalStatus status;
}
