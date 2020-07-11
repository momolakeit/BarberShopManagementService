package com.momoProjet.BarberShopManagementService.service.converter;

import com.momoProjet.BarberShopManagementService.DTO.BusinessDTO;
import com.momoProjet.BarberShopManagementService.DTO.ClientDTO;
import com.momoProjet.BarberShopManagementService.DTO.EmployeeDTO;
import com.momoProjet.BarberShopManagementService.DTO.UtilisateurDTO;
import com.momoProjet.BarberShopManagementService.entities.Business;
import com.momoProjet.BarberShopManagementService.entities.Employee;
import com.momoProjet.BarberShopManagementService.entities.Utilisateur;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UtilisateurToDTOGenericConverter {
    private final String BUSINESS_ROLE= "BUSINESS";
    private final String CLIENT_ROLE= "CLIENT";
    private final String EMPLOYEE_ROLE= "EMPLOYEE";
    public  UtilisateurDTO  convert(Utilisateur utilisateur) throws InvocationTargetException, IllegalAccessException {
        switch (utilisateur.getClass().getSimpleName().toUpperCase()){
            case BUSINESS_ROLE:

                BusinessDTO businessDTO= copyItem(new BusinessDTO(),utilisateur);
                List<EmployeeDTO> employeeDTOList= new ArrayList<>();
                businessDTO.setEmployees(employeeDTOList);
                if(Objects.nonNull(((Business)utilisateur).getEmployees())){
                    for(Employee employee : ((Business)utilisateur).getEmployees()){
                        employeeDTOList.add(copyItem(new EmployeeDTO(),employee));
                    }
                }

                return  businessDTO;

            case EMPLOYEE_ROLE:
                EmployeeDTO employeeDTO= copyItem(new EmployeeDTO(),utilisateur);
                BusinessDTO businessDTO1= copyItem(new BusinessDTO(),utilisateur);
                employeeDTO.setBoss(businessDTO1);

                return employeeDTO;
            case CLIENT_ROLE:
                ClientDTO clientDTO= copyItem(new ClientDTO(),utilisateur);
                return clientDTO;

        }
       return  null;

    }
    private <T,R> T copyItem(T returnObj, R valueToCopy) throws InvocationTargetException, IllegalAccessException {
        BeanUtils.copyProperties(returnObj,valueToCopy);
        return returnObj;
    }

}
