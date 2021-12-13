//package com.dbc.walletapi.testes;
//
//
//import com.dbc.walletapi.dto.GerenteCreateDTO;
//import org.junit.jupiter.api.Test;
//import org.testng.annotations.BeforeClass;
//
//import javax.validation.ConstraintViolation;
//import javax.validation.Validation;
//import javax.validation.Validator;
//import java.util.Set;
//
//public class TestesUnitariosDTO {
//
//    @BeforeClass
//    public static void setupValidatorInstance() {
//        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
//    }
//
//    @Test
//    public void whenNotNullName_thenNoConstraintViolations(){
//        GerenteCreateDTO gerente = new GerenteCreateDTO();
//        gerente.setNomeCompleto("Fulano");
//        Set<ConstraintViolation<GerenteCreateDTO>> violations = Validator.validate(gerente);
//    }
//}
