package dev.example.db.domain.alarm;


import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import dev.example.db.domain.BaseEntity;
import dev.example.db.domain.alarm.enums.AlarmType;
import dev.example.db.domain.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@NoArgsConstructor
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@AllArgsConstructor
@SQLDelete(sql = "UPDATE users SET deleted_at = NOW() where alarm_id=?")
@Where(clause = "deleted_at is NULL")
@Table(name = "alarms",  indexes = {
        @Index(name = "user_id_idx", columnList = "user_id")
})
public class AlarmEntity extends BaseEntity {

    @Id @Column(name = "alarm_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 알람 타입 설정
    @Enumerated(EnumType.STRING)
    private AlarmType alarmType;

    // 알람 발생된 주체 , post, like 등
    // postgras 에서만 지원
    @Type(type = "jsonb")
    @Column(columnDefinition = "json")
    private AlarmArgs args;

    // 알람을 받을 사람
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "registerd_at")
    private LocalDateTime registeredAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;


    /**
     *
     * * */
    public void setRegisterdAt(LocalDateTime localDateTime) {
        this.registeredAt = localDateTime;
    }


    @Override
    public String toString() {
        return "AlarmEntity{" +
                "id=" + id +
                ", alarmType=" + alarmType +
                ", args=" + args +
                ", user=" + user +
                ", registeredAt=" + registeredAt +
                ", updatedAt=" + updatedAt +
                ", deletedAt=" + deletedAt +
                '}';
    }
}
