package com.example.maintenance.Action;

import com.example.maintenance.controller.ActionRestController;
import com.example.maintenance.entity.ActionEntity;
import com.example.maintenance.entity.EventEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MockActionRestControllerTest extends AbstractActionStep {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ActionRestController mockActionRestController;

    /**
     * Проверяем структуру mock json-данных
     */
    @Test
    public void getMockReport() throws Exception {
        when(mockActionRestController.getReport()).thenReturn(this.getMockActionEntityList());

        ResultActions resultActions = mockMvc.perform(get(ACTION_REPORT_URL_PATH));
        this.validateActionEntityList(resultActions);

        verify(mockActionRestController, times(1)).getReport();
    }

    private List<ActionEntity> getMockActionEntityList() {
        EventEntity eventEntity = new EventEntity();
        eventEntity.setId(1);
        eventEntity.setActionId(2);
        eventEntity.setMileage(3);
        eventEntity.setEventDate(LocalDate.of(2022, 1, 31));

        List<EventEntity> eventEntities = Arrays.asList(
                eventEntity,
                eventEntity
        );

        ActionEntity actionEntity = new ActionEntity();
        actionEntity.setId(1);
        actionEntity.setName("mockActionName");
        actionEntity.setSort(10);
        actionEntity.setEvents(eventEntities);

        return List.of(actionEntity);
    }
}
