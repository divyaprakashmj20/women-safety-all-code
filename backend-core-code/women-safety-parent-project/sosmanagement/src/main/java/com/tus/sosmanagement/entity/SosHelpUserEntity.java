package com.tus.sosmanagement.entity;

import com.tus.sosmanagement.enums.HelpState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SosHelpUserEntity {
    @EmbeddedId
    private SosUserId sosUserId;

    private Double latitude;
    private Double longitude;
    private LocalDateTime helpAcceptanceTime;
    private LocalDateTime lastLocationUpdateTime;

    @Enumerated(EnumType.STRING)
    private HelpState helpState;

    @Embeddable
    @Data
    @NoArgsConstructor
    public static class SosUserId implements Serializable {
        private Long sosId;
        private Long userId;

        public SosUserId(Long sos_id, Long userId) {
            this.sosId = sos_id;
            this.userId = userId;
        }
    }
}
