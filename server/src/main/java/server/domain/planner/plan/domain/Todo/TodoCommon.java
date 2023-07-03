package server.domain.planner.plan.domain.Todo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "TODOTYPE")
public class TodoCommon {

    // 투두 인덱스
    @Id
    @GeneratedValue
    private Long todoId;

    // 투두 타입 (enum)
    @Enumerated(EnumType.STRING)
    private TodoType todoType;

    // 투두 제목
    protected String title;

    // 투두 실행 날짜
    @Column(columnDefinition = "timestamp default now()")
    protected LocalDateTime todoDate;

    // 투두 내용
    protected String contents;

    // 투두 공개 여부
    @ColumnDefault("false")
    protected Boolean isPrivate;
}
