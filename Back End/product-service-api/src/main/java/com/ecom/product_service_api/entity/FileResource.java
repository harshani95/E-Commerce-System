package com.ecom.product_service_api.entity;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import lombok.*;

import java.sql.Blob;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Embeddable
public class FileResource {
    @Lob
    private Blob hash;

    @Lob
    private Blob fileName;

    @Lob
    private Blob resourceUrl;

    @Lob
    private Blob directory;


}
