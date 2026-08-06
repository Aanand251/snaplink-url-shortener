package com.anand.url_shortner.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminDashboardResponse {

    private long totalUsers;

    private long totalAdmins;

    private long totalActingAdmins;

    private long totalMarkedUsers;

    private long totalSuspendedUsers;

    private long totalUrls;

    private long totalClicks;
}