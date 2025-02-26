package com.felipemoreira.dockermanager.controllers;

import com.felipemoreira.dockermanager.services.DockerService;
import com.github.dockerjava.api.model.Container;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class DockerContainersControllerTest {

    @Mock private DockerService dockerService;

    @InjectMocks private DockerContainersController dockerContainersController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(dockerContainersController).build();
    }

    @Test
    @DisplayName("Deveria chamar o endpoint de consultar os containers com o default value da rota")
    void testListContainers_withDefaultValue() throws Exception {
        List<Container> mockContainers = Collections.emptyList();
        when(dockerService.listContainers(true)).thenReturn(mockContainers);

        mockMvc.perform(get("/api/containers"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(dockerService).listContainers(true);
    }

    @Test
    @DisplayName("Deveria chamar o endpoint de consultar os containers recebendo o valor de showAll no body")
    void testListContainers_withParamShowAllFalse() throws Exception {
        List<Container> mockContainers = Collections.emptyList();
        when(dockerService.listContainers(false)).thenReturn(mockContainers);

        mockMvc.perform(get("/api/containers").param("showAll", "false"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(dockerService).listContainers(false);
    }
}