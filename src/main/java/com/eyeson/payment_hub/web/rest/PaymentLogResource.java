package com.eyeson.payment_hub.web.rest;

import com.eyeson.payment_hub.service.PaymentLogService;
import com.eyeson.payment_hub.web.rest.errors.BadRequestAlertException;
import com.eyeson.payment_hub.service.dto.PaymentLogDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.eyeson.payment_hub.domain.PaymentLog}.
 */
@RestController
@RequestMapping("/api")
public class PaymentLogResource {

    private final Logger log = LoggerFactory.getLogger(PaymentLogResource.class);

    private static final String ENTITY_NAME = "globalPaymentHubPaymentLog";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaymentLogService paymentLogService;

    public PaymentLogResource(PaymentLogService paymentLogService) {
        this.paymentLogService = paymentLogService;
    }

    /**
     * {@code POST  /payment-logs} : Create a new paymentLog.
     *
     * @param paymentLogDTO the paymentLogDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paymentLogDTO, or with status {@code 400 (Bad Request)} if the paymentLog has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/payment-logs")
    public ResponseEntity<PaymentLogDTO> createPaymentLog(@RequestBody PaymentLogDTO paymentLogDTO) throws URISyntaxException {
        log.debug("REST request to save PaymentLog : {}", paymentLogDTO);
        if (paymentLogDTO.getId() != null) {
            throw new BadRequestAlertException("A new paymentLog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaymentLogDTO result = paymentLogService.save(paymentLogDTO);
        return ResponseEntity.created(new URI("/api/payment-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /payment-logs} : Updates an existing paymentLog.
     *
     * @param paymentLogDTO the paymentLogDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentLogDTO,
     * or with status {@code 400 (Bad Request)} if the paymentLogDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paymentLogDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/payment-logs")
    public ResponseEntity<PaymentLogDTO> updatePaymentLog(@RequestBody PaymentLogDTO paymentLogDTO) throws URISyntaxException {
        log.debug("REST request to update PaymentLog : {}", paymentLogDTO);
        if (paymentLogDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PaymentLogDTO result = paymentLogService.save(paymentLogDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentLogDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /payment-logs} : get all the paymentLogs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paymentLogs in body.
     */
    @GetMapping("/payment-logs")
    public ResponseEntity<List<PaymentLogDTO>> getAllPaymentLogs(Pageable pageable) {
        log.debug("REST request to get a page of PaymentLogs");
        Page<PaymentLogDTO> page = paymentLogService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /payment-logs/:id} : get the "id" paymentLog.
     *
     * @param id the id of the paymentLogDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paymentLogDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/payment-logs/{id}")
    public ResponseEntity<PaymentLogDTO> getPaymentLog(@PathVariable Long id) {
        log.debug("REST request to get PaymentLog : {}", id);
        Optional<PaymentLogDTO> paymentLogDTO = paymentLogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paymentLogDTO);
    }

    /**
     * {@code DELETE  /payment-logs/:id} : delete the "id" paymentLog.
     *
     * @param id the id of the paymentLogDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/payment-logs/{id}")
    public ResponseEntity<Void> deletePaymentLog(@PathVariable Long id) {
        log.debug("REST request to delete PaymentLog : {}", id);
        paymentLogService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/payment-logs?query=:query} : search for the paymentLog corresponding
     * to the query.
     *
     * @param query the query of the paymentLog search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/payment-logs")
    public ResponseEntity<List<PaymentLogDTO>> searchPaymentLogs(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of PaymentLogs for query {}", query);
        Page<PaymentLogDTO> page = paymentLogService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
