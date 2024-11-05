package com.ecom.product_service_api.dto.response.paginate;

import com.ecom.product_service_api.dto.response.ResponseProductDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ResponseProductPaginate {
    private long count;
    private List<ResponseProductDto> productDtoList;
}
