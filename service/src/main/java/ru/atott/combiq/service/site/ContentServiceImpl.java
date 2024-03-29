package ru.atott.combiq.service.site;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.atott.combiq.dao.entity.ContentEntity;
import ru.atott.combiq.dao.entity.MarkdownContent;
import ru.atott.combiq.dao.repository.ContentRepository;

@Service
public class ContentServiceImpl implements ContentService {
    @Autowired
    private ContentRepository contentRepository;

    @Override
    public MarkdownContent getContent(String id) {
        ContentEntity contentEntity = contentRepository.findOne(id);

        if (contentEntity == null) {
            return new MarkdownContent(id, null);
        }

        MarkdownContent content = contentEntity.getContent();

        if (content.getId() == null) {
            content.setId(id);
            contentRepository.save(contentEntity);
        }

        return content;
    }

    @Override
    public void updateContent(String id, String content) {
        ContentEntity contentEntity = contentRepository.findOne(id);

        if (contentEntity == null) {
            contentEntity = new ContentEntity();
            contentEntity.setId(id);
        }

        contentEntity.setContent(new MarkdownContent(id, content));
        contentRepository.save(contentEntity);
    }
}
