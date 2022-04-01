package com.example.maintenance.Action;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.matchesRegex;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

abstract class AbstractActionStep {
    protected final String ACTION_REPORT_URL_PATH =  "/api/action/report";

    protected void validateActionEntityList(ResultActions resultActions) throws Exception {
        resultActions.andDo(print());
        resultActions
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id", instanceOf(Integer.class)))
                .andExpect(jsonPath("$[0].name", instanceOf(String.class)))
                .andExpect(jsonPath("$[0].sort", instanceOf(Integer.class)))
                .andExpect(jsonPath("$[0].events").isArray())
                .andExpect(jsonPath("$[0].events[0].id", instanceOf(Integer.class)))
                .andExpect(jsonPath("$[0].events[0].actionId", instanceOf(Integer.class)))
                .andExpect(jsonPath("$[0].events[0].mileage", instanceOf(Integer.class)))
                .andExpect(jsonPath("$[0].events[0].eventDate", instanceOf(String.class)))
                .andExpect(jsonPath("$[0].events[0].eventDate", matchesRegex("[0-9]{4}-[0-9]{2}-[0-9]{2}")));
    }
}
