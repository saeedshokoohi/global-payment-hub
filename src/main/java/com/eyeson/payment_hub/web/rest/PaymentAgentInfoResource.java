package com.eyeson.payment_hub.web.rest;

import com.eyeson.payment_hub.service.PaymentAgentInfoService;
import com.eyeson.payment_hub.web.rest.errors.BadRequestAlertException;
import com.eyeson.payment_hub.service.dto.PaymentAgentInfoDTO;

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
 * REST controller for managing {@link com.eyeson.payment_hub.domain.PaymentAgentInfo}.
 */
@RestController
@RequestMapping("/api")
public class PaymentAgentInfoResource {

    private final Logger log = LoggerFactory.getLogger(PaymentAgentInfoResource.class);

    private static final String ENTITY_NAME = "globalPaymentHubPaymentAgentInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaymentAgentInfoService paymentAgentInfoService;

    public PaymentAgentInfoResource(PaymentAgentInfoService paymentAgentInfoService) {
        this.paymentAgentInfoService = paymentAgentInfoService;
    }

    /**
     * {@code POST  /payment-agent-infos} : Create a new paymentAgentInfo.
     *
     * @param paymentAgentInfoDTO the paymentAgentInfoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paymentAgentInfoDTO, or with status {@code 400 (Bad Request)} if the paymentAgentInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/payment-agent-infos")
    public ResponseEntity<PaymentAgentInfoDTO> createPaymentAgentInfo(@RequestBody PaymentAgentInfoDTO paymentAgentInfoDTO) throws URISyntaxException {
        log.debug("REST request to save PaymentAgentInfo : {}", paymentAgentInfoDTO);
        if (paymentAgentInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new paymentAgentInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaymentAgentInfoDTO result = paymentAgentInfoService.save(paymentAgentInfoDTO);
        return ResponseEntity.created(new URI("/api/payment-agent-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /payment-agent-infos} : Updates an existing paymentAgentInfo.
     *
     * @param paymentAgentInfoDTO the paymentAgentInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentAgentInfoDTO,
     * or with status {@code 400 (Bad Request)} if the paymentAgentInfoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paymentAgentInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/payment-agent-infos")
    public ResponseEntity<PaymentAgentInfoDTO> updatePaymentAgentInfo(@RequestBody PaymentAgentInfoDTO paymentAgentInfoDTO) throws URISyntaxException {
        log.debug("REST request to update PaymentAgentInfo : {}", paymentAgentInfoDTO);
        if (paymentAgentInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PaymentAgentInfoDTO result = paymentAgentInfoService.save(paymentAgentInfoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentAgentInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /payment-agent-infos} : get all the paymentAgentInfos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paymentAgentInfos in body.
     */
    @GetMapping("/payment-agent-infos")
    public ResponseEntity<List<PaymentAgentInfoDTO>> getAllPaymentAgentInfos(Pageable pageable) {
        log.debug("REST request to get a page of PaymentAgentInfos");
        Page<PaymentAgentInfoDTO> page = paymentAgentInfoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /payment-agent-infos/:id} : get the "id" paymentAgentInfo.
     *
     * @param id the id of the paymentAgentInfoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paymentAgentInfoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/payment-agent-infos/{id}")
    public ResponseEntity<PaymentAgentInfoDTO> getPaymentAgentInfo(@PathVariable Long id) {
        log.debug("REST request to get PaymentAgentInfo : {}", id);
        Optional<PaymentAgentInfoDTO> paymentAgentInfoDTO = paymentAgentInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paymentAgentInfoDTO);
    }

    /**
     * {@code DELETE  /payment-agent-infos/:id} : delete the "id" paymentAgentInfo.
     *
     * @param id the id of the paymentAgentInfoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/payment-agent-infos/{id}")
    public ResponseEntity<Void> deletePaymentAgentInfo(@PathVariable Long id) {
        log.debug("REST request to delete PaymentAgentInfo : {}", id);
        paymentAgentInfoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/payment-agent-infos?query=:query} : search for the paymentAgentInfo corresponding
     * to the query.
     *
     * @param query the query of the paymentAgentInfo search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/payment-agent-infos")
    public ResponseEntity<List<PaymentAgentInfoDTO>> searchPaymentAgentInfos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of PaymentAgentInfos for query {}", query);
        Page<PaymentAgentInfoDTO> page = paymentAgentInfoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
