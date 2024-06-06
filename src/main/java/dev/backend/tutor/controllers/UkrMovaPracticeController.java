package dev.backend.tutor.controllers;



import dev.backend.tutor.services.practice.ukrmova.UkrainianLanguageTestsSupplier;

import org.bson.Document;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/practice/ukr_mova")
public class UkrMovaPracticeController {

    private final UkrainianLanguageTestsSupplier ukrainianLanguageTestsSupplier;

    public UkrMovaPracticeController(UkrainianLanguageTestsSupplier ukrainianLanguageTestsSupplier) {
        this.ukrainianLanguageTestsSupplier = ukrainianLanguageTestsSupplier;
    }

    @GetMapping("/lexicology/{topic}")
    public ResponseEntity<List<Document>> getLexicologyTestsByTopic(
            @PathVariable("topic") String topic,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        List<Document> documents = ukrainianLanguageTestsSupplier.getDocumentFromLexicologyCollectionByTopicName(topic, page, size);
        return ResponseEntity.ok(documents);
    }

    @GetMapping("/syntax_punctuation/{topic}")
    public List<Document> getSyntaxPunctuationTestsByTopic(
            @PathVariable("topic") String topic,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        return ukrainianLanguageTestsSupplier.getDocumentFromSyntaxPunctuationCollectionByTopicName(topic, page, size);
    }

    @GetMapping("/word_structure/{topic}")
    public List<Document> getWordStructureTestsByTopic(
            @PathVariable("topic") String topic,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        return ukrainianLanguageTestsSupplier.getDocumentFromWordStructureCollectionByTopicName(topic, page, size);
    }

    @GetMapping("/stylistics_text_speech_development/{topic}")
    public List<Document> getStylisticsTextSpeechDevelopmentTestsByTopic(
            @PathVariable("topic") String topic,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        return ukrainianLanguageTestsSupplier.getDocumentFromStylisticsTextSpeechDevelopmentCollectionByTopicName(topic, page, size);
    }

    @GetMapping("/orthography/{topic}")
    public List<Document> getOrthographyTestsByTopic(
            @PathVariable("topic") String topic,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        return ukrainianLanguageTestsSupplier.getDocumentFromOrthographyCollectionByTopicName(topic, page, size);
    }

    @GetMapping("/morphology/{topic}")
    public List<Document> getMorphologyTestsByTopic(
            @PathVariable("topic") String topic,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        return ukrainianLanguageTestsSupplier.getDocumentFromMorphologyCollectionByTopicName(topic, page, size);
    }

    @GetMapping("/phonetics_graphics_orphoepy/{topic}")
    public List<Document> getPhoneticsGraphicsOrthoepyTestsByTopic(
            @PathVariable("topic") String topic,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        return ukrainianLanguageTestsSupplier.getDocumentFromPhoneticsGraphicsOrphoepyCollectionByTopicName(topic, page, size);
    }
}
