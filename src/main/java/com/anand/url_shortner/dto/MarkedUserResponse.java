package com.anand.url_shortner.dto;

import com.anand.url_shortner.entity.MarkReason;
import com.anand.url_shortner.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarkedUserResponse {

    private Long id;

    private String name;

    private String email;

    private Role role;

    private boolean marked;

    private MarkReason reason;

    private String markedBy;

    private LocalDateTime markedAt;

    private boolean suspended;
}