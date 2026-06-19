package cl.bibliotecaam.donacion.microserviciodonacion;

import cl.bibliotecaam.donacion.microserviciodonacion.assemblers.DonacionModelAssembler;
import cl.bibliotecaam.donacion.microserviciodonacion.controller.DonacionController;
import cl.bibliotecaam.donacion.microserviciodonacion.dto.DonacionRequestDTO;
import cl.bibliotecaam.donacion.microserviciodonacion.dto.DonacionResponseDTO;
import cl.bibliotecaam.donacion.microserviciodonacion.service.DonacionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DonacionController.class)
@DisplayName("Tests unitarios - DonacionController")
class DonacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private DonacionService donacionService;

    @MockitoBean
    private DonacionModelAssembler assembler;

    private DonacionResponseDTO responseDTO;
    private EntityModel<DonacionResponseDTO> entityModel;

    @BeforeEach
    void setUp() {
        responseDTO = new DonacionResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setNumrun(12345678L);
        responseDTO.setDvRun("K");
        responseDTO.setPnombre("Juan");
        responseDTO.setSnombre("Carlos");
        responseDTO.setAppaterno("Pérez");
        responseDTO.setApmaterno("Gómez");
        responseDTO.setIdEmpleado(99L);

        entityModel = EntityModel.of(responseDTO);
    }

    @Test
    @DisplayName("WHEN: Se solicita obtenerTodos THEN: Retorna la colección en formato HAL+JSON con estado 200")
    void shouldObtenerTodos() throws Exception {
        List<DonacionResponseDTO> listaDto = Collections.singletonList(responseDTO);
        when(donacionService.obtenerTodos()).thenReturn(listaDto);
        when(assembler.toModel(any(DonacionResponseDTO.class))).thenReturn(entityModel);

        mockMvc.perform(get("/api/bibliotecaam/donacion")
                        .accept(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("$._embedded.donacionResponseDTOList[0].pnombre").value("Juan"))
                .andExpect(jsonPath("$._embedded.donacionResponseDTOList[0].numrun").value(12345678));
    }

    @Test
    @DisplayName("GIVEN: ID existente WHEN: Se busca por ID THEN: Retorna la donación con estado 200")
    void shouldObtenerPorIdExitoso() throws Exception {
        when(donacionService.obtenerPorId(1L)).thenReturn(Optional.of(responseDTO));
        when(assembler.toModel(responseDTO)).thenReturn(entityModel);

        mockMvc.perform(get("/api/bibliotecaam/donacion/{id}", 1L)
                        .accept(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pnombre").value("Juan"))
                .andExpect(jsonPath("$.appaterno").value("Pérez"));
    }

    @Test
    @DisplayName("GIVEN: ID inexistente WHEN: Se busca por ID THEN: Retorna estado 404")
    void shouldObtenerPorIdNoEncontrado() throws Exception {
        when(donacionService.obtenerPorId(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/bibliotecaam/donacion/{id}", 99L))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GIVEN: Datos válidos y token WHEN: Se guarda una donación THEN: Retorna la nueva donación con estado 201")
    void shouldGuardarDonacion() throws Exception {
        DonacionRequestDTO requestDTO = new DonacionRequestDTO();
        requestDTO.setNumrun(12345678L);
        requestDTO.setDvRun("K");
        requestDTO.setPnombre("Juan");
        requestDTO.setSnombre("Carlos");
        requestDTO.setAppaterno("Pérez");
        requestDTO.setApmaterno("Gómez");
        requestDTO.setIdEmpleado(99L);

        String tokenFalso = "Bearer token_de_prueba_123";

        when(donacionService.guardar(any(DonacionRequestDTO.class), eq(tokenFalso))).thenReturn(responseDTO);
        when(assembler.toModel(any(DonacionResponseDTO.class))).thenReturn(entityModel);

        mockMvc.perform(post("/api/bibliotecaam/donacion")
                        .header(HttpHeaders.AUTHORIZATION, tokenFalso)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.pnombre").value("Juan"));
    }

    @Test
    @DisplayName("GIVEN: ID existente WHEN: Se elimina THEN: Retorna estado 204 No Content")
    void shouldEliminarDonacionExitosa() throws Exception {
        when(donacionService.obtenerPorId(1L)).thenReturn(Optional.of(responseDTO));

        mockMvc.perform(delete("/api/bibliotecaam/donacion/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}