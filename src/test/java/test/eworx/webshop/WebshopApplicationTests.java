package test.eworx.webshop;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import test.eworx.webshop.framework.V1;
import test.eworx.webshop.model.AvailableProducts;
import test.eworx.webshop.model.Basket;
import test.eworx.webshop.model.SelectedProduct;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WebshopApplicationTests {

    @Autowired
    private WebApplicationContext ctx;

    @Autowired
    ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.ctx)
                .build();
    }

    @Test
    public void TestTotalControllerOK() throws Exception {
        TestProductsControllerOK();
        TestAddProductControllerOK();
        TestRemoveProductControllerOK();
        TestBasketControllerOK();
    }

    /**
     * Test TestProductsControllerOK
     * @throws Exception
     */
    public void TestProductsControllerOK() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(
                get(V1.URI_GET_PRODUCTS_ABSOLUTE).accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(request().asyncStarted())
                .andReturn();

        ResponseEntity<AvailableProducts> availableProductsResponseEntity = (ResponseEntity<AvailableProducts>)mvcResult.getAsyncResult();
        AvailableProducts availableProducts = availableProductsResponseEntity.getBody();

        assertEquals(availableProducts.getResults().size(), 5);
        assertEquals(availableProductsResponseEntity.getStatusCode(), HttpStatus.OK);
    }

    /**
     * Test TestAddProductControllerOK with correct parameters
     * @throws Exception
     */
    public void TestAddProductControllerOK() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(
                get(V1.URI_ADD_PRODUCT_ABSOLUTE).accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .param("id", "1"))
                .andExpect(request().asyncStarted())
                .andReturn();

        ResponseEntity<SelectedProduct> selectedProductResponseEntity = (ResponseEntity<SelectedProduct>)mvcResult.getAsyncResult();
        SelectedProduct selectedProduct = selectedProductResponseEntity.getBody();

        assertEquals(selectedProduct.getCounter().toString(), "1");
        assertEquals(selectedProductResponseEntity.getStatusCode(), HttpStatus.OK);
    }

    /**
     * Test TestLocationControllerOK with correct parameters
     * @throws Exception
     */
    public void TestRemoveProductControllerOK() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(
                get(V1.URI_REMOVE_PRODUCT_ABSOLUTE).accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .param("id", "1"))
                .andExpect(request().asyncStarted())
                .andReturn();

        ResponseEntity<SelectedProduct> selectedProductResponseEntity = (ResponseEntity<SelectedProduct>)mvcResult.getAsyncResult();
        SelectedProduct selectedProduct = selectedProductResponseEntity.getBody();

        assertEquals(selectedProduct.getCounter().toString(), "0");
        assertEquals(selectedProductResponseEntity.getStatusCode(), HttpStatus.OK);
    }

    /**
     * Test TestBasketControllerOK
     * @throws Exception
     */
    public void TestBasketControllerOK() throws Exception {
        this.mockMvc.perform(
                get(V1.URI_GET_PRODUCTS_ABSOLUTE).accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(request().asyncStarted())
                .andReturn();

        this.mockMvc.perform(
                get(V1.URI_ADD_PRODUCT_ABSOLUTE).accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .param("id", "5"))
                .andExpect(request().asyncStarted())
                .andReturn();

        MvcResult mvcResult = this.mockMvc.perform(
                get(V1.URI_GET_BASKET_ABSOLUTE).accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(request().asyncStarted())
                .andReturn();

        ResponseEntity<Basket> basketResponseEntity = (ResponseEntity<Basket>)mvcResult.getAsyncResult();
        Basket basket = basketResponseEntity.getBody();

        assertEquals(basket.getTotalPrice().toString(), "52.5");
        assertEquals(basketResponseEntity.getStatusCode(), HttpStatus.OK);
    }
}

