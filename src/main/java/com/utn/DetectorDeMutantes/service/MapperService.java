package com.utn.DetectorDeMutantes.service;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MapperService {

    private final ModelMapper modelMapper;

    public MapperService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public <D, T> D convertir(T origen, Class<D> destinoClass) {
        return modelMapper.map(origen, destinoClass);
    }
}
