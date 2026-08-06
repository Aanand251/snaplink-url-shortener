package com.anand.url_shortner.service;
import com.anand.url_shortner.dto.*;
import com.anand.url_shortner.entity.*;
import com.anand.url_shortner.repository.UrlRepository;
import com.anand.url_shortner.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.anand.url_shortner.exception.BadRequestException;
import com.anand.url_shortner.dto.AdminDashboardResponse;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final UrlRepository urlRepository;
    private final UserService userService;
    private final AuditLogService auditLogService;

    @Transactional(readOnly = true)
    public List<AdminUserResponse> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(this::mapToUserResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AdminUrlResponse> getAllUrls() {

        return urlRepository.findAll()
                .stream()
                .map(this::mapToUrlResponse)
                .toList();
    }

    @Transactional
    public void deleteUser(Long id) {

        User currentUser = userService.getCurrentUser();

        User targetUser = findUserById(id);

        if (currentUser.getId() == targetUser.getId()) {
            throw new BadRequestException(
                    "You cannot delete your own account."
            );
        }

        if (targetUser.getRole() == Role.ADMIN) {
            throw new BadRequestException(
                    "Admin account cannot be deleted."
            );
        }

        auditLogService.logAction(
                AuditAction.USER_DELETED,
                currentUser,
                targetUser,
                "Deleted user : " + targetUser.getEmail()
        );

        userRepository.delete(targetUser);
    }

    @Transactional
    public void changeUserRole(
            Long id,
            ChangeUserRoleRequest request
    ) {

        User targetUser = findUserById(id);

        User currentUser = userService.getCurrentUser();

        if (currentUser.getId() == targetUser.getId()) {
            throw new RuntimeException(
                    "You cannot change your own role."
            );
        }

        targetUser.setRole(request.getRole());
        auditLogService.logAction(
                AuditAction.USER_ROLE_CHANGED,
                currentUser,
                targetUser,
                "Role changed to " + request.getRole()
        );
    }

    @Transactional
    public void markUser(
            Long id,
            MarkUserRequest request
    ) {

        User targetUser = findUserById(id);

        User currentUser = userService.getCurrentUser();

        if (currentUser.getId() == targetUser.getId()) {
            throw new RuntimeException(
                    "You cannot mark yourself."
            );
        }

        if (targetUser.isMarked()) {
            throw new RuntimeException(
                    "User is already marked."
            );
        }

        targetUser.setMarked(true);
        targetUser.setMarkedReason(request.getReason());
        targetUser.setMarkedBy(currentUser);
        targetUser.setMarkedAt(LocalDateTime.now());
        auditLogService.logAction(
                AuditAction.USER_MARKED,
                currentUser,
                targetUser,
                request.getReason().name()
        );
    }

    private User findUserById(Long id) {

        return userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );
    }

    @Transactional
    public void unmarkUser(Long id) {
        User currentUser = userService.getCurrentUser();

        User targetUser = findUserById(id);

        if (!targetUser.isMarked()) {
            throw new RuntimeException(
                    "User is not marked."
            );
        }

        targetUser.setMarked(false);
        targetUser.setMarkedReason(null);
        targetUser.setMarkedBy(null);
        targetUser.setMarkedAt(null);
        auditLogService.logAction(
                AuditAction.USER_UNMARKED,
                currentUser,
                targetUser,
                "User unmarked"
        );
    }

    @Transactional(readOnly = true)
    public List<MarkedUserResponse> getMarkedUsers() {

        return userRepository.findByMarkedTrue()
                .stream()
                .map(user -> MarkedUserResponse.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .email(user.getEmail())
                        .role(user.getRole())
                        .marked(user.isMarked())
                        .reason(user.getMarkedReason())
                        .markedBy(
                                user.getMarkedBy() != null
                                        ? user.getMarkedBy().getEmail()
                                        : null
                        )
                        .markedAt(user.getMarkedAt())
                        .suspended(user.isSuspended())
                        .build())
                .toList();
    }
    @Transactional
    public void suspendUser(
            Long userId,
            SuspendUserRequest request
    ) {

        User currentUser = userService.getCurrentUser();

        User targetUser = findUserById(userId);

        if (currentUser.getId() == targetUser.getId()) {
            throw new BadRequestException(
                    "You cannot suspend yourself."
            );
        }

        if (targetUser.getRole() == Role.ADMIN) {
            throw new BadRequestException(
                    "Admin account cannot be suspended."
            );
        }

        if (targetUser.isSuspended()) {
            throw new BadRequestException(
                    "User is already suspended."
            );
        }

        targetUser.setSuspended(true);
        targetUser.setSuspensionType(
                request.getSuspensionType()
        );

        targetUser.setSuspendedAt(
                LocalDateTime.now()
        );

        targetUser.setSuspendedBy(currentUser);

        String remarks;

        if (request.getSuspensionType() == SuspensionType.SOFT) {

            if (request.getDurationInDays() == null) {
                throw new BadRequestException(
                        "Duration is required for SOFT suspension."
                );
            }

            if (request.getDurationInDays() < 1
                    || request.getDurationInDays() > 7) {

                throw new BadRequestException(
                        "Duration must be between 1 and 7 days."
                );
            }

            targetUser.setSuspendedUntil(
                    LocalDateTime.now()
                            .plusDays(request.getDurationInDays())
            );

            remarks = "SOFT (" +
                    request.getDurationInDays() +
                    " days)";

        } else {

            targetUser.setSuspendedUntil(null);

            remarks = "HARD";
        }

        auditLogService.logAction(
                AuditAction.USER_SUSPENDED,
                currentUser,
                targetUser,
                remarks
        );
    }

    private PageRequest getPageRequest(
            int page,
            int size
    ) {

        return PageRequest.of(
                page,
                size,
                Sort.by(
                        Sort.Direction.DESC,
                        "createdAt"
                )
        );
    }

    @Transactional(readOnly = true)
    public Page<AdminUserResponse> searchUsersByName(
            String name,
            int page,
            int size
    ) {

        return userRepository
                .findByNameContainingIgnoreCase(
                        name,
                        getPageRequest(page, size)
                )
                .map(this::mapToUserResponse);
    }

    @Transactional(readOnly = true)
    public Page<AdminUserResponse> searchUsersByEmail(
            String email,
            int page,
            int size
    ) {

        return userRepository
                .findByEmailContainingIgnoreCase(
                        email,
                        getPageRequest(page, size)
                )
                .map(this::mapToUserResponse);
    }

    @Transactional(readOnly = true)
    public Page<AdminUserResponse> filterUsersByRole(
            Role role,
            int page,
            int size
    ) {

        return userRepository
                .findByRole(
                        role,
                        getPageRequest(page, size)
                )
                .map(this::mapToUserResponse);
    }

    @Transactional(readOnly = true)
    public Page<AdminUserResponse> filterMarkedUsers(
            boolean marked,
            int page,
            int size
    ) {

        return userRepository
                .findByMarked(
                        marked,
                        getPageRequest(page, size)
                )
                .map(this::mapToUserResponse);
    }

    @Transactional(readOnly = true)
    public Page<AdminUserResponse> filterSuspendedUsers(
            boolean suspended,
            int page,
            int size
    ) {

        return userRepository
                .findBySuspended(
                        suspended,
                        getPageRequest(page, size)
                )
                .map(this::mapToUserResponse);
    }


    private AdminUserResponse mapToUserResponse(User user) {

        long totalLinks = user.getUrls().size();

        long totalClicks = user.getUrls()
                .stream()
                .mapToLong(UrlMapping::getTotalClicks)
                .sum();

        return AdminUserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .marked(user.isMarked())
                .suspended(user.isSuspended())
                .totalLinks(totalLinks)
                .totalClicks(totalClicks)
                .createdAt(user.getCreatedAt())
                .build();
    }

    private AdminUrlResponse mapToUrlResponse(UrlMapping urlMapping) {

        User owner = urlMapping.getUser();

        return AdminUrlResponse.builder()
                .id(urlMapping.getId())
                .originalUrl(urlMapping.getOriginalUrl())
                .shortCode(urlMapping.getShortCode())
                .totalClicks(urlMapping.getTotalClicks())
                .createdAt(urlMapping.getCreatedAt())
                .userId(owner != null ? owner.getId() : null)
                .userEmail(owner != null ? owner.getEmail() : null)
                .build();
    }

    @Transactional
    public void unsuspendUser(Long userId) {
        User currentUser = userService.getCurrentUser();
        User targetUser = findUserById(userId);

        if (!targetUser.isSuspended()) {
            throw new BadRequestException(
                    "User is not suspended."
            );
        }

        targetUser.setSuspended(false);
        targetUser.setSuspensionType(null);
        targetUser.setSuspendedUntil(null);
        targetUser.setSuspendedAt(null);
        targetUser.setSuspendedBy(null);
        auditLogService.logAction(
                AuditAction.USER_UNSUSPENDED,
                currentUser,
                targetUser,
                "Suspension removed"
        );
    }
    @Transactional(readOnly = true)
    public List<SuspendedUserResponse> getSuspendedUsers() {

        return userRepository.findBySuspendedTrue()
                .stream()
                .map(user -> SuspendedUserResponse.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .email(user.getEmail())
                        .role(user.getRole())
                        .suspended(user.isSuspended())
                        .suspensionType(
                                user.getSuspensionType()
                        )
                        .suspendedUntil(
                                user.getSuspendedUntil()
                        )
                        .suspendedAt(
                                user.getSuspendedAt()
                        )
                        .suspendedBy(
                                user.getSuspendedBy() != null
                                        ? user.getSuspendedBy().getEmail()
                                        : null
                        )
                        .build())
                .toList();
    }
    @Transactional(readOnly = true)
    public AdminDashboardResponse getDashboardStats() {

        return AdminDashboardResponse.builder()

                .totalUsers(
                        userRepository.count()
                )

                .totalAdmins(
                        userRepository.countByRole(Role.ADMIN)
                )

                .totalActingAdmins(
                        userRepository.countByRole(Role.ACTING_ADMIN)
                )

                .totalMarkedUsers(
                        userRepository.countByMarkedTrue()
                )

                .totalSuspendedUsers(
                        userRepository.countBySuspendedTrue()
                )

                .totalUrls(
                        urlRepository.count()
                )

                .totalClicks(
                        urlRepository.getTotalClicks()
                )

                .build();
    }
}