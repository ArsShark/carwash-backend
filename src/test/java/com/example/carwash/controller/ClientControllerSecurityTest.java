package com.example.carwash.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.carwash.dto.request.ClientRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Verifies the security rules end to end: SecurityConfig only requires
 * authentication on write paths, and the actual USER-vs-ADMIN restriction
 * for creating/updating/deleting comes from @PreAuthorize on the controller
 * methods. These tests make sure that restriction is really enforced.
 *
 * Requires the project's MySQL instance (docker-compose) to be running,
 * same as any other @SpringBootTest in this project.
 */
@SpringBootTest
@AutoConfigureMockMvc
class ClientControllerSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String validClientJson() throws Exception {
        ClientRequest request = new ClientRequest("Test Client", "+375291112233", "Toyota Camry");
        return objectMapper.writeValueAsString(request);
    }

    @Test
    void getAll_withoutAuthentication_isRejected() throws Exception {
        mockMvc.perform(get("/api/clients"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "USER")
    void getAll_asUser_isAllowed() throws Exception {
        mockMvc.perform(get("/api/clients"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    void create_asUser_isForbidden() throws Exception {
        mockMvc.perform(post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validClientJson()))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void create_asAdmin_isAllowed() throws Exception {
        mockMvc.perform(post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validClientJson()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    void delete_asUser_isForbidden() throws Exception {
        mockMvc.perform(delete("/api/clients/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void create_withBlankFullName_isRejectedWith400() throws Exception {
        String invalidJson = objectMapper.writeValueAsString(new ClientRequest("", "+375291112233", "Toyota Camry"));

        mockMvc.perform(post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }
}
