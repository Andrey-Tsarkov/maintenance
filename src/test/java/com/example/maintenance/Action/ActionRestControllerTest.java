package com.example.maintenance.Action;

import com.example.maintenance.controller.ActionRestController;
import com.example.maintenance.entity.ActionEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ActionRestControllerTest extends AbstractActionStep {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ActionRestController actionRestController;

    /**
     * Проверяем наличие контроллера
     */
    @org.junit.jupiter.api.Test
    void hasActionController() {
        assertThat(actionRestController).isNotNull();
    }

    /**
     * Проверить тип данных отчета, полученного из контроллера
     */
    @org.junit.jupiter.api.Test
    void getReportContextByController() {
        List<ActionEntity> actionEntityList = actionRestController.getReport();
        ActionEntity actionEntity = actionEntityList.get(0);
        if (null != actionEntity) {
            assertThat(actionEntity).isInstanceOf(ActionEntity.class);
        }
    }

    /**
     * Проверяем структуру json-данных
     */
    @Test
    public void getReport() throws Exception {
        ResultActions resultActions = mockMvc.perform(get(ACTION_REPORT_URL_PATH));
        String content = resultActions.andReturn().getResponse().getContentAsString();
        if (0 < content.length()) {
            this.validateActionEntityList(resultActions);
        }
    }
}
