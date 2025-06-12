package com.ddis.ddis_hr.goals.command.domain.aggregate;

import com.ddis.ddis_hr.member.command.domain.aggregate.entity.Employee;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "selfreviewfile")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SelfreviewFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "selfreview_file_id")
    private Long id;

    @Column(name = "selfreview_file_name",columnDefinition = "TEXT")
    private String fileName;

    @Column(name = "selfreview_file_url", nullable = false,columnDefinition = "TEXT")
    private String fileUrl;

    @Column(name = "selfreview_file_upload_date", nullable = false)
    private LocalDateTime uploadDate;

    @Column(name = "selfreview_file_size", nullable = false, length = 255)
    private Double fileSize;

    @Column(name = "selfreview_file_type", nullable = false, length = 255)
    private String fileType;

    // 연관관계: 이 첨부파일을 업로드한 직원
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    @ToString.Exclude
    private Employee employee;

    // 연관관계: 이 첨부파일이 속한 SelfReview (Performance 엔티티)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "selfreview_id", nullable = false)
    @ToString.Exclude
    private Performance performance;
}
