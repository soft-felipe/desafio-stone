package com.felipemoreira.dockermanager.services;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.ListContainersCmd;
import com.github.dockerjava.api.command.StartContainerCmd;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.github.dockerjava.api.model.Container;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DockerServiceTest {

    @Mock private DockerClient dockerClient;

    @Mock private ListContainersCmd listContainersCmd;
    @Mock private StartContainerCmd startContainerCmd;

    @InjectMocks private DockerService dockerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deveria consultar os containers para showAll=true")
    void testListContainers_withShowAllTrue() {
        /* Prepare (preparar) */
        List<Container> mockContainers = Collections.emptyList();
        /* Necessário mockar os métodos chamados de maneira encadeada no service (method chaining) */
        when(dockerClient.listContainersCmd()).thenReturn(listContainersCmd);
        when(listContainersCmd.withShowAll(true)).thenReturn(listContainersCmd);
        when(listContainersCmd.exec()).thenReturn(mockContainers);

        /* Act (agir) */
        List<Container> result = dockerService.listContainers(true);

        /* Assert (afirmar) */
        assertEquals(mockContainers, result);
        verify(dockerClient).listContainersCmd();
        verify(listContainersCmd).withShowAll(true);
        verify(listContainersCmd).exec();
    }

    @Test
    @DisplayName("Deveria consultar os containers para showAll=false")
    void testListContainers_withShowAllFalse() {
        List<Container> mockContainers = Collections.emptyList();
        when(dockerClient.listContainersCmd()).thenReturn(listContainersCmd);
        when(listContainersCmd.withShowAll(false)).thenReturn(listContainersCmd);
        when(listContainersCmd.exec()).thenReturn(mockContainers);

        List<Container> result = dockerService.listContainers(false);

        assertEquals(mockContainers, result);
        verify(dockerClient).listContainersCmd();
        verify(listContainersCmd).withShowAll(false);
        verify(listContainersCmd).exec();
    }

    @Test
    @DisplayName("Deveria iniciar um container com sucesso passando um ID")
    void testStartContainer_withRandomUUID() {
        String containerId = UUID.randomUUID().toString();
        when(dockerClient.startContainerCmd(containerId)).thenReturn(startContainerCmd);
        /* Outras possibilidades de passar o ID como parâmetro:
         * - eq(): verifica se é exatamente igual quando rolar o "act" (chamada do método do serviço)
         *      when(dockerClient.startContainerCmd(eq(containerId))).thenReturn(startContainerCmd);
         * - any(): para qualquer valor passado como parâmetro no "act" ele vai aceitar e retornar o .then()
         *      when(dockerClient.startContainerCmd(any())).thenReturn(startContainerCmd);
         * */

        dockerService.startContainer(containerId);

        verify(dockerClient).startContainerCmd(containerId);
        verify(startContainerCmd).exec();
    }
}