package com.anand.url_shortner.scheduler;

import com.anand.url_shortner.entity.SuspensionType;
import com.anand.url_shortner.entity.User;
import com.anand.url_shortner.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class SuspensionScheduler {

    private final UserRepository userRepository;

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void removeExpiredSuspensions() {

        for (User user : userRepository.findBySuspendedTrue()) {

            if (user.getSuspensionType() != SuspensionType.SOFT) {
                continue;
            }

            if (user.getSuspendedUntil() == null) {
                continue;
            }

            if (user.getSuspendedUntil().isAfter(LocalDateTime.now())) {
                continue;
            }

            user.setSuspended(false);
            user.setSuspensionType(null);
            user.setSuspendedUntil(null);
            user.setSuspendedAt(null);
            user.setSuspendedBy(null);
        }
    }
}