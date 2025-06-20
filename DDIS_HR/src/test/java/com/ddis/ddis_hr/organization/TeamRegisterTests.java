package com.ddis.ddis_hr.organization;

import com.ddis.ddis_hr.organization.command.application.controller.StructureController;
import com.ddis.ddis_hr.organization.command.application.dto.TeamDTO;
import com.ddis.ddis_hr.organization.command.application.dto.TeamRequestDTO;
import com.ddis.ddis_hr.organization.command.application.service.DepartmentService;
import com.ddis.ddis_hr.organization.command.application.service.HeadquartersService;
import com.ddis.ddis_hr.organization.command.application.service.TeamService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TeamRegisterTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TeamService teamService;

    @Autowired
    private ObjectMapper objectMapper;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public TeamService teamService() {
            return Mockito.mock(TeamService.class);
        }

        @Bean
        public HeadquartersService headquartersService() {
            return Mockito.mock(HeadquartersService.class);
        }

        @Bean
        public DepartmentService departmentService() {
            return Mockito.mock(DepartmentService.class);
        }
    }

    @Test
    @DisplayName("팀 등록 성공")
    void createTeam_success() throws Exception {
        // given
        TeamRequestDTO requestDTO = new TeamRequestDTO();
        requestDTO.setTeamName("테스트팀");
        requestDTO.setDepartmentId(10L);

        TeamDTO responseDTO  = new TeamDTO();
        responseDTO .setTeamId(23L);
        responseDTO .setTeamName("테스트팀");
        responseDTO .setTeamCode("T022");

        Mockito.when(teamService.createTeam(any(TeamRequestDTO.class))).thenReturn(responseDTO);

        // when & then
        mockMvc.perform(post("/org/create/team")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.teamId").value(23L))
                .andExpect(jsonPath("$.teamName").value("테스트팀"))
                .andExpect(jsonPath("$.teamCode").value("T022"));
    }
}
