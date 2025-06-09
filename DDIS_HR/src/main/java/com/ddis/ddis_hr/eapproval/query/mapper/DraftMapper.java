package com.ddis.ddis_hr.eapproval.query.mapper;

import com.ddis.ddis_hr.eapproval.query.dto.ApprovalLineQueryDTO;
import com.ddis.ddis_hr.eapproval.query.dto.ContentQueryDTO;
import com.ddis.ddis_hr.eapproval.query.dto.DraftDetailResponseQueryDTO;
import com.ddis.ddis_hr.eapproval.query.dto.FileDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DraftMapper {

        DraftDetailResponseQueryDTO selectDraftDetail(@Param("docId") Long docId);
        List<ApprovalLineQueryDTO> selectApprovalLines(int docId);
        ContentQueryDTO selectContent(int docId);
        List<FileDTO> selectFiles(int docId);

        String test();


}
