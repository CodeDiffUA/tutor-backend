package dev.backend.tutor.services.practice.ukrmova;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Repository
public class UkrainianLanguageTestsSupplier {

    private static final Logger logger = LoggerFactory.getLogger(UkrainianLanguageTestsSupplier.class);

    private final MongoCollection<Document> lexicologyCollection;
    private final MongoCollection<Document> morphologyCollection;
    private final MongoCollection<Document> orthographyCollection;
    private final MongoCollection<Document> phoneticsGraphicsOrphoepyCollection;
    private final MongoCollection<Document> stylisticsTextSpeechDevelopmentCollection;
    private final MongoCollection<Document> syntaxPunctuationCollection;
    private final MongoCollection<Document> wordStructureCollection;

    public UkrainianLanguageTestsSupplier(MongoDatabase mongoDatabase) {
        lexicologyCollection = mongoDatabase.getCollection("lexicology");
        orthographyCollection = mongoDatabase.getCollection("orthography");
        morphologyCollection = mongoDatabase.getCollection("morphology");
        phoneticsGraphicsOrphoepyCollection = mongoDatabase.getCollection("phonetics-graphics-orphoepy");
        stylisticsTextSpeechDevelopmentCollection = mongoDatabase.getCollection("stylistics-text-speech-development");
        syntaxPunctuationCollection = mongoDatabase.getCollection("syntax-punctuation");
        wordStructureCollection = mongoDatabase.getCollection("word-structure");
    }

    public List<Document> getDocumentFromLexicologyCollectionByTopicName(String topicName, int page, int size) {
        return getDocumentByNameFromCollectionByName(topicName, lexicologyCollection, page, size);
    }

    public List<Document> getDocumentFromMorphologyCollectionByTopicName(String topicName, int page, int size) {
        return getDocumentByNameFromCollectionByName(topicName, morphologyCollection, page, size);
    }

    public List<Document> getDocumentFromOrthographyCollectionByTopicName(String topicName, int page, int size) {
        return getDocumentByNameFromCollectionByName(topicName, orthographyCollection, page, size);
    }

    public List<Document> getDocumentFromPhoneticsGraphicsOrphoepyCollectionByTopicName(String topicName, int page, int size) {
        return getDocumentByNameFromCollectionByName(topicName, phoneticsGraphicsOrphoepyCollection, page, size);
    }

    public List<Document> getDocumentFromStylisticsTextSpeechDevelopmentCollectionByTopicName(String topicName, int page, int size) {
        return getDocumentByNameFromCollectionByName(topicName, stylisticsTextSpeechDevelopmentCollection, page, size);
    }

    public List<Document> getDocumentFromSyntaxPunctuationCollectionByTopicName(String topicName, int page, int size) {
        return getDocumentByNameFromCollectionByName(topicName, syntaxPunctuationCollection, page, size);
    }

    public List<Document> getDocumentFromWordStructureCollectionByTopicName(String topicName, int page, int size) {
        return getDocumentByNameFromCollectionByName(topicName, wordStructureCollection, page, size);
    }

    private List<Document> getDocumentByNameFromCollectionByName(
            String topicName,
            MongoCollection<Document> collection,
            int page,
            int size
    ) {
        Document filter = new Document("name", topicName);
        List<Document> listOfJsonTests = new LinkedList<>();

        try (MongoCursor<Document> cursor = collection.find(filter).iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                List<Document> tests = (List<Document>) doc.get("tests");
                if (tests != null) {
                    listOfJsonTests.addAll(tests);
                }
            }
        } catch (MongoException e) {
            logger.error("MongoException while retrieving documents for topic name: {}", topicName, e);
        } catch (Exception e) {
            logger.error("Exception while retrieving documents for topic name: {}", topicName, e);
        }

        int start = page * size;
        int end = Math.min(start + size, listOfJsonTests.size());

        if (start > listOfJsonTests.size()) {
            return new ArrayList<>();
        }

        return listOfJsonTests.subList(start, end);
    }

}
