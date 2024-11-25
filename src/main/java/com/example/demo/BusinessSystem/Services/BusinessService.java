package com.example.demo.BusinessSystem.Services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demo.BusinessSystem.DTOs.CreateBusinessDTO;
import com.example.demo.BusinessSystem.DTOs.UpdateBusinessDTO;
import com.example.demo.BusinessSystem.Entities.Business;

import com.example.demo.BusinessSystem.Mappers.Interfaces.IBusinessMapper;
import com.example.demo.BusinessSystem.Repositories.IBusinessRepository;
import com.example.demo.BusinessSystem.Repositories.IUserRepository;
import com.example.demo.BusinessSystem.Services.Interfaces.IBusinessService;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BusinessService implements IBusinessService{
  private IBusinessRepository repository;
  private IUserRepository userRepository;
  private IBusinessMapper mapper;

  @Override
  public Business createBusiness(CreateBusinessDTO dto) {
    Business business = mapper.createBusinessDTOToBusiness(dto);
    return repository.save(business); 
  }

  
  @Override 
  public void deleteBusiness(UUID businessId) {
    if (repository.existsById(businessId)) {
      repository.deleteById(businessId);
    } else {
      throw new IllegalArgumentException("Business with ID " + businessId + " does not exist.");
    }
  }

  @Override
  public Business updateBusiness(UUID businessId, UpdateBusinessDTO updateBusinessDTO) {
     
      Business existingBusiness = repository.findById(businessId)
              .orElseThrow(() -> new EntityNotFoundException("Business not found with ID: " + businessId));
  
      
      if (updateBusinessDTO.getOwnerId() != null) {
          boolean ownerExists = userRepository.existsById(updateBusinessDTO.getOwnerId());
          if (!ownerExists) {
              throw new IllegalArgumentException("No user found with ID: " + updateBusinessDTO.getOwnerId());
          }
      }
  
      Business updatedBusiness = mapper.updateBusinessFromDto(updateBusinessDTO, existingBusiness);

      return repository.save(updatedBusiness);
  }
  

}