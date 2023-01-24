package al.ikubinfo.registrationmanagement.controller;

import al.ikubinfo.registrationmanagement.dto.BaseDto;
import al.ikubinfo.registrationmanagement.repository.criteria.BaseCriteria;
import al.ikubinfo.registrationmanagement.service.ServiceTemplate;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@AllArgsConstructor
public abstract class ControllerTemplate<
        D extends BaseDto,
        C extends BaseCriteria,
        S extends ServiceTemplate> {


    protected final S service;

    /**
     * Exports the criteria data into a excel file
     *
     * @param criteria
     * @return ResponseEntity
     */
    @PostMapping(value = "exportToExcel")
    public ResponseEntity<Resource> export(@Nullable C criteria) {
        ByteArrayResource resource;
        HttpHeaders headers = new HttpHeaders();
        String fileName = RandomStringUtils.randomAlphanumeric(17).toUpperCase();
        resource = new ByteArrayResource(service.createExcel(criteria));
        headers.setContentType(new MediaType("application", "vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + ".xlsx\"");
        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }

    /**
     * Exports the criteria data into a cvs file
     *
     * @param criteria
     * @return ResponseEntity
     */
    @PostMapping(value = "exportToCvs")
    public ResponseEntity<Resource> exportToCvs(@Nullable C criteria) {
        ByteArrayResource resource;
        HttpHeaders headers = new HttpHeaders();
        String fileName = RandomStringUtils.randomAlphanumeric(17).toUpperCase();
        resource = new ByteArrayResource(service.createCsv(criteria));
        headers.setContentType(new MediaType("text", "csv"));
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + ".csv\"");
        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }

    /**
     * Exports the criteria data into a pdf file
     *
     * @param criteria
     * @return ResponseEntity
     */
    @PostMapping(value = "exportToPdf")
    public ResponseEntity<Resource> exportToPdf(@Nullable C criteria) {
        ByteArrayResource resource;
        HttpHeaders headers = new HttpHeaders();
        String fileName = RandomStringUtils.randomAlphanumeric(17).toUpperCase();
        resource = new ByteArrayResource(service.createPdf(criteria));
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + ".pdf\"");
        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }
}
