package cl.bibliotecaam.donacion.microserviciodonacion.assemblers;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import cl.bibliotecaam.donacion.microserviciodonacion.controller.DonacionController;
import cl.bibliotecaam.donacion.microserviciodonacion.dto.DonacionResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;


import org.springframework.stereotype.Component;

@Component
public class DonacionModelAssembler implements RepresentationModelAssembler<DonacionResponseDTO, EntityModel<DonacionResponseDTO>>{
    @Override
    public EntityModel<DonacionResponseDTO> toModel(DonacionResponseDTO donacionDto){
        return EntityModel.of(donacionDto,
                linkTo(methodOn(DonacionController.class).obtenerPorId(donacionDto.getId())).withSelfRel(),
                linkTo(methodOn(DonacionController.class).obtenerTodos()).withRel("donaciones"));
    }
}
