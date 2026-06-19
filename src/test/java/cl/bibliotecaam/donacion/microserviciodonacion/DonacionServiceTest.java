package cl.bibliotecaam.donacion.microserviciodonacion;

import cl.bibliotecaam.donacion.microserviciodonacion.dto.DonacionRequestDTO;
import cl.bibliotecaam.donacion.microserviciodonacion.dto.DonacionResponseDTO;
import cl.bibliotecaam.donacion.microserviciodonacion.model.Donacion;
import cl.bibliotecaam.donacion.microserviciodonacion.repository.DonacionRepository;
import cl.bibliotecaam.donacion.microserviciodonacion.service.DonacionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
public class DonacionServiceTest {

    @Autowired
    private DonacionService donacionService;

    @MockitoBean
    private DonacionRepository donacionRepository;

    @MockitoBean
    private WebClient webClient;

    private final String tokenFalso = "Bearer token_falso_123";

    @BeforeEach
    public void limpiarMocks() {
        // Limpiamos el historial del repositorio para aislar cada test
        clearInvocations(donacionRepository);
    }

    @Test
    public void testObtenerTodos() {
        Donacion donacion = new Donacion(1L, 12345678L, "K", "Juan", "Carlos", "Pérez", "Gómez", 99L);
        when(donacionRepository.findAll()).thenReturn(List.of(donacion));

        List<DonacionResponseDTO> resultado = donacionService.obtenerTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Juan", resultado.get(0).getPnombre());
    }

    @Test
    public void testObtenerPorId() {
        Donacion donacion = new Donacion(1L, 12345678L, "K", "Juan", "Carlos", "Pérez", "Gómez", 99L);
        when(donacionRepository.findById(1L)).thenReturn(Optional.of(donacion));

        Optional<DonacionResponseDTO> found = donacionService.obtenerPorId(1L);

        assertTrue(found.isPresent());
        assertEquals(1L, found.get().getId());
        assertEquals("Juan", found.get().getPnombre());
    }

    @Test
    public void testGuardar() {
        // 1. Preparamos el DTO de entrada con datos válidos
        DonacionRequestDTO requestDTO = new DonacionRequestDTO();
        requestDTO.setNumrun(12345678L);
        requestDTO.setDvRun("K");
        requestDTO.setPnombre("Juan");
        requestDTO.setSnombre("Carlos");
        requestDTO.setAppaterno("Pérez");
        requestDTO.setApmaterno("Gómez");
        requestDTO.setIdEmpleado(99L);

        // 2. Simulamos que el WebClient responde exitosamente al validar el empleado
        simularWebClientExitoso(webClient);

        // 3. Simulamos la persistencia en base de datos
        Donacion donacionGuardada = new Donacion(1L, 12345678L, "K", "Juan", "Carlos", "Pérez", "Gómez", 99L);
        when(donacionRepository.save(any(Donacion.class))).thenReturn(donacionGuardada);

        // 4. Ejecutamos
        DonacionResponseDTO resultado = donacionService.guardar(requestDTO, tokenFalso);

        // 5. Validamos
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(99L, resultado.getIdEmpleado());
        verify(donacionRepository, times(1)).save(any(Donacion.class));
    }

    @Test
    public void testActualizar() {
        DonacionRequestDTO requestDTO = new DonacionRequestDTO();
        requestDTO.setNumrun(87654321L);
        requestDTO.setDvRun("1");
        requestDTO.setPnombre("Pedro");
        requestDTO.setIdEmpleado(99L);

        Donacion donacionExistente = new Donacion(1L, 12345678L, "K", "Juan", "Carlos", "Pérez", "Gómez", 99L);

        simularWebClientExitoso(webClient);
        when(donacionRepository.findById(1L)).thenReturn(Optional.of(donacionExistente));

        Donacion donacionActualizada = new Donacion(1L, 87654321L, "1", "Pedro", "Carlos", "Pérez", "Gómez", 99L);
        when(donacionRepository.save(any(Donacion.class))).thenReturn(donacionActualizada);

        Optional<DonacionResponseDTO> resultado = donacionService.actualizar(1L, requestDTO, tokenFalso);

        assertTrue(resultado.isPresent());
        assertEquals(87654321L, resultado.get().getNumrun());
        assertEquals("Pedro", resultado.get().getPnombre());
    }

    @Test
    public void testEliminar() {
        doNothing().when(donacionRepository).deleteById(1L);

        donacionService.eliminar(1L);

        verify(donacionRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testObtenerPorNumrun() {
        Donacion donacion = new Donacion(1L, 12345678L, "K", "Juan", "Carlos", "Pérez", "Gómez", 99L);
        when(donacionRepository.findByNumrun(12345678L)).thenReturn(List.of(donacion));

        List<DonacionResponseDTO> resultado = donacionService.obtenerPorNumrun(12345678L);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(12345678L, resultado.get(0).getNumrun());
    }

    @Test
    public void testObtenerPorPnombre() {
        Donacion donacion = new Donacion(1L, 12345678L, "K", "Juan", "Carlos", "Pérez", "Gómez", 99L);
        when(donacionRepository.findByPnombre("Juan")).thenReturn(List.of(donacion));

        List<DonacionResponseDTO> resultado = donacionService.obtenerPorPnombre("Juan");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Juan", resultado.get(0).getPnombre());
    }

    @Test
    public void testObtenerPorAppaterno() {
        Donacion donacion = new Donacion(1L, 12345678L, "K", "Juan", "Carlos", "Pérez", "Gómez", 99L);
        when(donacionRepository.findByAppaterno("Pérez")).thenReturn(List.of(donacion));

        List<DonacionResponseDTO> resultado = donacionService.obtenerPorAppaterno("Pérez");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Pérez", resultado.get(0).getAppaterno());
    }

    // --- MÉTODOS AUXILIARES ---

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void simularWebClientExitoso(WebClient webClientMock) {
        WebClient.RequestHeadersUriSpec uriSpec = mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec headersSpec = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);

        when(webClientMock.get()).thenReturn(uriSpec);
        // Usamos Varargs (Object[].class) o String por el encadenamiento del path variable {id} e headers
        when(uriSpec.uri(anyString(), any(Object[].class))).thenReturn(headersSpec);
        when(headersSpec.header(anyString(), anyString())).thenReturn(headersSpec);
        when(headersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just("OK"));
    }
}