package com.ddis.ddis_hr.eapproval.query.mapper;

import com.ddis.ddis_hr.eapproval.query.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DraftMapper {

        DraftDetailResponseQueryDTO selectDraftDetail(@Param("docId") Long docId);
        List<ApprovalLineQueryDTO> selectApprovalLines(int docId);
        ContentQueryDTO selectContent(int docId);
        List<FileQueryDTO> selectFiles(int docId);

        List<ApprovalStepQueryDTO> findApprovalStepsByPosition(String position);


}
