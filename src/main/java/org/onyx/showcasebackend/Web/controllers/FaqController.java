package org.onyx.showcasebackend.Web.controllers;

import org.onyx.showcasebackend.Web.services.FaqService;
import org.onyx.showcasebackend.entities.Faq;
import org.onyx.showcasebackend.entities.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api")
public class FaqController {
    @Autowired
    private FaqService faqService;

    @GetMapping("/faqs")
    private ResponseEntity<?> getAllFaqs() {
        HashMap<String,Object> data = new HashMap<>();
        List<Faq> items = faqService.getFaqs();
        data.put("results", items);
        data.put("count", items.size());
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    // creating a get mapping that retrieves the detail of a specific faq
    @GetMapping("/faqs/{id}")
    private Faq getFaqs(@PathVariable("id") long faqId) {
        return faqService.getFaqById(faqId);
    }

    // creating a deleted mapping that deletes a specified faq
    @DeleteMapping("/faqs/{id}")
    private void deleteFaq(@PathVariable("id") long faqId) {
        faqService.deleteFaq(faqId);
    }

    // creating post mapping that post the faq detail in the database
    @PostMapping("/faqs")
    private long saveFaq(@RequestBody Faq faq) {
        faqService.saveFaq(faq);
        return faq.getId();
    }

    // creating put mapping that updates the faq detail
    @PutMapping("/faqs")
    private Faq update(@RequestBody Faq faq) {
        faqService.saveFaq(faq);
        return faq;
    }

}
