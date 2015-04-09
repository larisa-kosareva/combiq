package ru.atott.combiq.service.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.atott.combiq.dao.entity.QuestionEntity;
import ru.atott.combiq.dao.repository.QuestionRepository;
import ru.atott.combiq.service.mapper.QuestionEntityToQuestionMapper;

@Service
public class GetQuestionServiceImpl implements GetQuestionService {
    private QuestionEntityToQuestionMapper questionMapper = new QuestionEntityToQuestionMapper();

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public GetQuestionResponse getQuestions(GetQuestionContext context) {
        Pageable pageable = new PageRequest(context.getPage(), context.getSize());
        Page<QuestionEntity> page = questionRepository.findAll(pageable);

        GetQuestionResponse response = new GetQuestionResponse();
        response.setQuestions(questionMapper.toList(page.getContent()));
        return response;
    }
}