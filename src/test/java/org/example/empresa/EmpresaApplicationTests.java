package org.example.empresa;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.empresa.domain.Company;
import org.example.empresa.dto.CompanyDTO;
import org.example.empresa.repository.CompanyRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class EmpresaApplicationTests {
    // @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockitoBean
    private CompanyRepository companyRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    Company company() {
        var company = new Company();
        company.setId(1L);
        company.setIssuer("1111");
        company.setAddress("asdsd");
        company.setName("ch");
        return company;
    }

    CompanyDTO companyDto() {
        var company = new CompanyDTO();
        company.setId(1L);
        company.setIssuer("1111");
        company.setAddress("asdsd");
        company.setName("ch");
        return company;
    }

    @Test
    void getCompanies() throws Exception {
        Mockito.when(companyRepository.findAll()).thenReturn(List.of(company()));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/company")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void getCompaniesNoContent() throws Exception {
        Mockito.when(companyRepository.findAll()).thenReturn(new ArrayList<>());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/company")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        Assertions.assertEquals(204, response.getStatus());
    }

    @Test
    void createCompany() throws Exception {
        Mockito.when(companyRepository.findByIssuer(Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(companyRepository.save(Mockito.any())).thenReturn(company());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/company")
                .content(objectMapper.writeValueAsString(companyDto()))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        Assertions.assertEquals(201, response.getStatus());
    }

    @Test
    void createCompanyConflict() throws Exception {
        Mockito.when(companyRepository.findByIssuer(Mockito.any())).thenReturn(Optional.of(company()));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/company")
                .content(objectMapper.writeValueAsString(companyDto()))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        Assertions.assertEquals(409, response.getStatus());
    }

    @Test
    void createCompanyBadRequest() throws Exception {
        var comp = companyDto();
        comp.setIssuer(null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/company")
                .content(objectMapper.writeValueAsString(comp))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }


    @Test
    void getCompany() throws Exception {
        Mockito.when(companyRepository.findById(Mockito.any())).thenReturn(Optional.of(company()));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/company/4")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void getCompanyNoContent() throws Exception {
        Mockito.when(companyRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/company/4")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        Assertions.assertEquals(204, response.getStatus());
    }


    @Test
    void deleteCompany() throws Exception {
        Mockito.when(companyRepository.findById(Mockito.any())).thenReturn(Optional.of(company()));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/company/4")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deleteCompanyBadRequest() throws Exception {
        Mockito.when(companyRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/company/4")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    void updateCompany() throws Exception {
        Mockito.when(companyRepository.findById(Mockito.any())).thenReturn(Optional.of(company()));
        Mockito.when(companyRepository.findByIssuerAndIdIsNot(Mockito.any(), Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(companyRepository.save(Mockito.any())).thenReturn(company());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/api/company")
                .content(objectMapper.writeValueAsString(companyDto()))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        Assertions.assertEquals(201, response.getStatus());
    }

    @Test
    void updateCompanyBadRequest() throws Exception {
        var comp = companyDto();
        comp.setIssuer(null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/api/company")
                .content(objectMapper.writeValueAsString(comp))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

}
