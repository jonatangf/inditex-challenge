package org.capitole.inditex.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.capitole.inditex.adapter.in.controller.price.model.PriceDTO;
import org.capitole.inditex.adapter.in.controller.price.model.SearchPriceResponseBody;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static java.util.Objects.isNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class SearchPricesIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @ParameterizedTest
    @CsvSource({"productId,35455", "brandId,1"})
    void shouldReturnAllPricesForAGivenProductIdOrBrandId(
            String parameterName,
            String parameterValue
    ) throws Exception {
        SearchPriceResponseBody searchPriceResponseBody = objectMapper
                .readValue(
                        mockMvc.perform(get("/api/prices")
                                                .queryParam(parameterName, parameterValue))
                                .andExpect(status().isOk())
                                .andReturn()
                                .getResponse()
                                .getContentAsString(),
                        SearchPriceResponseBody.class
                );

        assertThat(searchPriceResponseBody).isNotNull();
        List<PriceDTO> prices = searchPriceResponseBody.getPrices();
        assertThat(prices).isNotEmpty().hasSize(4);

        assertThat(prices.stream().anyMatch(price -> isNull(price.getProductId()) || isNull(price.getBrandId())))
                .isFalse();

        assertThat(prices.get(0).getStartDate()).isEqualTo("2020-06-13T23:00:00Z");
        assertThat(prices.get(0).getEndDate()).isEqualTo("2020-12-31T23:59:59Z");
        assertThat(prices.get(1).getStartDate()).isEqualTo("2020-06-14T14:00:00Z");
        assertThat(prices.get(1).getEndDate()).isEqualTo("2020-06-14T17:30:00Z");
        assertThat(prices.get(2).getStartDate()).isEqualTo("2020-06-14T23:00:00Z");
        assertThat(prices.get(2).getEndDate()).isEqualTo("2020-06-15T10:00:00Z");
        assertThat(prices.get(3).getStartDate()).isEqualTo("2020-06-15T15:00:00Z");
        assertThat(prices.get(3).getEndDate()).isEqualTo("2020-12-31T23:59:59Z");
    }

    @ParameterizedTest
    @CsvSource({
            "2020-06-14T10:00:00Z,2020-06-13T23:00:00Z,2020-12-31T23:59:59Z",
            "2020-06-14T16:00:00Z,2020-06-14T14:00:00Z,2020-06-14T17:30:00Z",
            "2020-06-14T21:00:00Z,2020-06-13T23:00:00Z,2020-12-31T23:59:59Z",
            "2020-06-15T10:00:00Z,2020-06-14T23:00:00Z,2020-06-15T10:00:00Z",
            "2020-06-16T21:00:00Z,2020-06-15T15:00:00Z,2020-12-31T23:59:59Z"
    })
    void shouldReturnPriceForAGivenDate(
            String dateTime, String expectedStartDate, String expectedEndDate
    ) throws Exception {
        SearchPriceResponseBody searchPriceResponseBody = objectMapper
                .readValue(
                        mockMvc.perform(get("/api/prices")
                                                .queryParam("brandId", "1")
                                                .queryParam("productId", "35455")
                                                .queryParam("effectiveDate", dateTime))
                                .andExpect(status().isOk())
                                .andReturn()
                                .getResponse()
                                .getContentAsString(),
                        SearchPriceResponseBody.class
                );

        assertThat(searchPriceResponseBody).isNotNull();
        List<PriceDTO> prices = searchPriceResponseBody.getPrices();
        assertThat(prices).isNotEmpty().hasSize(1);
        assertThat(prices.get(0).getStartDate()).isEqualTo(expectedStartDate);
        assertThat(prices.get(0).getEndDate()).isEqualTo(expectedEndDate);
    }
}
