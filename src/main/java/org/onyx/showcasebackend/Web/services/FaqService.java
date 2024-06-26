package org.onyx.showcasebackend.Web.services;

import org.onyx.showcasebackend.dao.FaqRepository;
import org.onyx.showcasebackend.entities.Faq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FaqService {
    @Autowired
    FaqRepository faqRepository;

    public List<Faq> getFaqs() {
        return faqRepository.findAll();
    }

    public Faq getFaqById(Long id) {
        return faqRepository.findById(id).isPresent() ? faqRepository.findById(id).get() : null;
    }

    public void saveFaq(Faq faq) {
        faqRepository.save(faq);
    }

    public void deleteFaq(long id) {
        faqRepository.deleteById(id);
    }

    public void updateFaq(Faq faq, Long id) {
        faq.setId(id);
        faqRepository.save(faq);
    }

}
